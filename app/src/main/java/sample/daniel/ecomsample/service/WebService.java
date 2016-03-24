package sample.daniel.ecomsample.service;

import retrofit2.Call;
import retrofit2.http.GET;
import sample.daniel.ecomsample.dao.BrandServiceResponse;
import sample.daniel.ecomsample.dao.ProductServiceResponse;

/**
 * Created by danielqiu on 24/3/16.
 */
public interface WebService {
    @GET("classes/Product")
    Call<ProductServiceResponse> listProducts();

    @GET("classes/Brand")
    Call<BrandServiceResponse> listBrands();
}
