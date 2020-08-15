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
import project.models.Category;
import project.service.ServiceGenerator;

public class CategoryRepository {

    public CategoryRepository() {
    }

    public LiveData<ArrayList<Category>> getLiveDataCategories(HashMap<String, String> map) {
        ServiceApi categoriesService = ServiceGenerator.createService(ServiceApi.class);
        return LiveDataReactiveStreams.fromPublisher(categoriesService.GetFlowableCategories(map)
                .subscribeOn(Schedulers.io()));
    }

    public Future<Observable<ArrayList<Category>>> makeFutureQueryCategories(HashMap<String, String> map) {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Callable<Observable<ArrayList<Category>>> myNetworkCallable = new Callable<Observable<ArrayList<Category>>>() {
            @Override
            public Observable<ArrayList<Category>> call() throws Exception {
                ServiceApi categoriesService = ServiceGenerator.createService(ServiceApi.class);
                return categoriesService.GetObservableCategories(map);
            }
        };
        final Future<Observable<ArrayList<Category>>> futureObservable = new Future<Observable<ArrayList<Category>>>() {
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
            public Observable<ArrayList<Category>> get() throws ExecutionException, InterruptedException {
                return executor.submit(myNetworkCallable).get();
            }

            @Override
            public Observable<ArrayList<Category>> get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
                return executor.submit(myNetworkCallable).get(timeout, unit);
            }
        };
        return futureObservable;
    }
}

