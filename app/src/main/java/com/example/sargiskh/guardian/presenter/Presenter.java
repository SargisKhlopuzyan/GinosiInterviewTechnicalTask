package com.example.sargiskh.guardian.presenter;

import com.example.sargiskh.guardian.network.DataResponse;
import com.example.sargiskh.guardian.network.APIService;
import com.example.sargiskh.guardian.network.RetrofitClientInstance;
import com.example.sargiskh.guardian.utils.Constants;
import com.example.sargiskh.guardian.view.MainActivityInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Presenter implements PresenterInterface {

    MainActivityInterface mainActivityInterface;

    public Presenter(MainActivityInterface mainActivityInterface) {
        this.mainActivityInterface = mainActivityInterface;
    }

    @Override
    public void getDataSearchedByPage(int pageIndex) {
        /*Create handle for the RetrofitInstance interface*/
        APIService service = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
        Call<DataResponse> call = service.getDataSearchedByPage(getUrlForSearchingByPage(pageIndex));
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if (DataController.getInstance().getLoadedTries() == 1) {
                    DataController.getInstance().setData(response.body());
                } else {
                    DataController.getInstance().addData(response.body());
                }
                //2nd approach //In this case we can directly update view. this time we need handle orientation changes
//                mainActivityInterface.updateRecyclerView(response.body());
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                mainActivityInterface.displayError(t.getMessage());
            }
        });
    }

    @Override
    public void getDataSearchedByPhrase(String phrase) {

        /*Create handle for the RetrofitInstance interface*/
        APIService service = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
        Call<DataResponse> call = service.getDataSearchedByPhrase(getUrlForSearchingByPhrase(phrase));
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                DataController.getInstance().setData(response.body());

                //2nd approach //In this case we can directly update view. this time we need handle orientation changes
//                mainActivityInterface.updateRecyclerView(response.body());
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                mainActivityInterface.displayError(t.getMessage());
            }
        });
    }

    private String getUrlForSearchingByPage(int pageNumber) {

        //NOTE - page-size=10 //this is just to show more articles
        //To search page by page we can use this: https://content.guardianapis.com/search?order-by=newest&page=" + pageNumber + "&api-key=" + Constants.API_KEY

        // In every try the application loads 10 pages
        pageNumber = pageNumber * 10 - 9;

        String url = "https://content.guardianapis.com/search?order-by=newest&page=" + pageNumber + "&page-size=10&api-key=" + Constants.API_KEY;
        return url;
    }

    private String getUrlForSearchingByPhrase(String phrase) {
        phrase = phrase.trim();
        String changedPhrase = phrase.replaceAll("\\s", "%20");
        String url = "https://content.guardianapis.com/search?q=" + changedPhrase + "&api-key=" + Constants.API_KEY;
        return url;
    }

}
