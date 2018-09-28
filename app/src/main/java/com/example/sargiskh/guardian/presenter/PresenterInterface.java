package com.example.sargiskh.guardian.presenter;

public interface PresenterInterface {

    void getDataByPage(int pageNumber);
    void getDataSearchedByDate(String fromDate, String toDate);
    void getDataSearchedByPhrase(String stringToSearch);

}
