package project.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.shokoofeadeli.onlineshop.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import project.dependency.ApplicationComponent;
import project.dependency.ApplicationModule;
import project.dependency.DaggerApplicationComponent;
import project.models.Product;
import project.models.User;

import static project.helper.PreferencesHelper.PASSWORD_KEY;
import static project.helper.PreferencesHelper.USERNAME_KEY;
import static project.helper.PreferencesHelper.getPreferences;

public class BaseApplication extends Application {
    private  ArrayList<String> factoryList = new ArrayList<>();
    private  ArrayList<Product> userBasketList = new ArrayList<>();
    private User user = new User();
    private Handler handler;
    private Context context;
    private Activity currentActivity;
    private LayoutInflater layoutInflater;
    private static BaseApplication baseApplication;
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        baseApplication = this;
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        handler = new Handler();
        layoutInflater = LayoutInflater.from(context);

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/IRANSans.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

        setActionBarMenuSetting();

        user.setUserUsername(getPreferences(USERNAME_KEY, getContext()));
        user.setUserPassword(getPreferences(PASSWORD_KEY, getContext()));
    }

    public static BaseApplication getBaseApplication() {
        return baseApplication;
    }

    public Context getContext() {
        if (currentActivity != null) {
            return currentActivity;
        }
        return context;
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Activity activity) {
        currentActivity = activity;
    }

    public View inflateLayout(@LayoutRes int res) {
        return layoutInflater.inflate(res, null);
    }

//    public View inflateLayout(@LayoutRes int res, @Nullable ViewGroup root) {
//        return layoutInflater.inflate(res, root);
//    }

    public Handler getHandler() {
        return handler;
    }

    public static void HideSoftKeyboar(Activity activity) {
        final InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getWindow().getDecorView().getRootView().getWindowToken(), 0);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<String> getFactoryList() {
        return factoryList;
    }

    public void setFactoryList(String factoryName) {
        this.factoryList.add(factoryName);
    }

    public void clearFactoryList() {
        factoryList.clear();
    }

    public ArrayList<Product> getUserBasketList() {
        return userBasketList;
    }

    public void setUserBasketList(ArrayList<Product> userBasketList) {
        this.userBasketList = userBasketList;
    }

    public void setUserBasketList(Product product){
        userBasketList.add(product);
    }

    public void clearUserBasketList() {
        userBasketList.clear();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    private void setActionBarMenuSetting() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
