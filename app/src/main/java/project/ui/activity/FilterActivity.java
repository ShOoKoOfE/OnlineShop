package project.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shokoofeadeli.onlineshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import project.adapter.FilterRecyclerAdapter;
import project.base.BaseActivity;
import project.comon.ActionBarSetting;

import static project.adapter.FilterRecyclerAdapter.filterFactoryList;
import static project.base.BaseApplication.getBaseApplication;

public class FilterActivity extends BaseActivity {
    @BindView(R.id.txtTabFactory)
    TextView txtTabFactory;
    @BindView(R.id.txtTabPrice)
    TextView txtTabPrice;
    @BindView(R.id.btnFilter)
    Button btnFilter;
    @BindView(R.id.lstFactory)
    RecyclerView lstFactory;
    @BindView(R.id.linearTabFactory)
    LinearLayout linearTabFactory;
    @BindView(R.id.linearTabPrice)
    LinearLayout linearTabPrice;
    @BindView(R.id.imgClose)
    ImageButton imgClose;
    @BindView(R.id.seekPrice)
    SeekBar seekPrice;
    int priceFilter = 0;
    long maxPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getBaseApplication().getApplicationComponent().inject(this);
        super.onCreate(savedInstanceState);
        setActionBarSetting();
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            maxPrice = bundle.getLong("MAX_PRICE");
        }
        lstFactory.setLayoutManager(new LinearLayoutManager(this));
        seekPrice.setMax((int) maxPrice);
        FilterRecyclerAdapter filterRecyclerAdapter = new FilterRecyclerAdapter(getBaseApplication().getFactoryList());
        lstFactory.setAdapter(filterRecyclerAdapter);

        seekPrice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                priceFilter = i;
                TextView txtPrice = (TextView) findViewById(R.id.txtPrice);
                txtPrice.setText(priceFilter + " " + getString(R.string.toman));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @OnClick(R.id.btnFilter)
    public void onButtonFilterClick(View view) {
        String filterParam = "1 = 1";
        String factoryParam = "";
        for (String factory : filterFactoryList) {
            factoryParam += " product_factory = '" + factory + "' OR";
        }
        if (factoryParam != "") {
            factoryParam = factoryParam.substring(0, factoryParam.length() - 2);
            filterParam = "(" + factoryParam + ")";
        }
        if (priceFilter != 0) {
            filterParam += " AND product_price <= " + priceFilter;
        }
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", filterParam);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @OnClick(R.id.txtTabFactory)
    public void onTabFactoryClick(View view) {
        txtTabFactory.setBackgroundColor(Color.parseColor("#f7f7f7"));
        txtTabPrice.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_backborder));
        linearTabFactory.setVisibility(View.VISIBLE);
        linearTabPrice.setVisibility(View.GONE);
    }

    @OnClick(R.id.txtTabPrice)
    public void onTabPriceClick(View view) {
        txtTabPrice.setBackgroundColor(Color.parseColor("#f7f7f7"));
        txtTabFactory.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_backborder));
        linearTabPrice.setVisibility(View.VISIBLE);
        linearTabFactory.setVisibility(View.GONE);
    }

    @OnClick(R.id.imgClose)
    public void onCloseClick(View view) {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    public void setActionBarSetting() {
        new ActionBarSetting(this)
                .hasActionbar(false)
                .build();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
