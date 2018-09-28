package com.example.sargiskh.guardian.view;

import com.example.sargiskh.guardian.network.DataResponse;

public interface MainActivityInterface {
    void updateRecyclerView(DataResponse dataResponse);
    void displayError(String errorMessage);
    void showToast(String message);
}
