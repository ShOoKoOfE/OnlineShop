package project.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.HashMap;
import java.util.concurrent.Future;

import javax.inject.Inject;

import io.reactivex.Observable;
import project.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;

    @Inject
    public UserViewModel(@NonNull Application application,UserRepository userRepository) {
        super(application);
        this.userRepository = userRepository;
    }

    public LiveData<User> getLiveDataUser(HashMap<String, String> map) {
        return userRepository.getLiveDataUser(map);
    }

    public Future<Observable<User>> makeFutureUser(HashMap<String, String> map) {
        return userRepository.makeFutureQueryUser(map);
    }
}