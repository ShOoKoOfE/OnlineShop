package project.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shokoofeadeli.onlineshop.R;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import project.adapter.ProductRecyclerAdapter;
import project.base.BaseActivity;
import project.comon.ActionBarSetting;
import project.models.Product;
import project.models.ProductViewModel;
import project.repository.IResponseListener;
import project.repository.RetrieveRepositoryData;

import static project.base.BaseApplication.getBaseApplication;

public class ProductActivity extends BaseActivity {
    final static int LAUNCH_FILTER_ACTIVITY = 1;
    @BindView(R.id.lstProduct)
    RecyclerView lstProduct;
    @BindView(R.id.linearSort)
    LinearLayout linearSort;
    @BindView(R.id.linearDropdown)
    LinearLayout linearDropdown;
    @BindView(R.id.linearFilter)
    LinearLayout linearFilter;
    @BindView(R.id.txtDESC)
    TextView txtDESC;
    @BindView(R.id.txtASC)
    TextView txtASC;
    @BindView(R.id.txtSort)
    TextView txtSort;
    String productCategory;
    ProductRecyclerAdapter productRecyclerAdapter;
    Bundle bundle;
    String filter = "1 = 1";
    String sort = "ASC";
    boolean flag = false;
    long maxPrice = 0;
    String categoryName;
    @Inject
    ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getBaseApplication().getApplicationComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        bundle = intent.getExtras();
        if (intent.hasExtra("category_code")) {
            productCategory = bundle.getString("category_code");
            categoryName = bundle.getString("category_name");
        }
        setActionBarSetting();
        getProductList();
    }

    private void setActionBarSetting() {
        new ActionBarSetting(this)
                .setBackground(getResources().getDrawable(R.drawable.actionbar_nologo))
                .setTitle(categoryName)
                .build();
    }

    @OnClick(R.id.linearSort)
    public void onlinearSortClick(View view) {
        showDropdownListSort();
    }

    @OnClick(R.id.linearFilter)
    public void onlinearFilterClick(View view) {
        Intent intent = new Intent(ProductActivity.this, FilterActivity.class);
        intent.putExtra("MAX_PRICE", maxPrice);
        startActivityForResult(intent, LAUNCH_FILTER_ACTIVITY);
    }

    private void showDropdownListSort() {
        if (flag) {
            flag = false;
            linearDropdown.animate()
                    .translationY(-50)
                    .alpha(0.0f)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            linearDropdown.setVisibility(View.GONE);
                        }
                    });
        } else {
            flag = true;
            linearDropdown.setVisibility(View.VISIBLE);
            linearDropdown.setAlpha(0.0f);
            linearDropdown.animate()
                    .translationY(0)
                    .alpha(1.0f)
                    .setListener(null);
        }
        txtDESC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort = "DESC";
                txtSort.setText(getString(R.string.DESC));
                getProductList();
                showDropdownListSort();
            }
        });
        txtASC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort = "ASC";
                txtSort.setText(getString(R.string.ASC));
                getProductList();
                showDropdownListSort();
            }
        });
    }

    private void getProductList() {
        //productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        maxPrice = 0;
        getBaseApplication().clearFactoryList();

        HashMap<String, String> map = new HashMap<>();
        map.put("action", "product");
        map.put("filter", filter);
        map.put("sort", sort);
        map.put("product_category", productCategory);
        RetrieveRepositoryData.getData(productViewModel.makeFutureProducts(map), productViewModel.getLiveDataProducts(map), new IResponseListener<ArrayList<Product>>() {
            @Override
            public void OnResponseComplete(ArrayList<Product> products) {
                for (Product product : products) {
                    if (!getBaseApplication().getFactoryList().contains(product.getProductFactory())) {
                        getBaseApplication().setFactoryList(product.getProductFactory());
                    }
                    if (maxPrice < product.getProductPrice()) {
                        maxPrice = product.getProductPrice();
                    }
                }
                productRecyclerAdapter = new ProductRecyclerAdapter(ProductActivity.this, products);
                lstProduct.setLayoutManager(new LinearLayoutManager(ProductActivity.this));
                lstProduct.setAdapter(productRecyclerAdapter);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_FILTER_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                filter = data.getStringExtra("result");
                getProductList();
            }
        }
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
