package project.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import com.shokoofeadeli.onlineshop.R;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import project.comon.ActionBarSetting;
import project.models.ProductViewModel;
import project.models.UserViewModel;
import project.repository.IResponseListener;
import project.base.BaseActivity;
import project.helper.ConnectivityHelper;
import project.helper.RequestHelper;
import project.helper.ToastHelper;
import project.models.Product;
import project.models.User;
import project.repository.RetrieveRepositoryData;

import static project.base.BaseApplication.getBaseApplication;
import static project.helper.PreferencesHelper.addSharedPreferences;
import static project.helper.PreferencesHelper.removeSharedPreferences;

public class SplashActivity extends BaseActivity {
    private static int SPLASH_TIME_OUT = 1000;
    @Inject
    ProductViewModel productViewModel;

    @Inject
    UserViewModel userViewModel;

    RequestHelper request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getBaseApplication().getApplicationComponent().inject(this);
        super.onCreate(savedInstanceState);
        setActionBarSetting();
        setContentView(R.layout.activity_splash);
        request = new RequestHelper(this);
        setPermission();
    }

    private void setPermission() {
        RequestHelper.OnGrantedListener grantedListenerListener = new RequestHelper.OnGrantedListener() {
            @Override
            public void onGranted() {
                initializeApp();
            }
        };
        RequestHelper.OnAlreadyGrantedListener onAlreadyGrantedListener = new RequestHelper.OnAlreadyGrantedListener() {
            @Override
            public void onAlreadyGranted() {
                initializeApp();
            }
        };
        RequestHelper.OnDeniedListener deniedListener = new RequestHelper.OnDeniedListener() {
            @Override
            public void onDenied() {
                ShowDeniedDialog();
            }
        };
        request.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, grantedListenerListener, deniedListener, onAlreadyGrantedListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        RequestHelper.onRequestPermissionResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initializeApp() {
        if (!ConnectivityHelper.HasInternet(this)) {
            String result = getString(R.string.connectivity_noConnection);
            ToastHelper.ShowToast(getBaseContext(), result);
            return;
        }
        if (getBaseApplication().getUser().getUserUsername() != null) {
            getUserInfo();
        } else {
            startHomeActivity();
        }
    }

    private void ShowDeniedDialog() {
        new AlertDialog.Builder(SplashActivity.this)
                .setTitle(getString(R.string.warning))
                .setMessage(getString(R.string.permission_warning))
                .setPositiveButton(getString(R.string.create_permission), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setPermission();
                    }
                })
                .setNegativeButton(getString(R.string.close_app), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                })
                .create()
                .show();
    }

    private void getUserInfo() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "login");
        map.put("username", getBaseApplication().getUser().getUserUsername());
        map.put("password", getBaseApplication().getUser().getUserPassword());
        RetrieveRepositoryData.getData(userViewModel.makeFutureUser(map), userViewModel.getLiveDataUser(map), new IResponseListener<User>() {
            @Override
            public void OnResponseComplete(User user) {
                if (user != null) {
                    addSharedPreferences(user, getBaseApplication().getContext());
                    getBaseApplication().setUser(user);
                    getUserBasketList();
                } else {
                    removeSharedPreferences(getBaseApplication().getContext());
                    startHomeActivity();
                }
            }
        });
    }

    private void startHomeActivity() {
        getBaseApplication().getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                if (getBaseApplication().getUser().getUserUsername() != null) {
                    intent.putExtra("FRAGMENT", "Home");
                } else {
                    intent.putExtra("FRAGMENT", "Login");
                }

                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    public void setActionBarSetting() {
        new ActionBarSetting(this)
                .hasActionbar(false)
                .build();
    }

    private void getUserBasketList() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "basket");
        map.put("user_id", "" + getBaseApplication().getUser().getUserId());
        RetrieveRepositoryData.getData(productViewModel.makeFutureProducts(map), productViewModel.getLiveDataProducts(map), new IResponseListener<ArrayList<Product>>() {
            @Override
            public void OnResponseComplete(ArrayList<Product> products) {
                getBaseApplication().setUserBasketList(products);
                startHomeActivity();
            }
        });
    }
}
