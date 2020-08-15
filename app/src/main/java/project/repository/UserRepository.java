package project.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

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
import project.models.User;
import project.service.ServiceGenerator;

public class UserRepository {

    public UserRepository() {
    }

    public LiveData<User> getLiveDataUser(HashMap<String, String> map) {
        ServiceApi userService = ServiceGenerator.createService(ServiceApi.class);
        return LiveDataReactiveStreams.fromPublisher(userService.GetFlowableUser(map)
                .subscribeOn(Schedulers.io()));
    }

    public Future<Observable<User>> makeFutureQueryUser(HashMap<String, String> map) {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Callable<Observable<User>> myNetworkCallable = new Callable<Observable<User>>() {
            @Override
            public Observable<User> call() throws Exception {
                ServiceApi userService = ServiceGenerator.createService(ServiceApi.class);
                return userService.GetObservableUser(map);
            }
        };
        final Future<Observable<User>> futureObservable = new Future<Observable<User>>() {
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
            public Observable<User> get() throws ExecutionException, InterruptedException {
                return executor.submit(myNetworkCallable).get();
            }

            @Override
            public Observable<User> get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
                return executor.submit(myNetworkCallable).get(timeout, unit);
            }
        };
        return futureObservable;
    }
}
