package android.outstandfood_client.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.outstandfood_client.R;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Introducts extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap map;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introducts);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        spinner = findViewById(R.id.spinner);
        spinner = findViewById(R.id.spinner);
        ArrayList<String> list = new ArrayList<>();
        list.add("Chế độ xem");
        list.add("Địa hình");
        list.add("Giao thông");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        break;
                    case 1:
                        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        break;
                    case 2:
                        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        LatLng nemnem = new LatLng(21.020354543111697, 105.79377143426719);
        googleMap.addMarker(new MarkerOptions().position(nemnem).title("OutStand'Food"));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(nemnem, 18);
        googleMap.animateCamera(cameraUpdate, 3000, null);
    }
}