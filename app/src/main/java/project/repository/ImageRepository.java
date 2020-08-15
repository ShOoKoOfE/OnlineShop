package project.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import project.api.ServiceApi;
import project.service.ServiceGenerator;
import project.models.ProductImage;

public class ImageRepository {

    public ImageRepository() {
    }

    public LiveData<ArrayList<ProductImage>> getLiveDataProductImage(HashMap<String, String> map) {
        ServiceApi productImageService = ServiceGenerator.createService(ServiceApi.class);
        return LiveDataReactiveStreams.fromPublisher(productImageService.GetFlowableImages(map)
                .subscribeOn(Schedulers.io()));
    }

    public Future<Observable<ArrayList<ProductImage>>> makeFutureQueryProductImage(HashMap<String, String> map) {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Callable<Observable<ArrayList<ProductImage>>> myNetworkCallable = new Callable<Observable<ArrayList<ProductImage>>>() {
            @Override
            public Observable<ArrayList<ProductImage>> call() throws Exception {
                ServiceApi productImageService = ServiceGenerator.createService(ServiceApi.class);
                return productImageService.GetObservableImages(map);
            }
        };
        final Future<Observable<ArrayList<ProductImage>>> futureObservable = new Future<Observable<ArrayList<ProductImage>>>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                if (mayInterruptIfRunning) {
                    executor.shutdown();
                }
                return false;
            }

            @Override
            public boolean isCancelled() {
                return executor.isShutdown();
            }

            @Override
            public boolean isDone() {
                return executor.isTerminated();
            }

            @Override
            public Observable<ArrayList<ProductImage>> get() throws ExecutionException, InterruptedException {
                return executor.submit(myNetworkCallable).get();
            }

            @Override
            public Observable<ArrayList<ProductImage>> get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
                return executor.submit(myNetworkCallable).get(timeout, unit);
            }
        };
        return futureObservable;
    }
}
