package com.example.game2048.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class FragmentViewpage extends FragmentPagerAdapter {
    private FragmentManager mfragmentManager;
    private List<Fragment> mlist;
    public FragmentViewpage(@NonNull FragmentManager fm) {
        super(fm);
    }

    public FragmentViewpage(@NonNull FragmentManager fm, int behavior,List<Fragment>list) {
        super(fm, behavior);
        this.mlist=list;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }
}
