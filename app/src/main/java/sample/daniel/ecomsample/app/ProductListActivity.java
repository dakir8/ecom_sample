package sample.daniel.ecomsample.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by danielqiu on 24/3/16.
 */
public class ProductListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_list);

        Fragment showingFragment = getSupportFragmentManager().findFragmentById(R.id.content);
        if (showingFragment == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.content,BrandListFragment.newInstance(), BrandListFragment.class.getName()).commit();
        }
    }
}
