/*
    
    Created by: Rana Depto  
    
    Email: mail@ranadepto.com
    
    Date: 6/10/18, Time: 1:59 PM
    
*/

package com.betabots.team.smileybot.NotMine.database_package;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.betabots.team.smileybot.NotMine.database_package.Report.ReportDAO;
import com.betabots.team.smileybot.NotMine.database_package.Report.ReportModel;
import com.betabots.team.smileybot.NotMine.database_package.ReportResult.ReportResultDAO;
import com.betabots.team.smileybot.NotMine.database_package.ReportResult.ReportResultModel;

@Database(entities = {ReportModel.class, ReportResultModel.class}, version = 1)
public abstract class SmartEHRDatabase extends RoomDatabase
{
	private static SmartEHRDatabase INSTANCE;

	public abstract ReportDAO reportDAO();

	public abstract ReportResultDAO reportResultDAO();

	public static SmartEHRDatabase getDatabase(Context context)
	{
		if (INSTANCE == null)
		{
			INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SmartEHRDatabase.class, "SmartEHRDatabase")
					// allow queries on the main thread.
					// Don't do this on a real app! See PersistenceBasicSample for an example.
					.allowMainThreadQueries().fallbackToDestructiveMigration().build();
		}
		return INSTANCE;
	}

	public static void destroyInstance()
	{
		INSTANCE = null;
	}
}
