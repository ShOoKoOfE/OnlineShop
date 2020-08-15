package project.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import project.ui.fragment.EntrenceProfileFragment;
import project.ui.fragment.RegisterProfileFragment;

public class TabLayoutAdapter extends FragmentPagerAdapter {
    public TabLayoutAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new EntrenceProfileFragment();
            case 1:
                return new RegisterProfileFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}






