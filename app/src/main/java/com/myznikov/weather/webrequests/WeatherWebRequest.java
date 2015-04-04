package com.myznikov.weather.webrequests;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.myznikov.weather.dots.NoCityFoundDto;
import com.myznikov.weather.dots.WeatherResponseDto;

/**
 * Created by MS Creator on 04.04.2015.
 */
public class WeatherWebRequest {
    private static final String BASE_URL = "http://api.openweathermap.org/";
    private static final String WEATHER_PARAMS_PART = "data/2.5/weather/?q=";
    private static final String IMAGE_PARAMS_PART = "img/w/";
    private static final String METRICS_UNITS_PARAMS = "&units=metric";
    private ResultListener resultListener;
    private Context context;

    public WeatherWebRequest(Context context, ResultListener<WeatherResponseDto, NoCityFoundDto> resultListener){
        this.context = context;
        this.resultListener = resultListener;
    }


    public void sendRequest(String cityName){
        String url = BASE_URL + WEATHER_PARAMS_PART + cityName + METRICS_UNITS_PARAMS;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                NoCityFoundDto noCityFoundDto = gson.fromJson(response, NoCityFoundDto.class);
                if(noCityFoundDto.getMessage() != null){
                    resultListener.onErrorResult(noCityFoundDto);
                }else {
                    WeatherResponseDto weatherResponseDto = gson.fromJson(response, WeatherResponseDto.class);
                    resultListener.onSuccessResult(weatherResponseDto);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    public static String getImageUrl(String imgName){
        return BASE_URL + IMAGE_PARAMS_PART + imgName;
    }
}
