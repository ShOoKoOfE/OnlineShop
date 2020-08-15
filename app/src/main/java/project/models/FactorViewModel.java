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
import project.repository.FactorRepository;

public class FactorViewModel extends AndroidViewModel {

    private FactorRepository factorRepository;

    @Inject
    public FactorViewModel(@NonNull Application application,FactorRepository factorRepository) {
        super(application);
        this.factorRepository = factorRepository;
    }

    public LiveData<ArrayList<Factor>> getLiveDataFactors(HashMap<String, String> map) {
        return factorRepository.getLiveDataFactors(map);
    }

    public Future<Observable<ArrayList<Factor>>> makeFutureFactors(HashMap<String, String> map) {
        return factorRepository.makeFutureQueryFactors(map);
    }

    public LiveData<Factor> getLiveDataFactor(HashMap<String, String> map) {
        return factorRepository.getLiveDataFactor(map);
    }

    public Future<Observable<Factor>> makeFutureFactor(HashMap<String, String> map) {
        return factorRepository.makeFutureQueryFactor(map);
    }
}
