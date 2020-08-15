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
import androidx.viewpager.widget.ViewPager;

import com.shokoofeadeli.onlineshop.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import project.adapter.CategoryRecyclerAdapter;
import project.adapter.ImagePagerAdapter;
import project.controls.PageIndicator;
import project.models.Category;
import project.models.CategoryViewModel;
import project.repository.IResponseListener;
import project.repository.RetrieveRepositoryData;

import static project.base.BaseApplication.getBaseApplication;

public class HomeFragment extends Fragment {
    private final long DELAY_MS = 500;
    private final long PERIOD_MS = 3000;
    @BindView(R.id.lstCategory)
    public RecyclerView lstCategory;
    Unbinder unbinder;
    @Inject
    CategoryViewModel categoryViewModel;
    private LinearLayoutManager layoutManager;
    private int currentPage = 0;
    private Timer timer;
    private ImagePagerAdapter imagePagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createSlider();
        getCategoryList();
    }

    @Override
    public void onAttach(Context context) {
        getBaseApplication().getApplicationComponent().inject(this);
        super.onAttach(context);
    }

    private void getCategoryList() {
       // categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "category");
        RetrieveRepositoryData.getData(categoryViewModel.makeFutureCategories(map), categoryViewModel.getLiveDataCategories(map), new IResponseListener<ArrayList<Category>>() {
            @Override
            public void OnResponseComplete(ArrayList<Category> categories) {
                ArrayList<Category> mainCategoryList = new ArrayList<>();
                for (Category item : categories) {
                    if (item.getCategoryType().equals("MAIN")) {
                        mainCategoryList.add(item);
                    }
                }
                layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                lstCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
                CategoryRecyclerAdapter categoryRecyclerAdapter = new CategoryRecyclerAdapter(getBaseApplication().getCurrentActivity(), mainCategoryList);
                lstCategory.setLayoutManager(layoutManager);
                lstCategory.setAdapter(categoryRecyclerAdapter);
            }
        });
    }

    private void createSlider() {
        final PageIndicator indicator = getActivity().findViewById(R.id.indicator);

        HashMap<String, String> slider_map = new HashMap<>();
        slider_map.put(getString(R.string.mainslider01), "slider-images/mainslider01.png");
        slider_map.put(getString(R.string.mainslider02), "slider-images/mainslider02.png");
        slider_map.put(getString(R.string.mainslider03), "slider-images/mainslider03.png");
        slider_map.put(getString(R.string.mainslider04), "slider-images/mainslider04.png");

        indicator.setIndicatorsCount(slider_map.size());

        final ViewPager pager = getActivity().findViewById(R.id.pager);
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

        imagePagerAdapter = new ImagePagerAdapter(getContext(), slider_map, ImagePagerAdapter.SHOWCAPTION);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
