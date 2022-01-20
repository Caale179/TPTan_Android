package fr.nantes.iut.tptan.presentation;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabsAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public TabsAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context ;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return Fragment.instantiate(mContext, ProximityStopListFragment.class.getName(), null);
            case 1:
                return Fragment.instantiate(mContext, ProximityStopMapFragment.class.getName(), null);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}