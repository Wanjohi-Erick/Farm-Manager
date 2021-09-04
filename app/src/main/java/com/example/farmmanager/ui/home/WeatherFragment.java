package com.example.farmmanager.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.farmmanager.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class WeatherFragment extends Fragment {
    private TextView weatherView;
    private String weatherURL = "http://api.openweathermap.org/data/2.5/weather";
    private String appID = "bcdc11330a630bbee976779a4284da11";
    private DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        weatherView = view.findViewById(R.id.weatherView);
        getWeatherDetails();

        return view;
    }

    private void getWeatherDetails() {
        String tempUrl;
        String city = "Nyeri";
        String country = "KE";
        if (city.equals("")){
            weatherView.setText("City field cannot be empty");
        }else {
            if (!country.equals("")){
                tempUrl = weatherURL + "?q=" + city + "," + country + "&appid=" + appID;
            }else {
                tempUrl = weatherURL + "?q=" + city +  "&appid=" + appID;
            }
            StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, response -> {
                String output = "";
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                    String description = jsonObjectWeather.getString("description");
                    JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                    double temp = jsonObjectMain.getDouble("temp") - 273.15;
                    float pressure = jsonObjectMain.getInt("pressure");
                    int humidity = jsonObjectMain.getInt("humidity");
                    JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                    String wind = jsonObjectWind.getString("speed");
                    JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                    String clouds = jsonObjectClouds.getString("all");
                    JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                    String countryName = jsonObjectSys.getString("country");
                    String cityName = jsonResponse.getString("name");

                    weatherView.setTextColor(Color.rgb(68, 134, 199));
                    output += "Current weather of " + cityName + " (" + countryName + ")"
                            + "\ntemp: " + decimalFormat.format(temp) + "C"
                            + "\n Humidity: " + humidity + "%"
                            + "\n Desctiption: " + description
                            + "\n Wind: " + wind + "m/s"
                            + "\n Cloudliness: " + clouds + "%"
                            + "\n Pressure: " + pressure +" hPa";
                    weatherView.setText(output);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> Toast.makeText(getContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);
        }
    }
}
