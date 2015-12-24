package com.gdgssu.android_deviewsched.ui.location;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gdgssu.android_deviewsched.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = makeLogTag("LocationActivity");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng deview = new LatLng(127.0594275, 37.5116621);
        googleMap.addMarker(new MarkerOptions().position(deview).title("Deview 2016"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(deview));
    }
}
