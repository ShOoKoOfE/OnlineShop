package project.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.shokoofeadeli.onlineshop.R;

import project.base.BaseActivity;
import project.comon.ActionBarSetting;
import project.ui.fragment.BasketFragment;
import project.ui.fragment.CategoriesFragment;
import project.ui.fragment.ContactFragment;
import project.ui.fragment.FactorFragment;
import project.ui.fragment.FavouriteFragment;
import project.ui.fragment.HomeFragment;
import project.ui.fragment.LoginFragment;
import project.ui.fragment.ProfileFragment;

import static project.base.BaseApplication.getBaseApplication;

public class HomeActivity extends BaseActivity {
    Fragment fragment = null;
    FragmentManager fragmentManager = getSupportFragmentManager();
    private String fragmentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getBaseApplication().getApplicationComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            fragmentName = bundle.getString("FRAGMENT");
            chooseFragment(fragmentName);
        }
    }

    private void setActionBarSetting(Drawable drawable, String title) {
        new ActionBarSetting(this)
                .setBackground(drawable)
                .setTitle(title)
                .build();
    }

    public void chooseFragment(String fragmentName) {
        switch (fragmentName) {
            case "Home":
                setActionBarSetting(getResources().getDrawable(R.drawable.actionbar_logo), "");
                fragment = new HomeFragment();
                break;
            case "Favourite":
                setActionBarSetting(getResources().getDrawable(R.drawable.actionbar_nologo), getString(R.string.favourite));
                fragment = new FavouriteFragment();
                break;
            case "Basket":
                setActionBarSetting(getResources().getDrawable(R.drawable.actionbar_nologo), getString(R.string.basket));
                fragment = new BasketFragment();
                break;
            case "Login":
                setActionBarSetting(getResources().getDrawable(R.drawable.actionbar_nologo), getString(R.string.creat_account));
                fragment = new LoginFragment();
                break;
            case "History":
                setActionBarSetting(getResources().getDrawable(R.drawable.actionbar_nologo), getString(R.string.factor_history));
                fragment = new FactorFragment();
                break;
        }
        fragmentTransaction();
    }

    public void chooseFragment(View view) {
        ImageButton button = (ImageButton) view;
        String tagName = (String) button.getTag();
        switch (tagName) {
            case "Home":
                fragmentName = "Home";
                setActionBarSetting(getResources().getDrawable(R.drawable.actionbar_logo), "");
                fragment = new HomeFragment();
                break;
            case "List":
                fragmentName = "List";
                setActionBarSetting(getResources().getDrawable(R.drawable.actionbar_nologo), getString(R.string.categories));
                fragment = new CategoriesFragment("ALL");
                break;
            case "Basket":
                fragmentName = "Basket";
                setActionBarSetting(getResources().getDrawable(R.drawable.actionbar_nologo), getString(R.string.basket));
                fragment = new BasketFragment();
                break;
            case "Profile":
                fragmentName = "Profile";
                if (getBaseApplication().getUser() != null) {
                    setActionBarSetting(getResources().getDrawable(R.drawable.actionbar_nologo), getString(R.string.profile));
                    fragment = new ProfileFragment();
                } else {
                    setActionBarSetting(getResources().getDrawable(R.drawable.actionbar_nologo), getString(R.string.creat_account));
                    fragment = new LoginFragment();
                }
                break;
            case "Contact":
                fragmentName = "Contact";
                setActionBarSetting(getResources().getDrawable(R.drawable.actionbar_nologo), getString(R.string.profile));
                fragment = new ContactFragment();
                break;
        }
        fragmentTransaction();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                chooseFragment("Home");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fragmentTransaction() {
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .addToBackStack("HomeActivity")
                .commitAllowingStateLoss();
    }
}
