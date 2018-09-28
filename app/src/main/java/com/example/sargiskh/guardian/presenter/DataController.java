package com.example.sargiskh.guardian.presenter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.sargiskh.guardian.network.DeveloperResponse;

public class DataController {

    private static DataController instance = null;

    private DataController() { }

    public static DataController getInstance() {
        if (instance == null) {
            synchronized (DataController.class) {
                if (instance == null) {
                    instance = new DataController();
                }
            }
        }
        return instance;
    }


    private MutableLiveData<DeveloperResponse> liveData = new MutableLiveData<>();

    public LiveData<DeveloperResponse> getData() {
        return liveData;
    }

    public void putData(DeveloperResponse data) {
        liveData.setValue(data);
    }

}
