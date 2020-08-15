package project.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Future;

import javax.inject.Inject;

import io.reactivex.Observable;
import project.repository.CategoryRepository;

public class CategoryViewModel extends AndroidViewModel {

    private CategoryRepository categoryRepository;

    @Inject
    public CategoryViewModel(@NonNull Application application,CategoryRepository categoryRepository) {
        super(application);
        this.categoryRepository = categoryRepository;
    }

    public LiveData<ArrayList<Category>> getLiveDataCategories(HashMap<String, String> map) {
        return categoryRepository.getLiveDataCategories(map);
    }

    public Future<Observable<ArrayList<Category>>> makeFutureCategories(HashMap<String, String> map) {
        return categoryRepository.makeFutureQueryCategories(map);
    }
}