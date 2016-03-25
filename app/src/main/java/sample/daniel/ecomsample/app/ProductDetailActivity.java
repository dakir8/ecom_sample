package sample.daniel.ecomsample.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import sample.daniel.ecomsample.dao.Product;

public class ProductDetailActivity extends BaseActivity {

    public static final String EXTRA_PRODUCT = "EXTRA_PRODUCT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_detail);

        Product product = (Product) getIntent().getSerializableExtra(EXTRA_PRODUCT);

        if (product == null) {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.productDetailProductNotFound)
                    .setNeutralButton(R.string.dlgBtnOk, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            supportFinishAfterTransition();
                        }
                    })
                    .show();
            return;
        }

        Fragment showingFragment = getSupportFragmentManager().findFragmentById(R.id.content);
        if (showingFragment == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.content,ProductDetailFragment.newInstance(product), ProductDetailFragment.class.getName()).commit();
        }
    }
}
