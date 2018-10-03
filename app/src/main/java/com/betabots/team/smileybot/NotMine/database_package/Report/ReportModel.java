/*
    
    Created by: Rana Depto  
    
    Email: mail@ranadepto.com
    
    Date: 6/10/18, Time: 1:45 PM
    
*/

package com.betabots.team.smileybot.NotMine.database_package.Report;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Report")
public class ReportModel
{

	@PrimaryKey @NonNull
	private String id;

	@ColumnInfo(name = "patientName")
	private String patientName;

	@ColumnInfo(name = "patientAge")
	private String patientAge;

	@ColumnInfo(name = "imagePath")
	private String imagePath;

	@ColumnInfo(name = "reportCategory")
	private String reportCategory;

	@ColumnInfo(name = "status")
	private int status;

/*
	public ReportModel()
	{
	}
*/

	public ReportModel(String id, String patientName, String patientAge, String imagePath, String reportCategory, int status)
	{
		this.id = id;
		this.patientName = patientName;
		this.patientAge = patientAge;
		this.imagePath = imagePath;
		this.reportCategory = reportCategory;
		this.status = status;
	}

	public String getId()
	{
		return id;
	}

	public String getPatientName()
	{
		return patientName;
	}

	public String getPatientAge()
	{
		return patientAge;
	}

	public String getImagePath()
	{
		return imagePath;
	}

	public String getReportCategory()
	{
		return reportCategory;
	}

	public int getStatus()
	{
		return status;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setPatientName(String patientName)
	{
		this.patientName = patientName;
	}

	public void setPatientAge(String patientAge)
	{
		this.patientAge = patientAge;
	}

	public void setImagePath(String imagePath)
	{
		this.imagePath = imagePath;
	}

	public void setReportCategory(String reportCategory)
	{
		this.reportCategory = reportCategory;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}
}