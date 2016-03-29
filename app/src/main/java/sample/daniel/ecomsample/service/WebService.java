package sample.daniel.ecomsample.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import sample.daniel.ecomsample.dao.BrandServiceResponse;
import sample.daniel.ecomsample.dao.ProductServiceResponse;

/**
 * Created by danielqiu on 24/3/16.
 */
public interface WebService {
    @GET("classes/Product")
    Call<ProductServiceResponse> listProducts();

    @GET("classes/Product?where={\"brandID\":{\"$inQuery\":{\"where\":{\"name\":\"BrandName\"},\"className\":\"Brand\"}}}&order=dateCreated&limit=10")
    Call<ProductServiceResponse> listProducts(@Query("BrandName") String brandName);

    @GET("classes/Brand")
    Call<BrandServiceResponse> listBrands();
}
