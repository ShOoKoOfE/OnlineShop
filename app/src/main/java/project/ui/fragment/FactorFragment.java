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
import project.adapter.FactorRecyclerAdapter;
import project.models.Factor;
import project.models.FactorViewModel;
import project.repository.IResponseListener;
import project.repository.RetrieveRepositoryData;

import static project.base.BaseApplication.getBaseApplication;

public class FactorFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.lstFactor)
    RecyclerView lstFactor;
    FactorRecyclerAdapter factorRecyclerAdapter;

    @Inject
    FactorViewModel factorViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_factor, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        getUserFactorList();
    }

    @Override
    public void onAttach(Context context) {
        getBaseApplication().getApplicationComponent().inject(this);
        super.onAttach(context);
    }

    private void getUserFactorList() {
        //factorViewModel = new ViewModelProvider(this).get(FactorViewModel.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "factor");
        map.put("user_id", "" + getBaseApplication().getUser().getUserId());
        RetrieveRepositoryData.getData(factorViewModel.makeFutureFactors(map), factorViewModel.getLiveDataFactors(map), new IResponseListener<ArrayList<Factor>>() {
            @Override
            public void OnResponseComplete(ArrayList<Factor> factors) {
                lstFactor.setLayoutManager(new LinearLayoutManager(getActivity()));
                factorRecyclerAdapter = new FactorRecyclerAdapter(getActivity(), factors);
                lstFactor.setAdapter(factorRecyclerAdapter);
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


