package com.example.catataja;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class WelcomeActivity extends AppCompatActivity {

    private ImageView flagImage;
    private TextView countryCodeText, greetingText;
    private FusedLocationProviderClient fusedLocationClient;

    private static final int LOCATION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        flagImage = findViewById(R.id.flagImage);
        countryCodeText = findViewById(R.id.countryCodeText);
        greetingText = findViewById(R.id.greetingText);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        requestLocation();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(WelcomeActivity.this, DashboardActivity.class));
            finish();
        }, 2000);
    }

    private void requestLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_REQUEST_CODE);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                try {
                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (!addresses.isEmpty()) {
                        String countryCode = addresses.get(0).getCountryCode();
                        countryCodeText.setText(countryCode);
                        setGreeting(countryCode);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    greetingText.setText("Welcome!");
                }
            } else {
                // fallback jika lokasi null
                setGreeting(Locale.getDefault().getCountry());
            }
        });
    }

    private void setGreeting(String countryCode) {
        switch (countryCode) {
            case "ID":
                greetingText.setText("Selamat Datang!");
                flagImage.setImageResource(R.drawable.flag_id);
                break;
            case "US":
                greetingText.setText("Welcome!");
                flagImage.setImageResource(R.drawable.flag_us);
                break;
            case "JP":
                greetingText.setText("ようこそ!");
                flagImage.setImageResource(R.drawable.flag_jp);
                break;
            case "SA":
                greetingText.setText("مرحباً!");
                flagImage.setImageResource(R.drawable.flag_sa);
                break;
            case "DE":
                greetingText.setText("Willkommen!");
                flagImage.setImageResource(R.drawable.flag_de);
                break;
            case "CN":
                greetingText.setText("欢迎！");
                flagImage.setImageResource(R.drawable.flag_cn);
                break;
            case "KR":
                greetingText.setText("환영합니다!");
                flagImage.setImageResource(R.drawable.flag_kr);
                break;
            default:
                greetingText.setText("Welcome!");
                flagImage.setImageResource(R.drawable.flag_us);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocation();
            } else {
                // fallback jika user menolak izin
                setGreeting(Locale.getDefault().getCountry());
            }
        }
    }
}