package com.betabots.team.smileybot.NotMine.database_package.ReportResult;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "ReportResult")
public class ReportResultModel
{
	@PrimaryKey(autoGenerate = true)
	int id;

	@ColumnInfo(name = "imageId")
	String imageId;

	@ColumnInfo(name = "attribute")
	String attribute;

	@ColumnInfo(name = "value")
	String value;

	@ColumnInfo(name = "accuracy")
	double accuracy;

	public ReportResultModel(int id, String imageId, String attribute, String value, double accuracy)
	{

		this.id = id;
		this.imageId = imageId;
		this.attribute = attribute;
		this.value = value;
		this.accuracy = accuracy;
	}

	public int getId()
	{
		return id;
	}

	public String getImageId()
	{
		return imageId;
	}

	public String getAttribute()
	{
		return attribute;
	}

	public String getValue()
	{
		return value;
	}

	public double getAccuracy()
	{
		return accuracy;
	}

	public void setId(int id)
	{

		this.id = id;
	}

	public void setImageId(String imageId)
	{
		this.imageId = imageId;
	}

	public void setAttribute(String attribute)
	{
		this.attribute = attribute;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public void setAccuracy(double accuracy)
	{
		this.accuracy = accuracy;
	}
}
