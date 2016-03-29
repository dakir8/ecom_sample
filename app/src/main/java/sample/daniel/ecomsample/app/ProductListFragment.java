package sample.daniel.ecomsample.app;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.daniel.ecomsample.dao.Brand;
import sample.daniel.ecomsample.dao.Product;
import sample.daniel.ecomsample.dao.ProductServiceResponse;
import sample.daniel.ecomsample.service.WebService;
import sample.daniel.ecomsample.service.WebServiceInstance;
import sample.daniel.ecomsample.ui.DividerItemDecoration;

/**
 * Created by danielqiu on 24/3/16.
 */
public class ProductListFragment extends BaseFragment {

    private final String TAG = "ProductListFragment";

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private Toolbar mToolbar;

    private ProductListdapter mAdapter;
    private ProductServiceResponse mProductServiceResponse;
    private List<Product> mProducts;
    private Brand mBrand;

    public static ProductListFragment newInstance() {

        Bundle args = new Bundle();

        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe
    public void onEventMainThread(BrandListFragment.BrandSelectionMsg msg)
    {
        mBrand = msg.brand;

        fetchProducts();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupToolbar();

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST,
                getResources().getDrawable(R.drawable.bg_divider)));

        fetchProducts();
    }

    private void setupToolbar()
    {
        mToolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        if (getActivity() != null && mToolbar != null)
        {
            // set title
            mToolbar.setTitle("Product List");

            // set navigation
            mToolbar.inflateMenu(R.menu.product_list);
            mToolbar.setOnMenuItemClickListener(mOnMenuItemClickListener);
        }
    }

    private Toolbar.OnMenuItemClickListener mOnMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.edit:
                    BrandListFragment.newInstance().show(getChildFragmentManager(), BrandListFragment.class.getSimpleName());
                    return true;
            }
            return false;
        }
    };

    private void fetchProducts()
    {
        WebService service = WebServiceInstance.getRetrofitInstance().create(WebService.class);
        Call<ProductServiceResponse> call = mBrand == null ? service.listProducts() : service.listProducts(mBrand.getName());
        call.enqueue(new Callback<ProductServiceResponse>() {
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

    private void onViewProductDetail(Product product, View animateView)
    {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra(ProductDetailActivity.EXTRA_PRODUCT, product);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && animateView != null) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), animateView, "CoverImage");

            getActivity().startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    public class ProductCellVH extends RecyclerView.ViewHolder
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

    public class ProductListdapter extends RecyclerView.Adapter<ProductCellVH> {
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
        public void onBindViewHolder(final ProductCellVH holder, int position) {
            final Product item = products.get(position);

            if (item == null) return;

            holder.tvProductName.setText(item.getProductName());
            holder.tvBrandName.setText("Brand Name");
            item.setImageDrawableId(DUMMY_THUMBNAILS[position % DUMMY_THUMBNAILS.length]);
            Picasso.with(context).load(item.getImageDrawableId()).into(holder.imgThumbnail);

            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onViewProductDetail(item, holder.imgThumbnail);
                }
            });
        }

        @Override
        public int getItemCount() {
            return products == null ? 0 : products.size();
        }
    }

}
