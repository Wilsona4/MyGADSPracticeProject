package com.funcrib.gadsapp.repository.data;

import android.os.Handler;

import androidx.annotation.NonNull;

import com.funcrib.gadsapp.repository.api.ApiResponseCallback;
import com.funcrib.gadsapp.repository.api.GadsApiService;
import com.funcrib.gadsapp.model.LearningLeaderModel;
import com.funcrib.gadsapp.model.SkillLeaderModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
//import java.util.logging.Handler;

public class GadsDataService {
    public static Handler handler = new Handler();

    public static void getLearningLeaders(@NonNull final DataResponseCallback<List<LearningLeaderModel>> callback) {
        GadsApiService.getLearningLeaders(new ApiResponseCallback<List<LearningLeaderModel>>() {
            @Override
            public void onResponse(final List<LearningLeaderModel> response) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Collections.sort(response, new Comparator<LearningLeaderModel>() {
                            @Override
                            public int compare(LearningLeaderModel a, LearningLeaderModel b) {
                                return (int)Math.signum( b.getHours()-a.getHours() );
                            }
                        });
                        callback.onResponse(response);
                    }
                });
            }

            @Override
            public void onError(Throwable error) {
                callback.onError(error);
            }
        });
    }

    public static void getSkillLeaders(@NonNull final DataResponseCallback<List<SkillLeaderModel>> callback) {
        GadsApiService.getSkillLeaders(new ApiResponseCallback<List<SkillLeaderModel>>() {
            @Override
            public void onResponse(final List<SkillLeaderModel> response) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Collections.sort(response, new Comparator<SkillLeaderModel>() {
                            @Override
                            public int compare(SkillLeaderModel a, SkillLeaderModel b) {
                                return (int)Math.signum(b.getScore() -a.getScore()  );
                            }
                        });
                        callback.onResponse(response);
                    }
                });
            }

            @Override
            public void onError(final Throwable error) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(error);
                    }
                });
            }
        });
    }
}