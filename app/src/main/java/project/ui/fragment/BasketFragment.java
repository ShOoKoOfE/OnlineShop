package project.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import project.adapter.BasketRecyclerAdapter;
import project.models.Factor;
import project.models.FactorViewModel;
import project.models.Product;
import project.repository.IResponseListener;
import project.repository.RetrieveRepositoryData;
import project.ui.activity.HomeActivity;

import static project.base.BaseApplication.getBaseApplication;
import static project.comon.NumberTextWatcherForThousand.GetCurrency;

public class BasketFragment extends Fragment {
    Unbinder unbinder;
    BasketRecyclerAdapter basketAdapter;
    @Nullable
    @BindView(R.id.txtTotalPrice)
    TextView txtTotalPrice;
    @Nullable
    @BindView(R.id.lstSpinner)
    RecyclerView lstSpinner;
    @Nullable
    @BindView(R.id.lstBasket)
    RecyclerView lstBasket;
    @Nullable
    @BindView(R.id.btnBuy)
    Button btnBuy;
    @Nullable
    @BindView(R.id.linearFactor)
    LinearLayout linearFactor;
    @Inject
    FactorViewModel factorViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        if (getBaseApplication().getUserBasketList().size() == 0) {
            view = inflater.inflate(R.layout.fragment_empty_basket, container, false);
        } else {
            view = inflater.inflate(R.layout.fragment_basket, container, false);
        }
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

        if (getBaseApplication().getUserBasketList().size() != 0) {
            long totalPrice = 0;
            for (Product product : getBaseApplication().getUserBasketList()) {
                totalPrice += product.ProductPrice();
            }
            txtTotalPrice.setText(GetCurrency(totalPrice + " "));
            lstBasket.setLayoutManager(new LinearLayoutManager(getActivity()));
            lstSpinner.setLayoutManager(new LinearLayoutManager(getActivity()));
            basketAdapter = new BasketRecyclerAdapter(getContext(), getBaseApplication().getUserBasketList(), lstSpinner, txtTotalPrice);
            lstBasket.setAdapter(basketAdapter);
            btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createUserFactor();
                }
            });
        } else {
            linearFactor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getBaseApplication().getHandler().post(new Runnable() {
                        public void run() {
                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            intent.putExtra("FRAGMENT", "History");
                            startActivity(intent);
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.menu_search);
        if (item != null)
            item.setVisible(false);
    }

    private void createUserFactor() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "insert_factor");
        map.put("user_id", "" + getBaseApplication().getUser().getUserId());
        RetrieveRepositoryData.getData(factorViewModel.makeFutureFactors(map), factorViewModel.getLiveDataFactors(map), new IResponseListener<ArrayList<Factor>>() {
            @Override
            public void OnResponseComplete(ArrayList<Factor> factors) {
                getBaseApplication().clearUserBasketList();
                getBaseApplication().getHandler().post(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        intent.putExtra("FRAGMENT", "History");
                        startActivity(intent);
                    }
                });
            }
        });
    }
}




