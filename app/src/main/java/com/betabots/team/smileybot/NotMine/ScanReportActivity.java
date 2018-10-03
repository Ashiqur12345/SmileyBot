package com.betabots.team.smileybot.NotMine;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.betabots.team.smileybot.BuildConfig;
import com.betabots.team.smileybot.Person;
import com.betabots.team.smileybot.R;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.betabots.team.smileybot.NotMine.database_package.Report.ReportModel;
import com.betabots.team.smileybot.NotMine.database_package.Report.ReportRepository;
import com.betabots.team.smileybot.NotMine.database_package.ReportResult.ReportResultModel;
import com.betabots.team.smileybot.NotMine.database_package.ReportResult.ReportResultRepository;
import com.betabots.team.smileybot.NotMine.report_helper.AttributeValue;
import com.betabots.team.smileybot.NotMine.report_helper.RearrangeScannedReportData;
import com.betabots.team.smileybot.NotMine.report_helper.ReportCategory;
import com.betabots.team.smileybot.NotMine.retrofit.ReportResponse;
import com.betabots.team.smileybot.NotMine.retrofit.RetrofitAPI;
import com.betabots.team.smileybot.NotMine.retrofit.UploadFileResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScanReportActivity extends AppCompatActivity
{

	private static final String LOG_TAG = "Text API";
	private static final int PHOTO_REQUEST = 10;
	private ImageView imageView;
	private TextView rawScanedResults;
	private Uri imageUri;
	private TextRecognizer detector;
	private static final int REQUEST_WRITE_PERMISSION_CAPTURE = 20;
	private static final int REQUEST_WRITE_PERMISSION_GALLERY = 21;
	public static final String SAVED_INSTANCE_URI = "uri";
	private static final String SAVED_INSTANCE_RESULT = "result";
	private static String imagePath = "";
	private static String imageName = "";
	private ReportModel reportModel;
	private String reportCategoryString;
	private ArrayList<AttributeValue> resultList = new ArrayList<>();
	private ProgressDialog mProgressDialog;

	private String patientName = "User";
	private String patientAge = "0";
	private TextInputEditText nameInputEditText;
	private TextInputEditText ageInputEditText;
	private ArrayList<TextView> attributeTextViewList = new ArrayList<>();
	private ArrayList<TextInputEditText> valueTextInputEditTextList = new ArrayList<>();
	boolean updating = false;
	private List<ReportResultModel> reportResultModels;

	private ReportCategory reportCategory = new ReportCategory();
	private RearrangeScannedReportData rearrangeScannedReportData = new RearrangeScannedReportData();
	private ZoomImage zoomImage = new ZoomImage();


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan_report);

		Person patient = (Person) getIntent().getSerializableExtra("PATIENT");
		if (patient != null){
			this.patientName = patient.getName();
			this.patientAge = patient.getAge()+"";
		}

		imageView = (ImageView) findViewById(R.id.imageView);
		imageView.setOnTouchListener(new View.OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				ImageView view = (ImageView) v;
/*
				view.bringToFront();
*/
				zoomImage.viewTransformation(view, event);
				return true;
			}
		});

		rawScanedResults = (TextView) findViewById(R.id.results);
		this.rawScanedResults.setFocusable(false);

		if (savedInstanceState != null)
		{
			imageUri = Uri.parse(savedInstanceState.getString(SAVED_INSTANCE_URI));
			rawScanedResults.setText(savedInstanceState.getString(SAVED_INSTANCE_RESULT));
		}
		detector = new TextRecognizer.Builder(getApplicationContext()).build();

		//Take Picture Button
		Button takePictureBtn = (Button) findViewById(R.id.captureBtn);
		takePictureBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				ActivityCompat.requestPermissions(ScanReportActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION_CAPTURE);
			}
		});

		//From Gallery Button
		Button fromGalleryBtn = (Button) findViewById(R.id.galleryBtn);
		fromGalleryBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent pickerPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				ActivityCompat.requestPermissions(ScanReportActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION_GALLERY);
				startActivityForResult(pickerPhotoIntent, 1);
			}
		});

		//Save Button
		final Button saveBtn = (Button) findViewById(R.id.saveBtn);
		saveBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				if (reportModel == null)
				{
					savePictureCompressed();
					Toast.makeText(ScanReportActivity.this, "No report found to save!", Toast.LENGTH_SHORT).show();
					return;
				}


				try
				{
					patientName = nameInputEditText.getText().toString();
					patientAge = ageInputEditText.getText().toString();
					if (patientName.length() == 0)
					{
						Toast.makeText(ScanReportActivity.this, "Please provide patient name.", Toast.LENGTH_SHORT).show();
						nameInputEditText.requestFocus();
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.showSoftInput(nameInputEditText, InputMethodManager.SHOW_IMPLICIT);
						return;
					}
					if (patientAge.length() == 0)
					{
						Toast.makeText(ScanReportActivity.this, "Please provide patient age.", Toast.LENGTH_SHORT).show();
						ageInputEditText.requestFocus();
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.showSoftInput(ageInputEditText, InputMethodManager.SHOW_IMPLICIT);
						return;
					}
					reportModel.setPatientName(patientName);
					reportModel.setPatientAge(patientAge);

					if (!updating)
					{
						savePicture(); //Before saving report, save the picture with new path into our own directory.
					}
					ReportRepository reportRepository = new ReportRepository(getApplication());
					reportRepository.insert(reportModel);

					List<ReportResultModel> reportResultModelsLocal = new ArrayList<>();
					ReportResultRepository reportResultRepository;
					if (updating)
					{
						for (int i = 0; i < valueTextInputEditTextList.size(); i++)
						{
							reportResultModelsLocal.add(new ReportResultModel(reportResultModels.get(i).getId(), reportModel.getId(), resultList.get(i).getAttribute(), valueTextInputEditTextList.get(i).getText().toString(), resultList.get(i).getAccuracy()));
						}
						reportResultRepository = new ReportResultRepository(getApplication());
						reportResultRepository.updateAll(reportResultModelsLocal);
						Toast.makeText(ScanReportActivity.this, "Report with id " + reportModel.getId() + " updated successfully.", Toast.LENGTH_LONG).show();
						updating = false;
					}
					else
					{
						for (int i = 0; i < valueTextInputEditTextList.size(); i++)
						{
							reportResultModelsLocal.add(new ReportResultModel(0, reportModel.getId(), resultList.get(i).getAttribute(), valueTextInputEditTextList.get(i).getText().toString(), resultList.get(i).getAccuracy()));
						}
						reportResultRepository = new ReportResultRepository(getApplication());
						reportResultRepository.insertAll(reportResultModelsLocal);
						Toast.makeText(ScanReportActivity.this, "Report saved with id " + reportModel.getId(), Toast.LENGTH_LONG).show();
					}

					MainActivity.loadReportsIntoCardView();

				}
				catch (Exception e)
				{
					Toast.makeText(ScanReportActivity.this, "Error: " + e.getMessage() + reportModel.getId(), Toast.LENGTH_SHORT).show();
					return;
				}

				reportModel = null;
			}


		});


		if (savedInstanceState == null)
		{
			Bundle extras = getIntent().getExtras();
			if (extras != null && extras.getString("id") != null)
			{
				loadReportToUpdate(extras.getString("id"));
			}
			else if (extras != null && extras.getString("action").equals("Capture"))
			{
				ActivityCompat.requestPermissions(ScanReportActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION_CAPTURE);
			}
			else if (extras != null && extras.getString("action").equals("Gallery"))
			{
				Intent pickerPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				ActivityCompat.requestPermissions(ScanReportActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION_GALLERY);
				startActivityForResult(pickerPhotoIntent, 1);
			}
		}

	}

	private void savePicture()
	{
		String path = "/sdcard/Android/data/com.ranadepto.smartehr";
		String fileName = "SmartEHR_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".png";
		File direct = new File(Environment.getExternalStorageDirectory() + path);

		if (!direct.exists())
		{
			File reportImageDirectory = new File(path);
			reportImageDirectory.mkdirs();
		}

		File photoSource = new File(imagePath);
		File photoDestination = new File(path, fileName);
		reportModel.setImagePath(photoDestination.getPath());
		reportModel.setId(photoDestination.getName());

		long start = 0;

/*
		start = System.nanoTime();
		try
		{
			FileChannel sourceChannel = new FileInputStream(photoSource).getChannel();
			FileChannel destChannel = new FileOutputStream(photoDestination).getChannel();
			destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());

			sourceChannel.close();
			destChannel.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("Time taken to copy File = " + (System.nanoTime() - start));
*/


		//Save a compressed image
		try
		{
			start = System.nanoTime();
			FileOutputStream fos = new FileOutputStream(path + "/" + fileName);
			Bitmap bitmap = decodeBitmapUri(getApplicationContext(), Uri.fromFile(photoSource));
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos); // bmp is your Bitmap instance
			fos.close();
			// PNG is a lossless format, the compression factor (100) is ignored
			System.out.println("Time taken to compressed File = " + (System.nanoTime() - start));

			start = System.nanoTime();
			String fileNameThumbnail = "Thumbnail_" + fileName;
			FileOutputStream outThumbnail = new FileOutputStream(path + "/" + fileNameThumbnail);
			Bitmap bitmapThumbnail = decodeBitmapUri(getApplicationContext(), Uri.fromFile(photoSource));
			bitmapThumbnail = Bitmap.createScaledBitmap(bitmapThumbnail, bitmapThumbnail.getWidth() / 3, bitmapThumbnail.getHeight() / 3, true);
			bitmapThumbnail.compress(Bitmap.CompressFormat.PNG, 100, outThumbnail); // bmp is your Bitmap instance
			outThumbnail.close();
			System.out.println("Time taken to thumbnail File = " + (System.nanoTime() - start));

			uploadFile(reportModel);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void savePictureCompressed()
	{
		String path = "/sdcard/Android/data/com.ranadepto.smartehr/compressed";
		File direct = new File(Environment.getExternalStorageDirectory() + path);

		if (!direct.exists())
		{
			File reportImageDirectory = new File(path);
			reportImageDirectory.mkdirs();
		}

		File photoSource = new File(imagePath);
		String fileName = photoSource.getName();

		//Save a compressed image
		try
		{
			FileOutputStream fos = new FileOutputStream(path + "/" + fileName);
			Bitmap bitmap = decodeBitmapUri(getApplicationContext(), Uri.fromFile(photoSource));
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos); // bmp is your Bitmap instance
			fos.close();
			// PNG is a lossless format, the compression factor (100) is ignored
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void loadReportToUpdate(String reportID)
	{
		ReportRepository reportRepository = new ReportRepository(getApplication());
		reportModel = reportRepository.findById(reportID);

		ReportResultRepository reportResultRepository = new ReportResultRepository(getApplication());
		reportResultModels = reportResultRepository.findById(reportID);
		resultList.clear();
		for (int i = 0; i < reportResultModels.size(); i++)
		{
			resultList.add(new AttributeValue(reportResultModels.get(i).getAttribute(), reportResultModels.get(i).getValue().toString(), reportResultModels.get(i).getAccuracy()));
		}

		imageView.setImageBitmap(BitmapFactory.decodeFile(reportModel.getImagePath()));
		patientName = reportModel.getPatientName();
		patientAge = reportModel.getPatientAge();
		reportCategoryString = reportModel.getReportCategory();
		imageName = reportModel.getId();
		updating = true;

		generateResultForm();
	}


	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode)
		{
			case REQUEST_WRITE_PERMISSION_CAPTURE:
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
				{
					takePicture();
				}
				else
				{
					Toast.makeText(ScanReportActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
				}
			case REQUEST_WRITE_PERMISSION_GALLERY:
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
				{
					return;
				}
				else
				{
					Toast.makeText(ScanReportActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
				}
		}
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode != PHOTO_REQUEST && resultCode == RESULT_OK && data != null)
		{
			Uri selectedImage = data.getData();
			String[] filePathColumn = {MediaStore.Images.Media.DATA};

			Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			File photo = new File(picturePath);
			imagePath = photo.getPath();
			imageName = photo.getName();

			imageUri = FileProvider.getUriForFile(ScanReportActivity.this, BuildConfig.APPLICATION_ID + ".provider", photo);

			requestCode = PHOTO_REQUEST;

		}

		if (requestCode == PHOTO_REQUEST && resultCode == RESULT_OK)
		{
			launchMediaScanIntent();
			try
			{
				Bitmap bitmap = decodeBitmapUri(this, imageUri);
				//createDirectoryAndSaveFile(bitmap);

				if (detector.isOperational() && bitmap != null)
				{
					imageView.setImageBitmap(bitmap); //If detector is operational, show the scanned image. Even if it can detect the text or not.

					Frame frame = new Frame.Builder().setBitmap(bitmap).build();
					SparseArray<TextBlock> textBlocks = detector.detect(frame);
					String blocks = "";
					String lines = "";
					ArrayList<String> scannedLineList = new ArrayList<>();
					String words = "";
					for (int index = 0; index < textBlocks.size(); index++)
					{
						//extract scanned text blocks here
						TextBlock tBlock = textBlocks.valueAt(index);
						blocks = blocks + tBlock.getValue() + "\n" + "\n";
						for (Text line : tBlock.getComponents())
						{
							//extract scanned text lines here
							lines = lines + line.getValue() + "\n";
							scannedLineList.add(line.getValue());
							for (Text element : line.getComponents())
							{
								//extract scanned text words here
								words = words + element.getValue() + ", ";
							}
						}
					}
					if (textBlocks.size() == 0)
					{
						rawScanedResults.setText("Scan Failed: Found nothing to scan");
					}
					else
					{
						//Detecting which type of reportModel it is(CBC/Urine/Stool).
						reportCategoryString = reportCategory.getReportCategory(words);

						//Getting scanned reportModel data in a presentable format
						if (reportCategoryString.equals("CBC"))
						{
							resultList = rearrangeScannedReportData.getCBCResult(scannedLineList);
						}
						else if (reportCategoryString.equals("URINE"))
						{
							resultList = rearrangeScannedReportData.getUrineResult(scannedLineList);
						}
						else if (reportCategoryString.equals("STOOL"))
						{
							resultList = rearrangeScannedReportData.getStoolResult(scannedLineList);

							//resultList = rearrangeScannedReportData.getStoolResultRegex(scannedLineList);
						}
						else
						{
/*
							rawScanedResults.setText(rawScanedResults.getText() + "Blocks: " + "\n");
							rawScanedResults.setText(rawScanedResults.getText() + blocks + "\n");
							rawScanedResults.setText(rawScanedResults.getText() + "---------" + "\n");
							rawScanedResults.setText(rawScanedResults.getText() + "Lines: " + "\n");
							rawScanedResults.setText(rawScanedResults.getText() + lines + "\n");
							rawScanedResults.setText(rawScanedResults.getText() + "---------" + "\n");
							rawScanedResults.setText(rawScanedResults.getText() + "Words: " + "\n");
							rawScanedResults.setText(rawScanedResults.getText() + words + "\n");
							rawScanedResults.setText(rawScanedResults.getText() + "---------" + "\n");
*/
						}

						rawScanedResults.setText("\n\n\n(This area is to help the developer, so that he can check original raw scanned value)" + "\n");
						rawScanedResults.setText(rawScanedResults.getText() + "Blocks: " + "\n");
						rawScanedResults.setText(rawScanedResults.getText() + blocks + "\n");
						rawScanedResults.setText(rawScanedResults.getText() + "---------" + "\n");


						if (resultList != null && resultList.size() != 0)
						{
							reportModel = new ReportModel(imageName, patientName, patientAge, imagePath, reportCategoryString, 1);
							generateResultForm();
						}
					}
				}
				else
				{
					rawScanedResults.setText("Could not set up the detector!");
				}
			}
			catch (Exception e)
			{
				Toast.makeText(this, "Failed to load Image", Toast.LENGTH_SHORT).show();
				rawScanedResults.setText(rawScanedResults.getText() + "\nError: " + e.toString());
				Log.e(LOG_TAG, e.toString());
			}
		}
	}


	private void generateResultForm()
	{

		final LinearLayout resultForm = (LinearLayout) findViewById(R.id.resultForm);
		resultForm.removeAllViews();
		String resultString = "";
		double totalAccuracy = 0;


		for (int i = 0; i < resultList.size(); i++)
		{
			totalAccuracy += resultList.get(i).getAccuracy();
			resultString += resultList.get(i).getAttribute() + "\t\t: " + resultList.get(i).getValue() + "\n";
		}

		TextView investigationResult = new TextView(this);
		investigationResult.setTextColor(Color.GREEN);
		investigationResult.setBackgroundColor(Color.BLACK);
		investigationResult.setTextSize(30);
		investigationResult.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
		investigationResult.setText(investigationResult.getText() + "Investigation: " + reportCategoryString);
		resultForm.addView(investigationResult);

		TextView accuracyRate = new TextView(this);
		accuracyRate.setTextColor(Color.GREEN);
		accuracyRate.setBackgroundColor(Color.BLACK);
		accuracyRate.setTextSize(20);
		accuracyRate.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
		try
		{
			accuracyRate.setText(accuracyRate.getText() + "Accuracy Rate: " + new BigDecimal((double) (totalAccuracy / resultList.size()) * 100).setScale(2, BigDecimal.ROUND_CEILING) + "%");
		}
		catch (NumberFormatException e)
		{
			accuracyRate.setText(accuracyRate.getText() + "Accuracy Rate: " + ((double) (totalAccuracy / resultList.size()) * 100) + "%");
		}
		resultForm.addView(accuracyRate);

		TextView notice = new TextView(this);
		notice.setBackgroundColor(Color.parseColor("#f8ff1a"));
		notice.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
		notice.setText("\n(*Yellow marked fields may have detected wrong value.)\n");
		resultForm.addView(notice);


		//Patient Name
		TextView nameTextView = new TextView(this);
		nameTextView.setTextSize(22);
		nameTextView.setText("Patient Name:");
		nameInputEditText = new TextInputEditText(this);
		nameInputEditText.setText(patientName);
		nameInputEditText.setTypeface(nameInputEditText.getTypeface(), Typeface.BOLD);
		nameInputEditText.setTextSize(22);
		nameInputEditText.setBackgroundColor(Color.GRAY);
		resultForm.addView(nameTextView);
		resultForm.addView(nameInputEditText);

		//Patient Age
		TextView ageTextView = new TextView(this);
		ageTextView.setTextSize(22);
		ageTextView.setText("Patient Age:");
		ageInputEditText = new TextInputEditText(this);
		ageInputEditText.setText(patientAge);
		ageInputEditText.setTextSize(22);
		ageInputEditText.setBackgroundColor(Color.GRAY);
		resultForm.addView(ageTextView);
		resultForm.addView(ageInputEditText);


		//Generating report attribute-value fields
		attributeTextViewList.clear();
		valueTextInputEditTextList.clear();
		for (int i = 0; i < resultList.size(); i++)
		{
			attributeTextViewList.add(new TextView(this));
			attributeTextViewList.get(i).setTextSize(18);
			attributeTextViewList.get(i).setText(resultList.get(i).getAttribute() + ":");

			valueTextInputEditTextList.add(new TextInputEditText(this));
			valueTextInputEditTextList.get(i).setText(resultList.get(i).getValue());
			if (resultList.get(i).getAccuracy() == 0)
			{
				valueTextInputEditTextList.get(i).setBackgroundColor(Color.parseColor("#f8ff1a"));
			}

			resultForm.addView(attributeTextViewList.get(i));
			resultForm.addView(valueTextInputEditTextList.get(i));
		}

		TextView endOfTheForm = new TextView(this);
		endOfTheForm.setText("\n\n********** THE END ***********\n\n");
		endOfTheForm.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
		resultForm.addView(endOfTheForm);

		resultForm.addView(rawScanedResults);

	}

	private void takePicture()
	{
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		String path = "/sdcard/Android/data/com.ranadepto.smartehr";
		String fileName = "SmartEHR" + ".png";
		File direct = new File(Environment.getExternalStorageDirectory() + path);

		if (!direct.exists())
		{
			File reportImageDirectory = new File(path);
			reportImageDirectory.mkdirs();
		}

		File photo = new File(path, fileName);
		imagePath = photo.getPath();
		imageName = photo.getName();

		imageUri = FileProvider.getUriForFile(ScanReportActivity.this, BuildConfig.APPLICATION_ID + ".provider", photo);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(intent, PHOTO_REQUEST);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		if (imageUri != null)
		{
			outState.putString(SAVED_INSTANCE_URI, imageUri.toString());
			outState.putString(SAVED_INSTANCE_RESULT, rawScanedResults.getText().toString());
		}
		super.onSaveInstanceState(outState);
	}

	private void launchMediaScanIntent()
	{
		Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		mediaScanIntent.setData(imageUri);
		this.sendBroadcast(mediaScanIntent);
	}

	private Bitmap decodeBitmapUri(Context ctx, Uri uri) throws IOException
	{
		int targetW = 600;
		int targetH = 600;
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(uri), null, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;

		int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;


		ExifInterface exif = null;
		try
		{
			exif = new ExifInterface(imagePath);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

		Matrix matrix = new Matrix();
		if (rotation == 6)
		{
			matrix.preRotate(90);
		}
		else if (rotation == 8)
		{
			matrix.preRotate(-90);
		}
		else if (rotation == 3)
		{
			matrix.preRotate(180);
		}

		Bitmap originalBitmap = BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(uri), null, bmOptions);
		Bitmap rotatedBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.getWidth(), originalBitmap.getHeight(), matrix, true);

		return rotatedBitmap;
	}


	ReportModel retrofitReportModel;
	private void uploadFile(ReportModel reportModel)
	{
		retrofitReportModel=reportModel;

		//creating a file
		File file = new File(reportModel.getImagePath());

		//The gson builder
		Gson gson = new GsonBuilder().setLenient().create();

		OkHttpClient client = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).writeTimeout(20, TimeUnit.SECONDS).build();

		//creating retrofit object
		Retrofit retrofit = new Retrofit.Builder().baseUrl(RetrofitAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).client(client).build();

		//creating our api
		RetrofitAPI api = retrofit.create(RetrofitAPI.class);

		MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

		Call<UploadFileResponse> call = api.uploadImage(filePart);


		//finally performing the call
		call.enqueue(new Callback<UploadFileResponse>()
		{
			@Override
			public void onResponse(Call<UploadFileResponse> call, Response<UploadFileResponse> response)
			{
				if (response.body() != null)
				{
					Toast.makeText(getApplicationContext(), "File Uploaded Successfully...", Toast.LENGTH_LONG).show();
					saveReportRetrofit(retrofitReportModel, response.body().getFileDownloadUri());
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Some error occurred...", Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onFailure(Call<UploadFileResponse> call, Throwable t)
			{
				Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
			}
		});
	}

	private void saveReportRetrofit(ReportModel reportModel, String uploadedImagePath)
	{
		//creating a file
		File file = new File(imagePath);

		//The gson builder
		Gson gson = new GsonBuilder().setLenient().create();

		OkHttpClient client = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).writeTimeout(20, TimeUnit.SECONDS).build();

		//creating retrofit object
		Retrofit retrofit = new Retrofit.Builder().baseUrl(RetrofitAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).client(client).build();

		//creating our api
		RetrofitAPI api = retrofit.create(RetrofitAPI.class);

		reportModel.setImagePath(uploadedImagePath);
		Call<ReportResponse> call = api.uploadReport(new ReportResponse(reportModel.getId(), reportModel.getPatientName(), reportModel.getPatientAge(), reportModel.getImagePath(), reportModel.getReportCategory()));


		//finally performing the call
		call.enqueue(new Callback<ReportResponse>()
		{
			@Override
			public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response)
			{
				if (response.body() != null)
				{
					Toast.makeText(getApplicationContext(), "Report Uploaded Successfully...", Toast.LENGTH_LONG).show();
					System.out.println("----------\n" + response.body());
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Some error occurred...", Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onFailure(Call<ReportResponse> call, Throwable t)
			{
				Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
			}
		});
	}

}
