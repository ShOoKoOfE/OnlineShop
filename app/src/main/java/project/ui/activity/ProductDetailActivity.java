package project.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.shokoofeadeli.onlineshop.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import project.adapter.ImagePagerAdapter;
import project.base.BaseActivity;
import project.comon.ActionBarSetting;
import project.controls.PageIndicator;
import project.models.Product;
import project.models.ProductImage;
import project.models.ProductImageViewModel;
import project.models.ProductViewModel;
import project.repository.IResponseListener;
import project.repository.RetrieveRepositoryData;

import static project.base.BaseApplication.getBaseApplication;
import static project.comon.NumberTextWatcherForThousand.GetCurrency;

public class ProductDetailActivity extends BaseActivity {
    final static int LAUNCH_FAVOURITE_FRAGMENT = 1;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 3000;
    @BindView(R.id.txtProductDetailName)
    TextView txtProductDetailName;
    @BindView(R.id.txtProductDetailCode)
    TextView txtProductDetailCode;
    @BindView(R.id.btnFavorite)
    ImageButton btnFavorite;
    @BindView(R.id.btnNotification)
    ImageButton btnNotification;
    @BindView(R.id.rateProductDetail)
    RatingBar rateProductDetail;
    @BindView(R.id.btnAddBasket)
    Button btnAddBasket;
    int currentPage = 0;
    Timer timer;
    ImagePagerAdapter imagePagerAdapter;
    int productId;
    HashMap<String, String> slider_map;
    Product selectedProduct;
    @Inject
    ProductViewModel productViewModel;
    @Inject
    ProductImageViewModel productImageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getBaseApplication().getApplicationComponent().inject(this);
        super.onCreate(savedInstanceState);
        setActionBarSetting();
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            productId = bundle.getInt("SELECTED_PRODUCT_ID");
            getProductDetails();
        }
    }

    @OnClick(R.id.btnFavorite)
    public void onButtonFavoriteClick(View view) {
        if (getBaseApplication().getUser() == null) {
            Toast.makeText(getBaseContext(), getString(R.string.account_warning), Toast.LENGTH_LONG).show();
            ImageButton btnProfile = (ImageButton) findViewById(R.id.btnProfile);
            btnProfile.callOnClick();
        } else {
            addProductToFavouriteList();
        }
    }

    @OnClick(R.id.btnAddBasket)
    public void onButtonAddBasketClick(View view) {
        if (getBaseApplication().getUser() == null) {
            Toast.makeText(getBaseContext(), getString(R.string.account_warning), Toast.LENGTH_LONG).show();
            ImageButton buttonProfile = (ImageButton) findViewById(R.id.btnProfile);
            buttonProfile.callOnClick();
        } else {
            addProductToBasketList();
        }
    }

    public void setActionBarSetting() {
        new ActionBarSetting(this)
                .hasActionbar(false)
                .build();
    }

    private void getProductDetails() {
       // productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "product_detail");
        map.put("product_id", "" + productId);
        RetrieveRepositoryData.getData(productViewModel.makeFutureProduct(map), productViewModel.getLiveDataProduct(map), new IResponseListener<Product>() {
            @Override
            public void OnResponseComplete(Product product) {
                selectedProduct = new Product();
                selectedProduct = product;
                txtProductDetailName.setText(selectedProduct.getProductName());
                txtProductDetailCode.setText(selectedProduct.getProductCode());
                rateProductDetail.setRating(selectedProduct.getProductRate());
                if (selectedProduct.getProductCount() == 0) {
                    btnAddBasket.setText(getString(R.string.not_exists));
                    btnAddBasket.setEnabled(false);
                } else {
                    btnAddBasket.setText(GetCurrency(selectedProduct.getProductPrice() + " "));
                }
                getProductImages();
            }
        });
    }

    private void getProductImages() {
      //  productImageViewModel = new ViewModelProvider(this).get(ProductImageViewModel.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "image");
        map.put("product_id", "" + productId);
        RetrieveRepositoryData.getData(productImageViewModel.makeFutureProductImage(map), productImageViewModel.getLiveDataProductImage(map), new IResponseListener<ArrayList<ProductImage>>() {
            @Override
            public void OnResponseComplete(ArrayList<ProductImage> productImages) {
                slider_map = new HashMap<String, String>();
                for (ProductImage productImage : productImages) {
                    slider_map.put("" + productImage.getImageId(), productImage.getProductImageUrl());
                }
                createSlider();
            }
        });
    }

    private void createSlider() {
        final PageIndicator indicator = (PageIndicator) findViewById(R.id.indicator_produc);
        indicator.setIndicatorsCount(slider_map.size());
        final ViewPager pager = (ViewPager) findViewById(R.id.pager_product);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                indicator.setCurrentPage(position);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        imagePagerAdapter = new ImagePagerAdapter(getBaseContext(), slider_map, ImagePagerAdapter.HIDECAPTION);
        pager.setAdapter(imagePagerAdapter);
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == imagePagerAdapter.getCount()) {
                    currentPage = 0;
                }
                pager.setCurrentItem(currentPage++, true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getBaseApplication().getHandler().post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    private void addProductToFavouriteList() {
       // productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "insert_favourite");
        map.put("product_id", "" + productId);
        map.put("user_id", "" + getBaseApplication().getUser().getUserId());
        RetrieveRepositoryData.getData(productViewModel.makeFutureProduct(map), productViewModel.getLiveDataProduct(map), new IResponseListener<Product>() {
            @Override
            public void OnResponseComplete(Product product) {
                Intent intent = new Intent(ProductDetailActivity.this, HomeActivity.class);
                intent.putExtra("FRAGMENT", "Favourite");
                startActivity(intent);
                finish();
            }
        });
    }

    private void addProductToBasketList() {
       // productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        selectedProduct.setBasketProductCount(1);
        getBaseApplication().setUserBasketList(selectedProduct);
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "insert_basket");
        map.put("product_id", "" + productId);
        map.put("user_id", "" + getBaseApplication().getUser().getUserId());
        RetrieveRepositoryData.getData(productViewModel.makeFutureProduct(map), productViewModel.getLiveDataProduct(map), new IResponseListener<Product>() {
            @Override
            public void OnResponseComplete(Product product) {
                Intent intent = new Intent(ProductDetailActivity.this, HomeActivity.class);
                intent.putExtra("FRAGMENT", "Basket");
                startActivity(intent);
                finish();
            }
        });
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