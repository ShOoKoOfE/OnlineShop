package project.comon;

import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.shokoofeadeli.onlineshop.R;

import project.base.BaseApplication;

public class ActionBarSetting {
    private boolean hasActionbar;
    private Drawable drawable;
    private String title;
    private View viewActionBar;
    private ActionBar actionBar;

    public ActionBarSetting(AppCompatActivity activity) {
        this.actionBar = activity.getSupportActionBar();
        this.hasActionbar = true;
        this.drawable = null;
        this.title = null;
        this.viewActionBar = ((BaseApplication) activity.getApplication()).inflateLayout(R.layout.actionbar_layout);
    }

    public ActionBarSetting hasActionbar(Boolean hasActionbar) {
        this.hasActionbar = hasActionbar;
        return this;
    }

    public ActionBarSetting setBackground(Drawable drawable) {
        this.drawable = drawable;
        return this;
    }

    public ActionBarSetting setTitle(String title) {
        this.title = title;
        return this;
    }

    public ActionBarSetting build() {
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        actionBar.setCustomView(viewActionBar, layoutParams);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_go_parent);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (!hasActionbar) {
            actionBar.hide();
        }
        if (title != null) {
            TextView actionBarTitle = viewActionBar.findViewById(R.id.txtActionBarTitle);
            actionBarTitle.setText(title);
        }
        if (drawable != null) {
            actionBar.setBackgroundDrawable(drawable);
        }
        return this;
    }
}
