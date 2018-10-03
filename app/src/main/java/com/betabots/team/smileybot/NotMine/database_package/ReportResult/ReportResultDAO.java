package com.betabots.team.smileybot.NotMine.database_package.ReportResult;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ReportResultDAO
{
	@Query("SELECT * FROM ReportResult")
	List<ReportResultModel> getAll();

	@Query("SELECT * FROM ReportResult where imageId LIKE  :imageId")
	List<ReportResultModel> findAllByImageId(String imageId);

	@Query("SELECT COUNT(*) from ReportResult")
	int countReportResult();

	@Insert
	void insert(ReportResultModel ReportResultModel);

	@Insert
	void insertAll(List<ReportResultModel> ReportResultModels);

	@Delete
	void delete(ReportResultModel ReportResultModel);

	@Query("DELETE FROM ReportResult where imageId LIKE  :imageId")
	void deleteAll(String imageId);

	@Update
	void update(ReportResultModel ReportResultModel);

	@Update
	void updateAll(List<ReportResultModel> ReportResultModels);

}