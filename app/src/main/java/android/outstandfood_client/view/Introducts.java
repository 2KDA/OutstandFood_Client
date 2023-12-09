package android.outstandfood_client.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.outstandfood_client.R;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Introducts extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introducts);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        LatLng nemnem = new LatLng(19.782117496888045, 105.77495404357217);
        googleMap.addMarker(new MarkerOptions().position(nemnem).title("Nem Nem"));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(nemnem, 18);
        googleMap.animateCamera(cameraUpdate, 3000, null);
    }
}