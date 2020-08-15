package project.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import project.adapter.AllCategoriesRecyclerAdapter;
import project.models.Category;
import project.models.CategoryViewModel;
import project.repository.IResponseListener;
import project.repository.RetrieveRepositoryData;

import static project.base.BaseApplication.getBaseApplication;

public class CategoriesFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.lstAllCategories)
    RecyclerView lstAllCategories;
    @Inject
    CategoryViewModel categoryViewModel;
    private String categoryType;

    public CategoriesFragment(String categoryType) {
        this.categoryType = categoryType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
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
        getCategoryList();
    }

    private void getCategoryList() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "category");
        RetrieveRepositoryData.getData(categoryViewModel.makeFutureCategories(map), categoryViewModel.getLiveDataCategories(map), new IResponseListener<ArrayList<Category>>() {
            @Override
            public void OnResponseComplete(ArrayList<Category> categories) {
                ArrayList<Category> categoryArrayList = new ArrayList<>();
                if (!categoryType.equals("ALL")) {
                    for (Category item : categories) {
                        if (item.getCategoryType().equals(categoryType)) {
                            categoryArrayList.add(item);
                        }
                    }
                } else {
                    categoryArrayList = categories;
                }
                AllCategoriesRecyclerAdapter categoriesRecyclerAdapter = new AllCategoriesRecyclerAdapter(getBaseApplication().getCurrentActivity(), categoryArrayList);
                lstAllCategories.setLayoutManager(new LinearLayoutManager(getActivity()));
                lstAllCategories.setAdapter(categoriesRecyclerAdapter);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
