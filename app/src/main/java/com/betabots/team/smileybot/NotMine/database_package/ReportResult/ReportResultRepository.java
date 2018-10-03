package com.betabots.team.smileybot.NotMine.database_package.ReportResult;

import android.app.Application;
import android.os.AsyncTask;


import com.betabots.team.smileybot.NotMine.database_package.SmartEHRDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ReportResultRepository
{
	private ReportResultDAO ReportResultDAO;
	private List<ReportResultModel> allReportResultModels;

	public ReportResultRepository(Application application)
	{
		SmartEHRDatabase db = SmartEHRDatabase.getDatabase(application);
		ReportResultDAO = db.reportResultDAO();
		allReportResultModels = ReportResultDAO.getAll();
	}

	public List<ReportResultModel> getAllReportResultModels()
	{
		return allReportResultModels;
	}

	public void insert(ReportResultModel ReportResultModel)
	{
		new insertAsyncTask(ReportResultDAO).execute(ReportResultModel);
	}

	public List<ReportResultModel> findById(String reportId)
	{
		List<ReportResultModel> reportResultModel = null;
		try
		{
			reportResultModel = new findByIdAsyncTask(ReportResultDAO).execute(reportId).get();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		catch (ExecutionException e)
		{
			e.printStackTrace();
		}
		return reportResultModel;
	}

	public void insertAll(List<ReportResultModel> ReportResultModels)
	{
		new insertAllAsyncTask(ReportResultDAO).execute(ReportResultModels);
	}

	public void delete(ReportResultModel ReportResultModel)
	{
		new deleteAsyncTask(ReportResultDAO).execute(ReportResultModel);
	}

	public void deleteAll(String reportId)
	{
		new deleteAllAsyncTask(ReportResultDAO).execute(reportId);
	}

	public void update(ReportResultModel ReportResultModel)
	{
		new updateAsyncTask(ReportResultDAO).execute(ReportResultModel);
	}

	public void updateAll(List<ReportResultModel> ReportResultModels)
	{
		new updateAllAsyncTask(ReportResultDAO).execute(ReportResultModels);
	}

	private static class insertAllAsyncTask extends AsyncTask<List<ReportResultModel>, Void, Void>
	{

		private ReportResultDAO asyncTaskDaos;

		public insertAllAsyncTask(ReportResultDAO reportResultDAO)
		{
			asyncTaskDaos = reportResultDAO;
		}

		@Override
		protected Void doInBackground(List<ReportResultModel>[] lists)
		{
			asyncTaskDaos.insertAll(lists[0]);
			return null;
		}
	}

	private static class insertAsyncTask extends AsyncTask<ReportResultModel, Void, Void>
	{

		private ReportResultDAO asyncTaskDao;

		insertAsyncTask(ReportResultDAO dao)
		{
			asyncTaskDao = dao;
		}

		@Override
		protected Void doInBackground(ReportResultModel... reportResultModels)
		{
			asyncTaskDao.insert(reportResultModels[0]);
			return null;
		}
	}

	private static class deleteAsyncTask extends AsyncTask<ReportResultModel, Void, Void>
	{

		private ReportResultDAO asyncTaskDao;

		deleteAsyncTask(ReportResultDAO dao)
		{
			asyncTaskDao = dao;
		}

		@Override
		protected Void doInBackground(final ReportResultModel... params)
		{
			asyncTaskDao.delete(params[0]);
			return null;
		}
	}

	private static class deleteAllAsyncTask extends AsyncTask<String, Void, Void>
	{

		private ReportResultDAO asyncTaskDaos;

		public deleteAllAsyncTask(ReportResultDAO reportResultDAO)
		{
			asyncTaskDaos = reportResultDAO;
		}

		@Override
		protected Void doInBackground(String[] lists)
		{
			asyncTaskDaos.deleteAll(lists[0]);
			return null;
		}
	}

	private static class updateAsyncTask extends AsyncTask<ReportResultModel, Void, Void>
	{

		private ReportResultDAO asyncTaskDao;

		updateAsyncTask(ReportResultDAO dao)
		{
			asyncTaskDao = dao;
		}

		@Override
		protected Void doInBackground(final ReportResultModel... params)
		{
			asyncTaskDao.update(params[0]);
			return null;
		}
	}

	private static class updateAllAsyncTask extends AsyncTask<List<ReportResultModel>, Void, Void>
	{

		private ReportResultDAO asyncTaskDaos;

		public updateAllAsyncTask(ReportResultDAO reportResultDAO)
		{
			asyncTaskDaos = reportResultDAO;
		}

		@Override
		protected Void doInBackground(List<ReportResultModel>[] lists)
		{
			asyncTaskDaos.updateAll(lists[0]);
			return null;
		}
	}

	private static class findByIdAsyncTask extends AsyncTask<String, Void, List<ReportResultModel>>
	{

		private ReportResultDAO asyncTaskDao;

		findByIdAsyncTask(ReportResultDAO dao)
		{
			asyncTaskDao = dao;
		}

		protected List<ReportResultModel> doInBackground(final String params)
		{
			return asyncTaskDao.findAllByImageId(params);
		}

		@Override
		protected List<ReportResultModel> doInBackground(String... reportResultModels)
		{
			return asyncTaskDao.findAllByImageId(reportResultModels[0]);
		}
	}

}
