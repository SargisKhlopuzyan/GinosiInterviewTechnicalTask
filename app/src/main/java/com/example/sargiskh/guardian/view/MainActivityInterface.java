package com.example.sargiskh.guardian.view;

import com.example.sargiskh.guardian.network.DeveloperResponse;

public interface MainActivityInterface {

    void updateRecyclerView(DeveloperResponse developerResponse);
    void displayError(String errorMessage);
    void showToast(String message);

}
