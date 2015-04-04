package com.myznikov.weather.activities;

import android.animation.ObjectAnimator;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.myznikov.weather.R;
import com.myznikov.weather.dots.Coord;
import com.myznikov.weather.dots.NoCityFoundDto;
import com.myznikov.weather.dots.WeatherResponseDto;
import com.myznikov.weather.utils.DateUtils;
import com.myznikov.weather.webrequests.ResultListener;
import com.myznikov.weather.webrequests.WeatherWebRequest;
import com.squareup.picasso.Picasso;

public class MapsActivity extends ActionBarActivity implements ResultListener<WeatherResponseDto, NoCityFoundDto> {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private TextView  tvCity , tvTemp, tvWind, tvHumidity, tvUpdDate;
    private ImageView ivWeatherIcon;
    private View weatherInfoLayout;
    private int mCameraZoom = 10;
    private String currentCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        initViews();
    }

    private void initViews() {
        tvCity = (TextView)findViewById(R.id.tv_city);
        tvTemp = (TextView)findViewById(R.id.tv_temp);
        ivWeatherIcon = (ImageView)findViewById(R.id. iv_weather_ic);
        tvWind = (TextView)findViewById(R.id.tv_wind);
        tvHumidity = (TextView)findViewById(R.id.tv_humidity);
        tvUpdDate = (TextView)findViewById(R.id.tv_upd_date);
        weatherInfoLayout = findViewById(R.id.ll_weather_info);
        weatherInfoLayout.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.maps_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String cityName = intent.getStringExtra(SearchManager.QUERY);
            currentCity = cityName;
            WeatherWebRequest request = new WeatherWebRequest(this, this);
            request.sendRequest(cityName);
            weatherInfoLayout.setVisibility(View.GONE);
        }
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    @Override
    public void onSuccessResult(WeatherResponseDto result) {
        Coord coord= result.getCoord();
        setUpCamera(coord.getLat(), coord.getLon());
        setWeatherInfo(result);
    }

    private void setWeatherInfo(WeatherResponseDto weatherInfo){
        tvCity.setText(currentCity + ", " + weatherInfo.getSys().getCountry());

        String iconName = weatherInfo.getWeather().get(0).getIcon();
        Picasso.with(this).load(WeatherWebRequest.getImageUrl(iconName)).into(ivWeatherIcon);

        tvTemp.setText(weatherInfo.getMain().getTemp() + " \u2103");
        tvWind.setText(String.format("%s1 m /s \n %s2 \u00B0"
                , weatherInfo.getWind().getSpeed()
                , weatherInfo.getWind().getDeg()));
        tvHumidity.setText(weatherInfo.getMain().getHumidity() + " %");
        tvUpdDate.setText(DateUtils.getDate(weatherInfo.getDt()));
        weatherInfoLayout.setVisibility(View.VISIBLE);
    }

    private void setUpCamera(double lat, double lng){
        LatLng latLng = new LatLng(lat, lng);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, mCameraZoom);
        if(mMap != null){
            mMap.animateCamera(cameraUpdate);
        }
    }

    @Override
    public void onErrorResult(NoCityFoundDto error) {
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
    }
}
