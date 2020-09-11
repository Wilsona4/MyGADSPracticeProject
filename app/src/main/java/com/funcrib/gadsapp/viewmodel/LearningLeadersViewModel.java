package com.funcrib.gadsapp.viewmodel;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.funcrib.gadsapp.repository.data.DataResponseCallback;
import com.funcrib.gadsapp.repository.data.GadsDataService;
import com.funcrib.gadsapp.model.LearningLeaderModel;

import java.util.List;
//import java.util.logging.Handler;

public class LearningLeadersViewModel extends ViewModel {
    private Handler handler = new Handler();
    private MutableLiveData<List<LearningLeaderModel>> learningLeaders;
    private MutableLiveData<Boolean> error = new MutableLiveData<>(false);

    public LiveData<List<LearningLeaderModel>> getLearningLeaders() {
        if (learningLeaders == null) {
            learningLeaders = new MutableLiveData<>();
            refreshList();
        }
        return learningLeaders;
    }

    public LiveData<Boolean> getError() {
        return error;
    }

    public void refreshList() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GadsDataService.getLearningLeaders(new DataResponseCallback<List<LearningLeaderModel>>() {
                    @Override
                    public void onResponse(List<LearningLeaderModel> response) {
                        learningLeaders.setValue(response);
                        error.setValue(false);
                    }

                    @Override
                    public void onError(Throwable t) {
                        error.setValue(true);
                    }
                });
            }
        }, 1000);
    }
}