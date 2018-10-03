/*
    
    Created by: Rana Depto  
    
    Email: mail@ranadepto.com
    
    Date: 6/10/18, Time: 1:55 PM
    
*/

package com.betabots.team.smileybot.NotMine.database_package.Report;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ReportDAO
{
	@Query("SELECT * FROM Report ORDER BY id DESC")
	List<ReportModel> getAll();

	@Query("SELECT * FROM Report where id LIKE  :id")
	ReportModel findByID(String id);

	@Query("SELECT COUNT(*) from Report")
	int countReport();

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(ReportModel reportModel);

	@Insert
	void insertAll(ReportModel... reportModels);

	@Delete
	void delete(ReportModel reportModel);

	@Update
	void update(ReportModel reportModel);
}