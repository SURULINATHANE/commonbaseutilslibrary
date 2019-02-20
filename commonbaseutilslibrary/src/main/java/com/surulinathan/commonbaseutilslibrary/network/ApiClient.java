package com.surulinathan.commonbaseutilslibrary.network;

import com.google.gson.Gson;
import com.surulinathan.commonbaseutilslibrary.BuildConfig;
import com.surulinathan.commonbaseutilslibrary.util.GsonWrapper;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.surulinathan.commonbaseutilslibrary.BuildConfig.api_host_name;
import static com.surulinathan.commonbaseutilslibrary.constants.Constants.BUILD_TYPE_DEBUG;


public class ApiClient {
    private static final ApiClient ourInstance = new ApiClient();

    public static ApiClient getInstance() {
        return ourInstance;
    }

    private Retrofit retrofit;

    private ApiClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.BUILD_TYPE.equals(BUILD_TYPE_DEBUG)) {
            builder.addInterceptor(getLoggingInterceptor());
        }

        //builder.addInterceptor(new ApiInterceptor());
        //builder.authenticator(new TokenAuthenticator());
        OkHttpClient okHttpClient = builder
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(28, TimeUnit.SECONDS)
                // .authenticator(new TokenAuthenticator())
                // .addInterceptor(new ApiInterceptor())
                .build();

        Gson gson = GsonWrapper.newInstance();
        retrofit = new Retrofit.Builder()
                .baseUrl(api_host_name)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory
                        .createWithScheduler(Schedulers.io()))
                .client(okHttpClient)
                .build();
    }

    public Retrofit retrofit() {
        return retrofit;
    }

    private HttpLoggingInterceptor getLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    public <T> T createService(Class<T> service) {
        return ApiClient.getInstance().
                retrofit().create(service);
    }
}
