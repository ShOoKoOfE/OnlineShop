package project.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shokoofeadeli.onlineshop.R;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import project.adapter.FavouriteRecyclerAdapter;
import project.models.Product;
import project.models.ProductViewModel;
import project.repository.IResponseListener;
import project.repository.RetrieveRepositoryData;

import static project.base.BaseApplication.getBaseApplication;

public class FavouriteFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.lstFavourite)
    RecyclerView lstFavourite;
    FavouriteRecyclerAdapter favouriteRecyclerAdapter;

    @Inject
    ProductViewModel productViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        getBaseApplication().getApplicationComponent().inject(this);
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        getUserFavouriteList();
    }

    private void getUserFavouriteList() {
       // productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "favourite");
        map.put("user_id", "" + getBaseApplication().getUser().getUserId());
        RetrieveRepositoryData.getData(productViewModel.makeFutureProducts(map), productViewModel.getLiveDataProducts(map), new IResponseListener<ArrayList<Product>>() {
            @Override
            public void OnResponseComplete(ArrayList<Product> products) {
                lstFavourite.setLayoutManager(new LinearLayoutManager(getActivity()));
                favouriteRecyclerAdapter = new FavouriteRecyclerAdapter(getActivity(), products);
                lstFavourite.setAdapter(favouriteRecyclerAdapter);
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.menu_search);
        if (item != null)
            item.setVisible(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}



