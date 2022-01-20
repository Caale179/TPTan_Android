package fr.nantes.iut.tptan.presentation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import fr.nantes.iut.tptan.R;
import fr.nantes.iut.tptan.data.entity.Arret;
import fr.nantes.iut.tptan.data.entity.ListArret;

public class ProximityStopListFragment extends Fragment
        implements HomeActivity.ProximityStopListener, ProximityStopAdapter.ArretClickListener {

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 9876;

    // Recycler view and adapter
    private ProximityStopAdapter mAdapter;
    private boolean mAskingPermission = false;
    private Arret mSelectedArret;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_proximity_stop_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.arretproche_recycler_view);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //API 25 DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
        //API 25         mLayoutManager.getOrientation());
        //API 25 recyclerView.addItemDecoration(mDividerItemDecoration);

        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ProximityStopAdapter(new ArrayList<Arret>(), this );
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((HomeActivity)context).registerProximityListener(this);
    }

    @Override
    public void onDetach() {
        ((HomeActivity)getActivity()).unregisterProximityStopListener(this);
        super.onDetach();
    }

    @Override
    public void arretClicked(Arret arret) {

    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    public void onProximityStopChanged(ListArret arrets, Location location) {
        mAdapter.updateListArrets(arrets);
    }

    @Override
    public void iconLongClick(Arret arret) {
        if (!mAskingPermission) {
            mSelectedArret = arret;
            if (ContextCompat.checkSelfPermission(this.getActivity(),
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                mAskingPermission = true;
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            } else {
                takePicture();
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePicture();
                }
                return;
            }
        }
        mAskingPermission = false;
    }

    private void takePicture() {
        //TODO T402
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //TODO T404
    }
}
