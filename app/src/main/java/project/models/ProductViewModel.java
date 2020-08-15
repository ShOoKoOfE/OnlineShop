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
import project.repository.ProductRepository;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository productRepository;

    @Inject
    public ProductViewModel(@NonNull Application application,ProductRepository productRepository) {
        super(application);
        this.productRepository = productRepository;
    }

    public LiveData<ArrayList<Product>> getLiveDataProducts(HashMap<String, String> map) {
        return productRepository.getLiveDataProducts(map);
    }

    public Future<Observable<ArrayList<Product>>> makeFutureProducts(HashMap<String, String> map) {
        return productRepository.makeFutureQueryProducts(map);
    }

    public LiveData<Product> getLiveDataProduct(HashMap<String, String> map) {
        return productRepository.getLiveDataProduct(map);
    }

    public Future<Observable<Product>> makeFutureProduct(HashMap<String, String> map) {
        return productRepository.makeFutureQueryProduct(map);
    }
}