package project.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityHelper {
    public static boolean HasInternet(Context context) {
        boolean hasInternet = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                hasInternet = true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                hasInternet = true;
            }
        }
        return hasInternet;
    }
}
