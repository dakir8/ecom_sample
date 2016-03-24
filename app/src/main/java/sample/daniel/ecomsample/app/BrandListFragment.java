package sample.daniel.ecomsample.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
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
import sample.daniel.ecomsample.dao.Brand;
import sample.daniel.ecomsample.dao.BrandServiceResponse;
import sample.daniel.ecomsample.service.WebService;
import sample.daniel.ecomsample.service.WebServiceInstance;

/**
 * Created by danielqiu on 24/3/16.
 */
public class BrandListFragment extends BaseFragment {

    public static BrandListFragment newInstance() {

        Bundle args = new Bundle();

        BrandListFragment fragment = new BrandListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private final String TAG = "BrandListFragment";

    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;

    private BrandListdapter mAdapter;
    private BrandServiceResponse mServiceResponse;
    private List<Brand> mBrands;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_brand_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        fetchBrands();
    }

    private void fetchBrands() {
        WebService service = WebServiceInstance.getRetrofitInstance().create(WebService.class);
        service.listBrands().enqueue(new Callback<BrandServiceResponse>() {
            @Override
            public void onResponse(Call<BrandServiceResponse> call, Response<BrandServiceResponse> response) {

                Log.d(TAG, response.message());
                if (getActivity() == null) return;

                if (response.isSuccessful()) {
                    mServiceResponse = response.body();
                    if (mServiceResponse == null) return;

                    mBrands = mServiceResponse.getResults();

                    mAdapter = new BrandListdapter(getActivity(), mBrands);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    // Todo
                    Log.d(TAG, response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<BrandServiceResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    public static class BrandCellVH extends RecyclerView.ViewHolder
    {
        public View root;
        public ImageView imgThumbnail;
        public TextView tvBrandName;

        public BrandCellVH(View view)
        {
            super(view);

            root = view.findViewById(R.id.root);
            tvBrandName = (TextView) view.findViewById(R.id.tvBrandName);
            imgThumbnail = (ImageView) view.findViewById(R.id.imgThumbnail);

        }

    }

    public static class BrandListdapter extends RecyclerView.Adapter<BrandCellVH> {
        private Context context;
        private List<Brand> brands;

        private int DUMMY_THUMBNAILS[] = {R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5,
                R.drawable.p6, R.drawable.p7, R.drawable.p8, R.drawable.p9, R.drawable.p10};

        public BrandListdapter(Context context, List<Brand> brands) {
            this.context = context;
            this.brands = brands;
        }


        @Override
        public BrandCellVH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BrandCellVH(LayoutInflater.from(context).inflate(R.layout.cell_brand_list, parent, false));
        }

        @Override
        public void onBindViewHolder(BrandCellVH holder, int position) {
            Brand item = brands.get(position);

            if (item == null) return;

            holder.tvBrandName.setText(item.getName());
            Picasso.with(context).load(DUMMY_THUMBNAILS[position % DUMMY_THUMBNAILS.length]).into(holder.imgThumbnail);
        }

        @Override
        public int getItemCount() {
            return brands == null ? 0 : brands.size();
        }
    }

}
