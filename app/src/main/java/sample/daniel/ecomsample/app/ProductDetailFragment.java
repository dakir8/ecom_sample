package sample.daniel.ecomsample.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import sample.daniel.ecomsample.dao.Product;

public class ProductDetailFragment extends BaseFragment {

    private Product mProduct;

    private TextView mTvProductName, mTvNumReviews, mTvAvailabilityStatus, mTvPrice, mTvDescription;
    private ImageView mImgCover;

    public static ProductDetailFragment newInstance(Product product) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setProduct(product);
        return fragment;
    }

    public void setProduct(Product product) {
        mProduct = product;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTvProductName = (TextView) getView().findViewById(R.id.tvProductName);
        mTvNumReviews = (TextView) getView().findViewById(R.id.tvNumReviews);
        mTvAvailabilityStatus = (TextView) getView().findViewById(R.id.tvAvailabilityStatus);
        mTvPrice = (TextView) getView().findViewById(R.id.tvPrice);
        mTvDescription = (TextView) getView().findViewById(R.id.tvDescription);

        mImgCover = (ImageView) getView().findViewById(R.id.imgCover);

        setupData();
    }

    private void setupData() {
        mTvProductName.setText(mProduct.getProductName());
        mTvAvailabilityStatus.setText(mProduct.getAvailabilityStatus());
        mTvPrice.setText(mProduct.getPrice());
        mTvDescription.setText(mProduct.getDescription());

        int DUMMY_THUMBNAILS[] = {R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5,
                R.drawable.p6, R.drawable.p7, R.drawable.p8, R.drawable.p9, R.drawable.p10};

        Picasso.with(getActivity()).load(DUMMY_THUMBNAILS[(int)(Math.random() * 10) % DUMMY_THUMBNAILS.length]).into(mImgCover);
    }

}
