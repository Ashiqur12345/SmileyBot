package com.betabots.team.smileybot.NotMine.database_package.Report;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import com.betabots.team.smileybot.NotMine.Email;
import com.betabots.team.smileybot.NotMine.MainActivity;
import com.betabots.team.smileybot.R;
import com.betabots.team.smileybot.NotMine.ScanReportActivity;
import com.betabots.team.smileybot.NotMine.database_package.ReportResult.ReportResultModel;
import com.betabots.team.smileybot.NotMine.database_package.ReportResult.ReportResultRepository;

import java.io.File;
import java.util.List;

import static com.betabots.team.smileybot.NotMine.MainActivity.context;
import static com.betabots.team.smileybot.NotMine.database_package.Report.ReportCustomAdapter.MyViewHolder.*;


public class ReportCustomAdapter extends RecyclerView.Adapter<ReportCustomAdapter.MyViewHolder>
{

	private List<ReportModel> reportsList;

	public static class MyViewHolder extends RecyclerView.ViewHolder
	{

		TextView textViewName;
		TextView textViewVersion;
		ImageView imageViewIcon;
		TextView textViewOptions;
		TextView textViewDate;
		TextView textViewReportID;

		static String emailAddress;
		static String emailSubject;
		static String emailBody;
		static ProgressDialog mProgressDialog;

