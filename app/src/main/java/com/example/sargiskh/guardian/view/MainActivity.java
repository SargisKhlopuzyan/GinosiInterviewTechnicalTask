package com.example.sargiskh.guardian.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.sargiskh.guardian.R;
import com.example.sargiskh.guardian.network.DeveloperResponse;
import com.example.sargiskh.guardian.model.Response;
import com.example.sargiskh.guardian.presenter.DataController;
import com.example.sargiskh.guardian.presenter.Presenter;
import com.example.sargiskh.guardian.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MainActivityInterface {

    private Presenter presenter;

    private SearchView searchView;
    private ProgressBar progressBar;
    private TextView textViewStatus;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new Presenter(this);

        findViews();
        setupListPeopleView();

        Log.e("LOG_TAG", "monthsCount: " + monthsCount);

//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        LiveData<DeveloperResponse> liveData = DataController.getInstance().getData();

        liveData.observe(this, new Observer<DeveloperResponse>() {
            @Override
            public void onChanged(@Nullable DeveloperResponse value) {
                updateRecyclerView(value);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("LOG_TAG", "query: " + query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    getDataByDate();
                } else {
                    presenter.getDataSearchedByPhrase(newText);
                }
                return true;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        searchView.setOnQueryTextListener(null);
    }


    private void findViews() {
        searchView = findViewById(R.id.searchView);
        progressBar = findViewById(R.id.progressBar);
        textViewStatus = findViewById(R.id.textViewStatus);
        recyclerView = findViewById(R.id.recyclerView);
    }

    public void setupListPeopleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DataAdapter adapter = new DataAdapter();
        recyclerView.setAdapter(adapter);
    }

    int monthsCount = 0;
    public void onClickFabLoad(View view) {
        --monthsCount;
        getDataByDate();
    }

    private void getDataByDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMATTER, Locale.getDefault());
        Calendar calendar = Calendar.getInstance();

        Date toDate = calendar.getTime();
        String toDateStr = sdf.format(toDate);

        calendar.add(Calendar.MONTH, monthsCount);
        Date fromDate = calendar.getTime();
        String fromDateStr = sdf.format(fromDate);

        Log.e("LOG_TAG", "toDateStr: " + toDateStr + "   :  " + "fromDateStr: " + fromDateStr);
//        presenter.getDataSearchedByDate("2017-10-31", "2017-10-31");
        presenter.getDataSearchedByDate(fromDateStr, toDateStr);
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void changeData(Response response) {
        DataAdapter dataAdapter = (DataAdapter) recyclerView.getAdapter();
        dataAdapter.setResults(response.results);
    }

    @Override
    public void updateRecyclerView(DeveloperResponse developerResponse) {
        changeData(developerResponse.getResponse());
        progressBar.setVisibility(View.GONE);
        textViewStatus.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayError(String errorMessage) {
        textViewStatus.setText("Something went wrong...Please try later! : " + errorMessage);
        progressBar.setVisibility(View.GONE);
        textViewStatus.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showToast(String toastMessage) {

    }

    @Override
    public void onBackPressed() {
        int firstVisibleItemPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        if (firstVisibleItemPosition == 0 || firstVisibleItemPosition == -1) {
            super.onBackPressed();
        } else {
            recyclerView.smoothScrollToPosition(0);
        }
    }

}
