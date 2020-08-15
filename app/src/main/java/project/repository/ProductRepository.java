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
import project.models.Product;
import project.service.ServiceGenerator;

public class ProductRepository {

    public ProductRepository() {
    }

    public LiveData<ArrayList<Product>> getLiveDataProducts(HashMap<String, String> map) {
        ServiceApi productService = ServiceGenerator.createService(ServiceApi.class);
        return LiveDataReactiveStreams.fromPublisher(productService.GetFlowableProducts(map)
                .subscribeOn(Schedulers.io()));
    }

    public Future<Observable<ArrayList<Product>>> makeFutureQueryProducts(HashMap<String, String> map) {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Callable<Observable<ArrayList<Product>>> myNetworkCallable = new Callable<Observable<ArrayList<Product>>>() {
            @Override
            public Observable<ArrayList<Product>> call() throws Exception {
                ServiceApi productService = ServiceGenerator.createService(ServiceApi.class);
                return productService.GetObservableProducts(map);
            }
        };
        final Future<Observable<ArrayList<Product>>> futureObservable = new Future<Observable<ArrayList<Product>>>() {
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
            public Observable<ArrayList<Product>> get() throws ExecutionException, InterruptedException {
                return executor.submit(myNetworkCallable).get();
            }

            @Override
            public Observable<ArrayList<Product>> get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
                return executor.submit(myNetworkCallable).get(timeout, unit);
            }
        };
        return futureObservable;
    }

    public LiveData<Product> getLiveDataProduct(HashMap<String, String> map) {
        ServiceApi productService = ServiceGenerator.createService(ServiceApi.class);
        return LiveDataReactiveStreams.fromPublisher(productService.GetFlowableProduct(map)
                .subscribeOn(Schedulers.io()));
    }

    public Future<Observable<Product>> makeFutureQueryProduct(HashMap<String, String> map) {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Callable<Observable<Product>> myNetworkCallable = new Callable<Observable<Product>>() {
            @Override
            public Observable<Product> call() throws Exception {
                ServiceApi productService = ServiceGenerator.createService(ServiceApi.class);
                return productService.GetObservableProduct(map);
            }
        };
        final Future<Observable<Product>> futureObservable = new Future<Observable<Product>>() {
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
            public Observable<Product> get() throws ExecutionException, InterruptedException {
                return executor.submit(myNetworkCallable).get();
            }

            @Override
            public Observable<Product> get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
                return executor.submit(myNetworkCallable).get(timeout, unit);
            }
        };
        return futureObservable;
    }
}
