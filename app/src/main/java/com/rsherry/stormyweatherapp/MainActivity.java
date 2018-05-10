package com.rsherry.stormyweatherapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String apiKey = ApiKey.getApiKey();
        double latitude = 37.8267;
        double longitude = -122.4233;
        String forecastURL = "https://api.darksky.net/forecast/"
                + apiKey + "/" + latitude + "," + longitude;

        if (isNetworkAvailable()) {

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(forecastURL)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        Log.v(TAG, response.body().string());
                        if (response.isSuccessful()) {
                        } else {
                            alertUserOfError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "IO Exception caught: ", e);
                    }
                }
            });
        } else {
            alertUserOfNoNetwork();
        }
            Log.d(TAG, "Main UI code is running, horray!");
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if(networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserOfError() {
        Bundle bundle = new Bundle();
        bundle.putString(AlertDialogueFragment.TITLE_ID,"Error");
        bundle.putString(AlertDialogueFragment.MESSAGE_ID, "An error occurred");

            AlertDialogueFragment dialogue = new AlertDialogueFragment();
        dialogue.setArguments(bundle);
            dialogue.show(getFragmentManager(),"error_dialogue");
    }

    private void alertUserOfNoNetwork() {
        Bundle bundle = new Bundle();
        bundle.putString(AlertDialogueFragment.TITLE_ID,"Error");
        bundle.putString(AlertDialogueFragment.MESSAGE_ID, "Network is unavailable");

        AlertDialogueFragment dialogue = new AlertDialogueFragment();
        dialogue.setArguments(bundle);
        dialogue.show(getFragmentManager(),"network_error_dialogue");
    }
}

