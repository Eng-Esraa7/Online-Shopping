package com.example.onlineshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.location.LocationListener;
import android.os.Bundle;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapppActivity extends AppCompatActivity implements OnMapReadyCallback{

    Button btn;
    EditText txt;
    GoogleMap map;
    myLocationListen locationListen;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mappp);

        txt = (EditText) findViewById(R.id.txt2);
        btn = (Button) findViewById(R.id.btn);
       // locationListen = new myLocationListen(getApplicationContext());
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        try{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000,0,locationListen);
        }
        catch (SecurityException ex){
            Toast.makeText(getApplicationContext(),"You are not all",Toast.LENGTH_LONG).show();
        }

    }



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.04441960,31.235711600),8));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.clear();
                Geocoder geocoder = new Geocoder(getApplicationContext());
                List<Address> addressList;
                Location loc = null;

                try{
                    loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
                catch (SecurityException ex)
                {
                    Toast.makeText(getApplicationContext(),"You did not allow to access",Toast.LENGTH_LONG).show();
                }

                if(loc != null)
                {
                    LatLng latLng = new LatLng(loc.getLatitude(),loc.getLongitude());
                    try{
                        addressList = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);

                        if(!addressList.isEmpty())
                        {
                            String address = "";
                            for (int i = 0; i <= addressList.get(0).getMaxAddressLineIndex();i++)
                            {
                                address += addressList.get(0).getAddressLine(i) + ", ";
                            }

                            map.addMarker(new MarkerOptions().position(latLng).title("My Location").snippet(address)).setDraggable(true);
                            txt.setText(address);
                        }
                    }
                    catch (IOException ex){
                        map.addMarker(new MarkerOptions().position(latLng).title("My Location"));
                    }
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Wait your position determind",Toast.LENGTH_LONG).show();
                }
            }
        });

        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(@NonNull Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(@NonNull Marker marker) {

                Geocoder geocoder = new Geocoder(getApplicationContext());
                List<Address> addressList;
                try{
                    addressList = geocoder.getFromLocation(marker.getPosition().latitude,marker.getPosition().longitude,1);

                    if(!addressList.isEmpty())
                    {
                        String address = "";
                        for (int i = 0; i <= addressList.get(0).getMaxAddressLineIndex();i++)
                        {
                            address += addressList.get(0).getAddressLine(i) + ", ";
                        }
                        txt.setText(address);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"No address for this location",Toast.LENGTH_LONG).show();
                        txt.getText().clear();
                    }
                }
                catch (IOException ex){
                    Toast.makeText(getApplicationContext(),"can not get address",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onMarkerDragStart(@NonNull Marker marker) {

            }
        });

    }

    }