package com.example.sublet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sublet.model.Location;
import com.example.sublet.model.Model;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MapFragment extends Fragment {
    MarkerOptions marker;
    LatLng centerLocation;
    List<MarkerOptions> markerOptions = new ArrayList<>();
    GoogleMap googleMap;
    double latitude,longitude;
    View view;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {

            googleMap.setMinZoomPreference(8.0f);
            Model.instance.getAllLocations(locationList -> {
                for (Location l : locationList){
                    markerOptions.add(new MarkerOptions().title("title")
                            .position(new LatLng(l.getLatitude(),l.getLongitude()))
                            .snippet("Hello"));
                }
                for(MarkerOptions marker : markerOptions){
                    googleMap.addMarker(marker);
                }
            });

            enableMyLocation();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerLocation,8));

            if(Model.instance.getMapStatus().equals("Create")) {
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        latitude = latLng.latitude;
                        longitude = latLng.longitude;
                        String currentPostId = MapFragmentArgs.fromBundle(getArguments()).getPostID();
                        Model.instance.saveLocation(currentPostId, latitude, longitude, () -> {
                            Navigation.findNavController(view).navigateUp(); // TODO: check if back to the add post fragment
                        });
                    }
                });
            }

            if(Model.instance.getMapStatus().equals("Edit")) {
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        latitude = latLng.latitude;
                        longitude = latLng.longitude;
                        String currentPostId = MapFragmentArgs.fromBundle(getArguments()).getPostID();
                        Model.instance.saveLocation(currentPostId, latitude, longitude, () -> {
                            Navigation.findNavController(view).navigateUp(); // TODO: check if back to the add post fragment
                        });
                    }
                });
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, container, false);
        centerLocation = new LatLng(31.973001,34.792501);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(MyApplication.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if ( googleMap!= null) {
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            }
        } else {
            String perms [] = {"android.permission.ACCESS_FINE_LOCATION"};
            ActivityCompat.requestPermissions(getActivity(),perms,200);
        }
    }
}