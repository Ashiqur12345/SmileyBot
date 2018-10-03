package com.betabots.team.smileybot.NotMine;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.betabots.team.smileybot.NotMine.database_package.Report.ReportCustomAdapter;
import com.betabots.team.smileybot.NotMine.database_package.Report.ReportModel;
import com.betabots.team.smileybot.NotMine.database_package.Report.ReportRepository;
import com.betabots.team.smileybot.NotMine.database_package.SmartEHRDatabase;
import com.betabots.team.smileybot.Person;
import com.betabots.team.smileybot.R;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
	public static Application application;
	public static Context context;
	private static com.betabots.team.smileybot.NotMine.database_package.Report.ReportRepository reportRepository;
	private static TextView noSavedReportTextView;
	//Cardview
	private static RecyclerView.Adapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private static RecyclerView recyclerView;
	public static View.OnClickListener onClickListenerEditReportPage;

	Person patient;


	private LoadReportsForCardViewAsyncTask loadReportsForCardViewAsyncTask;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_2);
		application=getApplication();
		context=MainActivity.this;

		patient = (Person) getIntent().getSerializableExtra("PATIENT");

		onClickListenerEditReportPage = new OnClickListenerEditReportPage(this);

		recyclerView = findViewById(R.id.my_recycler_view);
		recyclerView.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		noSavedReportTextView = findViewById(R.id.noSavedReport);

		String args[] = new String[10];
		new LoadReportsForCardViewAsyncTask().execute(args);

		//Load image from gallery to scan
		FloatingActionButton refreshFloatingBtn = findViewById(R.id.refreshBtn);
		refreshFloatingBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				loadReportsIntoCardView();
			}
		});

		//Load image from gallery to scan
		@SuppressLint("WrongViewCast") FloatingActionButton galleryFloatingBtn = findViewById(R.id.galleryBtn);
		galleryFloatingBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				try
				{
					Intent myIntent = new Intent(MainActivity.this, ScanReportActivity.class);
					myIntent.putExtra("PATIENT", patient);
					myIntent.putExtra("action", "Gallery"); //Optional parameters
					MainActivity.this.startActivity(myIntent);
				}
				catch (Exception e)
				{
					System.out.print(e.getMessage());
				}
			}
		});

		//Scan image with camera to scan
		@SuppressLint("WrongViewCast") FloatingActionButton captureFloatingBtn = findViewById(R.id.captureBtn);
		captureFloatingBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				try
				{
					Intent myIntent = new Intent(MainActivity.this, ScanReportActivity.class);
					myIntent.putExtra("PATIENT", patient);
					myIntent.putExtra("action", "Capture"); //Optional parameters
					MainActivity.this.startActivity(myIntent);
				}
				catch (Exception e)
				{
					System.out.print(e.getMessage());
				}
			}
		});

	}


	public static void loadReportsIntoCardView()
	{
		reportRepository = new ReportRepository(MainActivity.application);
		List<ReportModel> allReportModels = reportRepository.getAllReportModels();
		adapter = new ReportCustomAdapter(allReportModels);
		recyclerView.setAdapter(adapter);
		if(allReportModels.size()>0)
		{
			noSavedReportTextView.setVisibility(View.INVISIBLE);
		}
		else
		{
			noSavedReportTextView.setVisibility(View.VISIBLE);
		}
	}


	@Override
	protected void onDestroy()
	{
		SmartEHRDatabase.destroyInstance();
		super.onDestroy();
	}

	private static class LoadReportsForCardViewAsyncTask extends AsyncTask<String, Void, Void>
	{
		@Override
		protected Void doInBackground(String... args)
		{
			reportRepository = new ReportRepository(MainActivity.application);
			List<ReportModel> allReportModels = reportRepository.getAllReportModels();
			adapter = new ReportCustomAdapter(allReportModels);
			recyclerView.setAdapter(adapter);
			if(allReportModels.size()>0)
			{
				noSavedReportTextView.setVisibility(View.INVISIBLE);
			}
			else
			{
				noSavedReportTextView.setVisibility(View.VISIBLE);
			}
			return null;
		}
	}

	public static class OnClickListenerEditReportPage implements View.OnClickListener
	{
		private final Context context;

		private OnClickListenerEditReportPage(Context context)
		{
			this.context = context;
		}

		@Override
		public void onClick(View v)
		{
			try
			{
				int selectedItemPosition = recyclerView.getChildPosition(v);
				RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForPosition(selectedItemPosition);
				TextView textViewReportID = viewHolder.itemView.findViewById(R.id.textViewReportID);

				Intent myIntent = new Intent(context, ScanReportActivity.class);
				myIntent.putExtra("id", textViewReportID.getText().toString()); //Optional parameters
				context.startActivity(myIntent);

			}
			catch (Exception e)
			{
				System.out.print(e.getMessage());
			}
		}
	}


}