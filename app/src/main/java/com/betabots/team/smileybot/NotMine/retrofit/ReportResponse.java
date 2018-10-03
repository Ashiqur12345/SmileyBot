package com.betabots.team.smileybot.NotMine.retrofit;

public class ReportResponse
{
	private String id;
	private String patientName;
	private String patientAge;
	private String imagePath;
	private String reportCategory;

	public ReportResponse(String id, String patientName, String patientAge, String imagePath, String reportCategory)
	{
		this.id = id;
		this.patientName = patientName;
		this.patientAge = patientAge;
		this.imagePath = imagePath;
		this.reportCategory = reportCategory;
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
}
