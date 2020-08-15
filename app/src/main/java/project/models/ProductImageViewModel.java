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
import project.repository.ImageRepository;

public class ProductImageViewModel extends AndroidViewModel {

    private ImageRepository imageRepository;

    @Inject
    public ProductImageViewModel(@NonNull Application application,ImageRepository imageRepository) {
        super(application);
        this.imageRepository = imageRepository;
    }

    public LiveData<ArrayList<ProductImage>> getLiveDataProductImage(HashMap<String, String> map) {
        return imageRepository.getLiveDataProductImage(map);
    }

    public Future<Observable<ArrayList<ProductImage>>> makeFutureProductImage(HashMap<String, String> map) {
        return imageRepository.makeFutureQueryProductImage(map);
    }
}