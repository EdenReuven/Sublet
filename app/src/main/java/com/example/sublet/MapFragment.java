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
import android.view.Display;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {
    LatLng centerLocation;
    List<MarkerOptions> markerOptions = new ArrayList<>();
    GoogleMap googleMap;
    double latitude,longitude;
    View view;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            googleMap.setMinZoomPreference(8.0f);

            if(!Model.instance.getMapStatus().equals("Create") && !Model.instance.getMapStatus().equals("Edit")){
                Model.instance.getAllLocations(locationList -> {
                    for (Location l : locationList){
                        String title = l.getLatitude()+","+l.getLongitude();
                        markerOptions.add(new MarkerOptions().position(new LatLng(l.getLatitude(),l.getLongitude())).title(title));
                    }
                    for(MarkerOptions marker : markerOptions){
                        googleMap.addMarker(marker);
                    }
                });
            }

            if(!Model.instance.getMapStatus().equals("Create") && !Model.instance.getMapStatus().equals("Edit")){
                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        String lat = marker.getTitle().split(",")[0];
                        String lon = marker.getTitle().split(",")[1];
                        Model.instance.getAllLocations(locationList -> {
                            for(Location l : locationList){
                                if(lat.equals(Double.toString(l.getLatitude()))
                                        && lon.equals(Double.toString(l.getLongitude()))){
                                    Navigation.findNavController(view)
                                            .navigate(MapFragmentDirections.actionMapFragmentToPostFragment(l.getPostId()));
                                }
                            }
                        });
                        return false;
                    }
                });
            }

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
                            Navigation.findNavController(view).navigateUp();
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
                            Navigation.findNavController(view).navigateUp();
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