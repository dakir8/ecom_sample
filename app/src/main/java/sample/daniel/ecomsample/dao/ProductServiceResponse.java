package sample.daniel.ecomsample.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by danielqiu on 24/3/16.
 */
public class ProductServiceResponse implements Serializable {

    List<Product> results;

    public List<Product> getResults() {
        return results;
    }

    public void setResults(List<Product> results) {
        this.results = results;
    }
}