		public MyViewHolder(View itemView)
		{
			super(itemView);
			this.textViewName = (TextView) itemView.findViewById(R.id.textViewNameAndAge);
			this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewCategory);
			this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
			this.textViewOptions = (TextView) itemView.findViewById(R.id.textViewOptions);
			this.textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
			this.textViewReportID = (TextView) itemView.findViewById(R.id.textViewReportID);
		}
	}

	public ReportCustomAdapter(List<ReportModel> reportModels)
	{
		this.reportsList = reportModels;
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_layout, parent, false);

		view.setOnClickListener(MainActivity.onClickListenerEditReportPage);

		MyViewHolder myViewHolder = new MyViewHolder(view);
		return myViewHolder;
	}

	@Override
	public void onBindViewHolder(final MyViewHolder holder, final int listPosition)
	{

		TextView textViewName = holder.textViewName;
		TextView textViewVersion = holder.textViewVersion;
		ImageView imageView = holder.imageViewIcon;
		final TextView textViewOptions = holder.textViewOptions;
		TextView textViewDate = holder.textViewDate;
		TextView textViewReportID = holder.textViewReportID;

		textViewName.setText(reportsList.get(listPosition).getPatientName() + " (" + reportsList.get(listPosition).getPatientAge() + ")");
		textViewVersion.setText("Investigation: " + reportsList.get(listPosition).getReportCategory());
		imageView.setImageBitmap(BitmapFactory.decodeFile(reportsList.get(listPosition).getImagePath().replace("SmartEHR_", "Thumbnail_SmartEHR_")));
		textViewDate.setText(getDateFromImageName(reportsList.get(listPosition).getId()));
		textViewReportID.setText(reportsList.get(listPosition).getId());
		textViewOptions.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View view)
			{
				//creating a popup menu
				PopupMenu popup = new PopupMenu(view.getContext(), textViewOptions);
				//inflating menu from xml resource
				popup.inflate(R.menu.options_menu);
				//adding click listener
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
				{
					@Override
					public boolean onMenuItemClick(MenuItem item)
					{
						switch (item.getItemId())
						{
							case R.id.shareReportBtn:
							{
								if (!hasConnection())
								{
									Toast.makeText(view.getContext(), "There is no internet connection!", Toast.LENGTH_SHORT).show();
									break;
								}

								// get share_prompts.xml view
								LayoutInflater li = LayoutInflater.from(context);
								View promptsView = li.inflate(R.layout.share_prompts, null);

								AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

								// set share_prompts.xml to alertdialog builder
								alertDialogBuilder.setView(promptsView);

								final EditText nameEditText = (EditText) promptsView.findViewById(R.id.nameEditText);
								final EditText emailEditText = (EditText) promptsView.findViewById(R.id.emailEditText);

								// set dialog message
								alertDialogBuilder.setCancelable(false).setPositiveButton("Share Report", new DialogInterface.OnClickListener()
								{
									public void onClick(DialogInterface dialog, int id)
									{
										if (nameEditText.getText().toString().length() < 4)
										{
											Toast.makeText(view.getContext(), "Please provide your name.", Toast.LENGTH_SHORT).show();
											return;
										}
										if (emailEditText.getText().toString().length() < 4)
										{
											Toast.makeText(view.getContext(), "Please provide recipient's email address.", Toast.LENGTH_SHORT).show();
											return;
										}
										if (!emailEditText.getText().toString().matches("[\\d.\\w]{1,30}[@][\\d.\\w]{2,20}[.][\\d\\w]{2,20}"))
										{
											Toast.makeText(view.getContext(), "Please provide a valid email address.", Toast.LENGTH_SHORT).show();
											return;
										}

										MyViewHolder.emailBody = "";
										MyViewHolder.emailBody = "Hello,\n" + nameEditText.getText().toString() + " has shared a report via SmartEHR app with you.\n";
										MyViewHolder.emailBody += "Here are details:\n\n";
										MyViewHolder.emailBody += "Patient Name: " + reportsList.get(listPosition).getPatientName();
										MyViewHolder.emailBody += "\nPatient Age: " + reportsList.get(listPosition).getPatientAge();
										MyViewHolder.emailBody += "\nInvestigation: " + reportsList.get(listPosition).getReportCategory();
										MyViewHolder.emailBody += "\n\n";
										ReportResultRepository reportResultRepository = new ReportResultRepository(MainActivity.application);
										List<ReportResultModel> reportResultModels = reportResultRepository.findById(reportsList.get(listPosition).getId());
										for (int i = 0; i < reportResultModels.size(); i++)
										{
											MyViewHolder.emailBody += reportResultModels.get(i).getAttribute() + ": " + reportResultModels.get(i).getValue() + "\n";
										}
										MyViewHolder.emailBody += "\n\n-----------------------------\nThanks & Regards,\nThe SmartEHR Team";

										MyViewHolder.mProgressDialog = new ProgressDialog(MainActivity.context);
										MyViewHolder.mProgressDialog.setMessage("Sending, please wait...");
										MyViewHolder.mProgressDialog.setCancelable(true);
										MyViewHolder.mProgressDialog.show();

										MyViewHolder.emailAddress = emailEditText.getText().toString();
										MyViewHolder.emailSubject = "SmartEHR Report Shared By " + nameEditText.getText().toString();

										new SendMailAsyncTask().execute();

									}
								}).setNegativeButton("Cancel", new DialogInterface.OnClickListener()
								{
									public void onClick(DialogInterface dialog, int id)
									{
										dialog.cancel();
									}
								});

								// create alert dialog
								AlertDialog alertDialog = alertDialogBuilder.create();

								// show it
								alertDialog.show();

								break;
							}
							case R.id.editReportBtn:
							{
								try
								{
									Intent myIntent = new Intent(context, ScanReportActivity.class);
									myIntent.putExtra("id", reportsList.get(listPosition).getId()); //Optional parameters
									context.startActivity(myIntent);

								}
								catch (Exception e)
								{
									System.out.print(e.getMessage());
								}

								break;
							}
							case R.id.deleteReportBtn:
							{
								File file;
								try
								{
									file = new File(reportsList.get(listPosition).getImagePath());
									file.delete();
								}
								catch (Exception e)
								{
								}
								try
								{
									file = new File(reportsList.get(listPosition).getImagePath().replace("SmartEHR_", "Thumbnail_SmartEHR_"));
									file.delete();
								}
								catch (Exception e)
								{
								}

								ReportRepository reportRepository = new ReportRepository(MainActivity.application);
								reportRepository.delete(reportsList.get(listPosition));

								ReportResultRepository reportResultRepository = new ReportResultRepository(MainActivity.application);
								reportResultRepository.deleteAll(reportsList.get(listPosition).getId());

								MainActivity.loadReportsIntoCardView();

								Toast.makeText(view.getContext(), "Report has been deleted!", Toast.LENGTH_SHORT).show();
								break;
							}
						}
						return false;
					}
				});
				//displaying the popup
				popup.show();
			}
		});

	}

	private String getDateFromImageName(String imageName)
	{
		try
		{
			String newString = imageName.split("_")[1].substring(0, 4) + "/" + imageName.split("_")[1].substring(4, 6) + "/" + imageName.split("_")[1].substring(6, 8) + "   " + imageName.split("_")[2].substring(0, 2) + ":" + imageName.split("_")[2].substring(2, 4);
			return newString;
		}
		catch (Exception e)
		{
		}

		return imageName;
	}

	public static boolean hasConnection()
	{
		ConnectivityManager cm = (ConnectivityManager) MainActivity.context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiNetwork != null && wifiNetwork.isConnected())
		{
			return true;
		}

		NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mobileNetwork != null && mobileNetwork.isConnected())
		{
			return true;
		}

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected())
		{
			return true;
		}

		return false;
	}

	@Override
	public int getItemCount()
	{
		return reportsList.size();
	}


	private static class SendMailAsyncTask extends AsyncTask<Void, Void, Void>
	{
		@Override
		protected Void doInBackground(Void... voids)
		{
			new Email().send(MyViewHolder.emailAddress, MyViewHolder.emailSubject, MyViewHolder.emailBody);
			MyViewHolder.mProgressDialog.cancel();
			new Handler(Looper.getMainLooper()).post(new Runnable()
			{
				@Override
				public void run()
				{
					Toast.makeText(MainActivity.context, "Report has been shared successfully.", Toast.LENGTH_SHORT).show();
				}
			});
			return null;
		}
	}


}
