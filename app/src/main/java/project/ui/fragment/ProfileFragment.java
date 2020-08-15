package project.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.shokoofeadeli.onlineshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static project.base.BaseApplication.getBaseApplication;
import static project.helper.PreferencesHelper.removeSharedPreferences;

public class ProfileFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.txtUser)
    TextView txtUser;
    @BindView(R.id.txtMobile)
    TextView txtMobile;
    @BindView(R.id.linearExit)
    LinearLayout linearExit;
    @BindView(R.id.linearFavorite)
    LinearLayout linearFavorite;
    @BindView(R.id.linearProfileFactor)
    LinearLayout linearProfileFactor;
    Fragment fragment = null;
    FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        getBaseApplication().getApplicationComponent().inject(this);
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        fragmentManager = getActivity().getSupportFragmentManager();
        txtUser.setText(getBaseApplication().getUser().getUserUsername());
        txtMobile.setText(getBaseApplication().getUser().getUserMobile());

        linearExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeSharedPreferences(getContext());
                ImageButton btnProfile = getActivity().findViewById(R.id.btnProfile);
                btnProfile.callOnClick();
            }
        });

        linearFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new FavouriteFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.content, fragment)
                        .addToBackStack("HomeActivity")
                        .commitAllowingStateLoss();
            }
        });

        linearProfileFactor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new FactorFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.content, fragment)
                        .addToBackStack("HomeActivity")
                        .commitAllowingStateLoss();
            }
        });
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
        unbinder.unbind();
    }
}
