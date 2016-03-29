package sample.daniel.ecomsample.dao;

import java.io.Serializable;

/**
 * Created by Daniel on 16/3/29.
 */
public class ProductID implements Serializable {

    public ProductID(String objectId) {
        this.objectId = objectId;
    }

    private String __type = "Pointer";
    private String className = "Product";
    private String objectId;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
