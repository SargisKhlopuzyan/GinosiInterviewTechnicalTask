package com.example.sargiskh.guardian.presenter;

import com.example.sargiskh.guardian.network.DeveloperResponse;
import com.example.sargiskh.guardian.network.APIService;
import com.example.sargiskh.guardian.network.RetrofitClientInstance;
import com.example.sargiskh.guardian.view.MainActivityInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Presenter implements PresenterInterface {

    MainActivityInterface mainActivityInterface;
    DeveloperResponse developerResponse;

    public Presenter(MainActivityInterface mainActivityInterface) {
        this.mainActivityInterface = mainActivityInterface;
    }


    @Override
    public void getDataByPage(int pageNumber) {
        /*Create handle for the RetrofitInstance interface*/
        APIService service = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
        Call<DeveloperResponse> call = service.getDataSearchedByPage(getUrlForSearchingByPage(pageNumber));
        call.enqueue(new Callback<DeveloperResponse>() {
            @Override
            public void onResponse(Call<DeveloperResponse> call, Response<DeveloperResponse> response) {
                developerResponse = response.body();
                DataController.getInstance().putData(response.body());
                //2nd approach
//                mainActivityInterface.updateRecyclerView(response.body());
            }

            @Override
            public void onFailure(Call<DeveloperResponse> call, Throwable t) {
                mainActivityInterface.displayError(t.getMessage());
            }
        });
    }

    @Override
    public void getDataSearchedByDate(String fromDate, String toDate) {
        /*Create handle for the RetrofitInstance interface*/
        APIService service = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
        Call<DeveloperResponse> call = service.getDataSearchedByDate(getUrlForSearchingByDate(fromDate, toDate));
        call.enqueue(new Callback<DeveloperResponse>() {
            @Override
            public void onResponse(Call<DeveloperResponse> call, Response<DeveloperResponse> response) {
                developerResponse = response.body();
                DataController.getInstance().putData(response.body());
                //2nd approach
//                mainActivityInterface.updateRecyclerView(response.body());
            }

            @Override
            public void onFailure(Call<DeveloperResponse> call, Throwable t) {
                mainActivityInterface.displayError(t.getMessage());
            }
        });
    }

    @Override
    public void getDataSearchedByPhrase(String phrase) {
        /*Create handle for the RetrofitInstance interface*/
        APIService service = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
        Call<DeveloperResponse> call = service.getDataSearchedByPhrase(getUrlForSearchingByPhrase(phrase));
        call.enqueue(new Callback<DeveloperResponse>() {
            @Override
            public void onResponse(Call<DeveloperResponse> call, Response<DeveloperResponse> response) {
                developerResponse = response.body();
                DataController.getInstance().putData(response.body());
                //2nd approach
//                mainActivityInterface.updateRecyclerView(response.body());
            }

            @Override
            public void onFailure(Call<DeveloperResponse> call, Throwable t) {
                mainActivityInterface.displayError(t.getMessage());
            }
        });
    }


    private String getUrlForSearchingByPage(int pageNumber) {
        String url = "https://content.guardianapis.com/culture/series/ranked?page-size=" + pageNumber + "&api-key=c192f963-b123-4413-9d94-a5d10324c59d";
        return url;
    }

    private String getUrlForSearchingByDate(String fromDate, String toDate) {
        String url = "http://content.guardianapis.com/search?from-date=" + fromDate + "&" + toDate + "=2017-10-31&page-size=200&show-tags=all&order-by=newest&page=1&api-key=c192f963-b123-4413-9d94-a5d10324c59d";
//        String url = "http://content.guardianapis.com/search?from-date=2017-10-31&to-date=2017-10-31&page-size=200&show-tags=all&order-by=newest&page=1&api-key=c192f963-b123-4413-9d94-a5d10324c59d";
        return url;
    }

    private String getUrlForSearchingByPhrase(String phrase) {
        phrase = phrase.trim();
        String changedPhrase = phrase.replaceAll("\\s", "%20");
        String url = "/search?q=" + changedPhrase + "&tag=politics/politics&from-date=2014-01-01&api-key=c192f963-b123-4413-9d94-a5d10324c59d";
        return url;
    }

}
