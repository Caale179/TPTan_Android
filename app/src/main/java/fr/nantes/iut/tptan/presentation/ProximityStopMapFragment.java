package fr.nantes.iut.tptan.presentation;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import fr.nantes.iut.tptan.R;
import fr.nantes.iut.tptan.data.entity.Arret;
import fr.nantes.iut.tptan.data.entity.ListArret;

public class ProximityStopMapFragment extends Fragment implements OnMapReadyCallback, HomeActivity.ProximityStopListener {

    private GoogleMap mMap;

    public ProximityStopMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_proximity_stop_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = getMapFragment();
        if (mapFragment == null) {
            mapFragment = new SupportMapFragment();
            mapFragment.setRetainInstance(true);
            getChildFragmentManager().beginTransaction().replace(R.id.proximity_stop_map_layout, mapFragment, null).commit();
        }
        mapFragment.getMapAsync(this);
    }

    private SupportMapFragment getMapFragment() {
        return (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.proximity_stop_map_layout);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((HomeActivity) context).registerProximityListener(this);
    }

    @Override
    public void onDetach() {
        ((HomeActivity) getActivity()).unregisterProximityStopListener(this);
        super.onDetach();
    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    public void onProximityStopChanged(ListArret arrets, Location location) {
        //TODO T302: implementer onProximityStopChanged
        this.mMap.setMyLocationEnabled(true);//feature my location
        LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
        this.mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));


        for (Arret a : arrets) {
            if (a.getStop() != null) {
                LatLng posStop = new LatLng(a.getStop().getLat(), a.getStop().getLng());
                mMap.addMarker(new MarkerOptions()
                        .position(posStop)
                        .title(a.getLibelle())
                        .snippet(a.getLignes().toString())
                        .icon(BitmapDescriptorFactory.fromResource(
                                R.drawable.busmarker
                        )));
            }
        }
    }
}