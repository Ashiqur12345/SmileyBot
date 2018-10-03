/*
    
    Created by: Rana Depto  
    
    Email: mail@ranadepto.com
    
    Date: 6/10/18, Time: 3:48 PM
    
*/

package com.betabots.team.smileybot.NotMine.database_package.Report;

import android.app.Application;
import android.os.AsyncTask;

import com.betabots.team.smileybot.NotMine.database_package.SmartEHRDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ReportRepository
{

	private ReportDAO reportDAO;
	private List<ReportModel> allReportModels;

	// Note that in order to unit test the WordRepository, you have to remove the Application
	// dependency. This adds complexity and much more code, and this sample is not about testing.
	// See the BasicSample in the android-architecture-components repository at
	// https://github.com/googlesamples
	public ReportRepository(Application application)
	{
		SmartEHRDatabase db = SmartEHRDatabase.getDatabase(application);
		reportDAO = db.reportDAO();
		allReportModels = reportDAO.getAll();
	}

	// Room executes all queries on a separate thread.
	// Observed LiveData will notify the observer when the data has changed.
	public List<ReportModel> getAllReportModels()
	{
		return allReportModels;
	}

	// You must call this on a non-UI thread or your app will crash.
	// Like this, Room ensures that you're not doing any long running operations on the main
	// thread, blocking the UI.
	public void insert(ReportModel reportModel)
	{
		new insertAsyncTask(reportDAO).execute(reportModel);
	}

	public void delete(ReportModel reportModel)
	{
		new deleteAsyncTask(reportDAO).execute(reportModel);
	}

	public void update(ReportModel reportModel)
	{
		new updateAsyncTask(reportDAO).execute(reportModel);
	}

	public ReportModel findById(String reportId)
	{
		ReportModel reportModel = null;
		try
		{
			reportModel = new findByIdAsyncTask(reportDAO).execute(reportId).get();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		catch (ExecutionException e)
		{
			e.printStackTrace();
		}
		return reportModel;

	}

	private static class insertAsyncTask extends AsyncTask<ReportModel, Void, Void>
	{

		private ReportDAO asyncTaskDao;

		insertAsyncTask(ReportDAO dao)
		{
			asyncTaskDao = dao;
		}

		@Override
		protected Void doInBackground(final ReportModel... params)
		{
			asyncTaskDao.insert(params[0]);
			return null;
		}
	}

	private static class deleteAsyncTask extends AsyncTask<ReportModel, Void, Void>
	{

		private ReportDAO asyncTaskDao;

		deleteAsyncTask(ReportDAO dao)
		{
			asyncTaskDao = dao;
		}

		@Override
		protected Void doInBackground(final ReportModel... params)
		{
			asyncTaskDao.delete(params[0]);
			return null;
		}
	}

	private static class updateAsyncTask extends AsyncTask<ReportModel, Void, Void>
	{

		private ReportDAO asyncTaskDao;

		updateAsyncTask(ReportDAO dao)
		{
			asyncTaskDao = dao;
		}

		@Override
		protected Void doInBackground(final ReportModel... params)
		{
			asyncTaskDao.update(params[0]);
			return null;
		}
	}

	private static class findByIdAsyncTask extends AsyncTask<String, Void, ReportModel>
	{

		private ReportDAO asyncTaskDao;

		findByIdAsyncTask(ReportDAO dao)
		{
			asyncTaskDao = dao;
		}

		@Override
		protected ReportModel doInBackground(final String... params)
		{
			return asyncTaskDao.findByID(params[0]);
		}
	}

}
