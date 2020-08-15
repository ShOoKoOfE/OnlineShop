package project.helper;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {
    public static void ShowToast(Context context, String result) {
        if (result.isEmpty()) {
            return;
        }
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}
