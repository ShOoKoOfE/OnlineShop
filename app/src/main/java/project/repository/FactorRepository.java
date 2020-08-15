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
import project.models.Factor;
import project.service.ServiceGenerator;

public class FactorRepository {

    public FactorRepository() {
    }

    public LiveData<ArrayList<Factor>> getLiveDataFactors(HashMap<String, String> map) {
        ServiceApi factorService = ServiceGenerator.createService(ServiceApi.class);
        return LiveDataReactiveStreams.fromPublisher(factorService.GetFlowableFactors(map)
                .subscribeOn(Schedulers.io()));
    }

    public Future<Observable<ArrayList<Factor>>> makeFutureQueryFactors(HashMap<String, String> map) {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Callable<Observable<ArrayList<Factor>>> myNetworkCallable = new Callable<Observable<ArrayList<Factor>>>() {
            @Override
            public Observable<ArrayList<Factor>> call() throws Exception {
                ServiceApi factorService = ServiceGenerator.createService(ServiceApi.class);
                return factorService.GetObservableFactors(map);
            }
        };
        final Future<Observable<ArrayList<Factor>>> futureObservable = new Future<Observable<ArrayList<Factor>>>() {
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
            public Observable<ArrayList<Factor>> get() throws ExecutionException, InterruptedException {
                return executor.submit(myNetworkCallable).get();
            }

            @Override
            public Observable<ArrayList<Factor>> get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
                return executor.submit(myNetworkCallable).get(timeout, unit);
            }
        };
        return futureObservable;
    }

    public LiveData<Factor> getLiveDataFactor(HashMap<String, String> map) {
        ServiceApi factorService = ServiceGenerator.createService(ServiceApi.class);
        return LiveDataReactiveStreams.fromPublisher(factorService.GetFlowableFactor(map)
                .subscribeOn(Schedulers.io()));
    }

    public Future<Observable<Factor>> makeFutureQueryFactor(HashMap<String, String> map) {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Callable<Observable<Factor>> myNetworkCallable = new Callable<Observable<Factor>>() {
            @Override
            public Observable<Factor> call() throws Exception {
                ServiceApi factorService = ServiceGenerator.createService(ServiceApi.class);
                return factorService.GetObservableFactor(map);
            }
        };
        final Future<Observable<Factor>> futureObservable = new Future<Observable<Factor>>() {
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
            public Observable<Factor> get() throws ExecutionException, InterruptedException {
                return executor.submit(myNetworkCallable).get();
            }

            @Override
            public Observable<Factor> get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
                return executor.submit(myNetworkCallable).get(timeout, unit);
            }
        };
        return futureObservable;
    }
}
