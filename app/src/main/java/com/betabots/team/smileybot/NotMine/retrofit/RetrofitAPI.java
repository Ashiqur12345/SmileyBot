package com.betabots.team.smileybot.NotMine.retrofit;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitAPI {

	String BASE_URL = "https://ranadepto.com/smartehr/";

	@Multipart
	@POST("uploadFile")
	Call<UploadFileResponse> uploadImage(@Part MultipartBody.Part filePart);

	@POST("api/reports")
	Call<ReportResponse> uploadReport(@Body ReportResponse body);
}