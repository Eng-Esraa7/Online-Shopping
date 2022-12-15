package com.example.onlineshopping;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private static final int REQUEST_LOCATION_PERMISSION =1 ;
    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    Marker marker;
    Button btn;
    EditText txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        btn=(Button)findViewById(R.id.btnmap2);
        txt=(EditText)findViewById(R.id.txt2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        /* Location Manager use either the Network provider or GPS provider of the android device to fetch the location
        information of the user, like latitude or longitude. */

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        /* This part asks for location access permission from the user. */

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                /* User's latitude and longitude is fetched here using the location object. */
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();


                /* Geocoder derives the location name, the specific country and city of the user as a list. */
                Geocoder geocoder = new Geocoder(getApplicationContext());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    String adress = addresses.get(0).getLocality() + ":";
                    adress += addresses.get(0).getCountryName();

                    /* The latitude and longitude is combined and placed on the google map using a marker in the following part. */

                    LatLng latLng = new LatLng(latitude, longitude);

                    if (marker != null) {
                        marker.remove();
                    }
                    marker = mMap.addMarker(new MarkerOptions().position(latLng).title(adress));
                    mMap.setMaxZoomPreference(10);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.0f));



                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        /* As stated before, location manager uses either network provider or the gps provider to fetch user's location information. */
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
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

                            mMap.addMarker(new MarkerOptions().position(latLng).title("My Location").snippet(address)).setDraggable(true);
                            txt.setText(address);
                        }
                    }
                    catch (IOException ex){
                        mMap.addMarker(new MarkerOptions().position(latLng).title("My Location"));
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Wait your position determind",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}