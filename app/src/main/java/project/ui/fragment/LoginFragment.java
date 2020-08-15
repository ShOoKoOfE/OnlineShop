package project.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.shokoofeadeli.onlineshop.R;

import project.adapter.TabLayoutAdapter;

import static project.base.BaseApplication.getBaseApplication;

public class LoginFragment extends Fragment {
    TabLayoutAdapter tabLayoutAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        getBaseApplication().getApplicationComponent().inject(this);
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tabLayoutAdapter = new TabLayoutAdapter(getChildFragmentManager());
        viewPager = view.findViewById(R.id.tabs);
        viewPager.setAdapter(tabLayoutAdapter);
        tabLayout = view.findViewById(R.id.tabcontent);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText(getString(R.string.entrance));
        tabLayout.getTabAt(1).setText(getString(R.string.register));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.menu_search);
        if (item != null)
            item.setVisible(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
