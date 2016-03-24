package sample.daniel.ecomsample.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by danielqiu on 24/3/16.
 */
public class BrandServiceResponse implements Serializable {

    List<Brand> results;

    public List<Brand> getResults() {
        return results;
    }

    public void setResults(List<Brand> results) {
        this.results = results;
    }
}
