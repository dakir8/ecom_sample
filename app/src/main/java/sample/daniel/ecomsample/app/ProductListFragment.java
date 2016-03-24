package sample.daniel.ecomsample.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.daniel.ecomsample.dao.Product;
import sample.daniel.ecomsample.dao.ProductServiceResponse;
import sample.daniel.ecomsample.service.WebService;
import sample.daniel.ecomsample.service.WebServiceInstance;

/**
 * Created by danielqiu on 24/3/16.
 */
public class ProductListFragment extends BaseFragment {

    private final String TAG = "ProductListFragment";

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    private ProductListdapter mAdapter;
    private ProductServiceResponse mProductServiceResponse;
    private List<Product> mProducts;

    public static ProductListFragment newInstance() {

        Bundle args = new Bundle();

        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        fetchProducts();
    }

    private void fetchProducts()
    {
        WebService service = WebServiceInstance.getRetrofitInstance().create(WebService.class);
        service.listProducts().enqueue(new Callback<ProductServiceResponse>() {
            @Override
            public void onResponse(Call<ProductServiceResponse> call, Response<ProductServiceResponse> response) {

                Log.d(TAG, response.message());
                if (getActivity() == null) return;

                if (response.isSuccessful()) {
                    mProductServiceResponse = response.body();
                    if (mProductServiceResponse == null) return;

                    mProducts = mProductServiceResponse.getResults();

                    mAdapter = new ProductListdapter(getActivity(), mProducts);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    // Todo
                    Log.d(TAG, response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ProductServiceResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    public static class ProductCellVH extends RecyclerView.ViewHolder
    {
        public View root;
        public TextView tvProductName;
        public ImageView imgThumbnail;
        public TextView tvBrandName;

        public ProductCellVH(View view)
        {
            super(view);

            root = view.findViewById(R.id.root);
            tvProductName = (TextView) view.findViewById(R.id.tvProductName);
            tvBrandName = (TextView) view.findViewById(R.id.tvBrandName);
            imgThumbnail = (ImageView) view.findViewById(R.id.imgThumbnail);

        }

    }

    public static class ProductListdapter extends RecyclerView.Adapter<ProductCellVH> {
        private Context context;
        private List<Product> products;

        private int DUMMY_THUMBNAILS[] = {R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5,
                R.drawable.p6, R.drawable.p7, R.drawable.p8, R.drawable.p9, R.drawable.p10};

        public ProductListdapter(Context context, List<Product> products) {
            this.context = context;
            this.products = products;
        }


        @Override
        public ProductCellVH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ProductCellVH(LayoutInflater.from(context).inflate(R.layout.cell_product_list, parent, false));
        }

        @Override
        public void onBindViewHolder(ProductCellVH holder, int position) {
            Product item = products.get(position);

            if (item == null) return;

            holder.tvProductName.setText(item.getProductName());
            holder.tvBrandName.setText("test brand");
            Picasso.with(context).load(DUMMY_THUMBNAILS[position % DUMMY_THUMBNAILS.length]).into(holder.imgThumbnail);
        }

        @Override
        public int getItemCount() {
            return products == null ? 0 : products.size();
        }
    }

}
