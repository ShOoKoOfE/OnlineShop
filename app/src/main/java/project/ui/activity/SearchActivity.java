package project.ui.activity;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.shokoofeadeli.onlineshop.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import project.adapter.SearchRecyclerAdapter;
import project.base.BaseActivity;
import project.comon.ActionBarSetting;
import project.models.Product;
import project.models.ProductViewModel;
import project.repository.IResponseListener;
import project.repository.RetrieveRepositoryData;

import static project.base.BaseApplication.getBaseApplication;

public class SearchActivity extends BaseActivity {
    Unbinder unbinder;
    @BindView(R.id.lstSearch)
    RecyclerView lstSearch;
    @BindView(R.id.svSearch)
    SearchView searchView;
    SearchRecyclerAdapter searchRecyclerAdapter;
    @Inject
    ProductViewModel productViewModel;
    private Disposable disposable;

    public static Observable<String> observableQueryText(SearchView searchView) {
        final PublishSubject<String> subject = PublishSubject.create();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                subject.onComplete();
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                subject.onNext(text);
                return false;
            }
        });
        return subject;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getBaseApplication().getApplicationComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView = findViewById(R.id.svSearch);
        lstSearch = findViewById(R.id.lstSearch);
        ButterKnife.bind(this);
        setActionBarSetting();
        observableQueryText(searchView)
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())// if you already use RxJava in your project, it's an elegant way to switch to the required thread
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(String query) {
                        getSearchList(query);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    private void getSearchList(String query) {
      //  productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "search_product");
        map.put("filter", query);
        RetrieveRepositoryData.getData(productViewModel.makeFutureProducts(map), productViewModel.getLiveDataProducts(map), new IResponseListener<ArrayList<Product>>() {
            @Override
            public void OnResponseComplete(ArrayList<Product> products) {
                searchRecyclerAdapter = new SearchRecyclerAdapter(SearchActivity.this, products);
                lstSearch.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                lstSearch.setAdapter(searchRecyclerAdapter);
            }
        });
    }

    private void setActionBarSetting() {
        new ActionBarSetting(this)
                .setBackground(getResources().getDrawable(R.drawable.actionbar_nologo))
                .setTitle(getString(R.string.search))
                .build();
    }
}



