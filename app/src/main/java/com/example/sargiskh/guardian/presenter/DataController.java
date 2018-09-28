package com.example.sargiskh.guardian.presenter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.sargiskh.guardian.network.DataResponse;

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


    // LiveData
    private MutableLiveData<DataResponse> liveData = new MutableLiveData<>();

    public LiveData<DataResponse> getData() {
        return liveData;
    }

    public void setData(DataResponse data) {
        liveData.setValue(data);
    }

    public void addData(DataResponse data) {
        if (liveData.getValue() == null) {
            liveData.setValue(data);
        } else {
            liveData.getValue().getResponse().results.addAll(data.getResponse().results);
            liveData.setValue(liveData.getValue());
        }
    }


    //Loaded Pages
    private int loadedTries = 1;

    public void increaseLoadedTriesBy(int count) {
        loadedTries += count;
    }

    public void setLoadedTries(int loadedTries) {
        this.loadedTries = loadedTries;
    }

    public int getLoadedTries() {
        return loadedTries;
    }


    //Is in search by phrase mode
    private boolean isInSearchByPhraseMode = false;

    public boolean isInSearchByPhraseMode() {
        return isInSearchByPhraseMode;
    }

    public void setIsInSearchByPhraseMode(boolean isInSearchByPhraseMode) {
        this.isInSearchByPhraseMode = isInSearchByPhraseMode;
    }

}
