package project.api;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import project.models.Category;
import project.models.Factor;
import project.models.Product;
import project.models.ProductImage;
import project.models.User;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ServiceApi {
    @GET("services.php")
    Flowable<ArrayList<Category>> GetFlowableCategories(@QueryMap HashMap<String, String> map);

    @GET("services.php")
    Observable<ArrayList<Category>> GetObservableCategories(@QueryMap HashMap<String, String> map);

    @GET("services.php")
    Flowable<User> GetFlowableUser(@QueryMap HashMap<String, String> map);

    @GET("services.php")
    Observable<User> GetObservableUser(@QueryMap HashMap<String, String> map);

    @GET("services.php")
    Flowable<ArrayList<Product>> GetFlowableProducts(@QueryMap HashMap<String, String> map);

    @GET("services.php")
    Observable<ArrayList<Product>> GetObservableProducts(@QueryMap HashMap<String, String> map);

    @GET("services.php")
    Flowable<Product> GetFlowableProduct(@QueryMap HashMap<String, String> map);

    @GET("services.php")
    Observable<Product> GetObservableProduct(@QueryMap HashMap<String, String> map);

    @GET("services.php")
    Flowable<ArrayList<Factor>> GetFlowableFactors(@QueryMap HashMap<String, String> map);

    @GET("services.php")
    Observable<ArrayList<Factor>> GetObservableFactors(@QueryMap HashMap<String, String> map);

    @GET("services.php")
    Flowable<ArrayList<ProductImage>> GetFlowableImages(@QueryMap HashMap<String, String> map);

    @GET("services.php")
    Observable<ArrayList<ProductImage>> GetObservableImages(@QueryMap HashMap<String, String> map);

    @GET("services.php")
    Flowable<Factor> GetFlowableFactor(@QueryMap HashMap<String, String> map);

    @GET("services.php")
    Observable<Factor> GetObservableFactor(@QueryMap HashMap<String, String> map);
}
