package sample.daniel.ecomsample.service;


import com.squareup.picasso.Downloader;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by danielqiu on 24/3/16.
 */
public class WebServiceInstance {

    private static WebServiceInstance _instance;

    public static Retrofit getRetrofitInstance() {
        if (_instance == null) {
            _instance = new WebServiceInstance();
        }
        return _instance.createRetroFit();
    }

    private OkHttpClient httpClient;

    public WebServiceInstance() {
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Interceptor.Chain chain) throws IOException {
                                Request original = chain.request();

                                // Request customization: add request headers
                                Request.Builder requestBuilder = original.newBuilder()
                                        .addHeader("X-Parse-Application-Id", "MlR6vYpYvLRxfibxE5cg0e73jXojL6jWFqXU6F8L")
                                        .addHeader("X-Parse-REST-API-Key", "7BTXVX1qUXKUCnsngL8LxhpEHKQ8KKd798kKpD9W")
                                        .addHeader("Content-Type", "application/json")
                                        .addHeader("Cache-Control", "no-cache")
                                        .method(original.method(), original.body());

                                Request request = requestBuilder.build();
                                return chain.proceed(request);
                            }
                        })
                .build();
    }

    public Retrofit createRetroFit()
    {
        return new Retrofit.Builder()
                .baseUrl("https://api.parse.com/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }

}
