package project.service;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    public static final String API_BASE_URL = "http://169.254.131.120/online-shop/";
    //http://192.168.3.247/online-shop/
    //http://192.168.125.2/online-shop/
    //http://169.254.131.120/online-shop/
    private static Retrofit.Builder builder;

    public static <S> S createService(Class<S> serviceClass) {
        builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit.create(serviceClass);
    }
}
