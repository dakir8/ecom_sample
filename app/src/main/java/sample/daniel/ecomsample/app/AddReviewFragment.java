package sample.daniel.ecomsample.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sample.daniel.ecomsample.dao.Product;

/**
 * Created by Daniel on 16/3/29.
 */
public class AddReviewFragment extends DialogFragment {

    public static AddReviewFragment newInstance(Product product) {

        Bundle args = new Bundle();

        AddReviewFragment fragment = new AddReviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private Product mProduct;

    public void setProduct(Product product) {
        mProduct = product;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_review, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
