package com.khyzhun.sasha.stupidweatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.khyzhun.sasha.stupidweatherapp.data.model.Query;
import com.khyzhun.sasha.stupidweatherapp.data.model.Weather;
import com.khyzhun.sasha.stupidweatherapp.remote.WeatherAPI;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tvLocation)         TextView  tvLocation;
    @Bind(R.id.tvDataAndTimeToday) TextView  tvDataAndTimeToday;
    @Bind(R.id.tvTemperature)      TextView  tvTemperature;
    @Bind(R.id.tvCurrentWeather)   TextView  tvCurrentWeather;
    @Bind(R.id.buttonRefresh)      Button    buttonRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.buttonRefresh) public void onClickButtonRefresh() {

        WeatherAPI.Factory.getInstance().getWeather().enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Query query = response.body().getQuery();
                tvTemperature.setText(query.getResults().getChannel().getItem().getCondition().getTemp());
                tvLocation.setText(query.getResults().getChannel().getLocation().getCity());
                tvDataAndTimeToday.setText(query.getResults().getChannel().getLastBuildDate());
                tvCurrentWeather.setText(query.getResults().getChannel().getItem().getCondition().getText());
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e("Failed", t.getMessage());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        onClickButtonRefresh();
    }
}
