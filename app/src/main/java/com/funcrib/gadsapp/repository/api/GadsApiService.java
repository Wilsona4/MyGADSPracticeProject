package com.funcrib.gadsapp.repository.api;

import androidx.annotation.NonNull;

import com.funcrib.gadsapp.model.LearningLeaderModel;
import com.funcrib.gadsapp.model.SkillLeaderModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class GadsApiService {

    public static interface RetrofitInterface {
        @GET("/api/hours")
        Call<List<LearningLeaderModel>> getLearningLeaders();

        @GET("/api/skilliq")
        Call<List<SkillLeaderModel>> getSkillLeaders();
    }

    private static RetrofitInterface retrofitInterface = new Retrofit.Builder()
            .baseUrl("https://gadsapi.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitInterface.class);

    public static void getLearningLeaders(@NonNull final ApiResponseCallback<List<LearningLeaderModel>> callback) {
        retrofitInterface.getLearningLeaders()
                .enqueue(new Callback<List<LearningLeaderModel>>() {
                    @Override
                    public void onResponse(Call<List<LearningLeaderModel>> call, Response<List<LearningLeaderModel>> response) {
                        if (response.isSuccessful())
                            callback.onResponse(response.body());
                        else
                            callback.onError(new ApiResponseError(response));
                    }

                    @Override
                    public void onFailure(Call<List<LearningLeaderModel>> call, Throwable t) {
                        callback.onError(t);
                    }
                });
    }

    public static void getSkillLeaders(@NonNull final ApiResponseCallback<List<SkillLeaderModel>> callback) {
        retrofitInterface.getSkillLeaders()
                .enqueue(new Callback<List<SkillLeaderModel>>() {
                    @Override
                    public void onResponse(Call<List<SkillLeaderModel>> call, Response<List<SkillLeaderModel>> response) {
                        if (response.isSuccessful())
                            callback.onResponse(response.body());
                        else
                            callback.onError(new ApiResponseError(response));
                    }

                    @Override
                    public void onFailure(Call<List<SkillLeaderModel>> call, Throwable t) {
                        callback.onError(t);
                    }
                });
    }
}