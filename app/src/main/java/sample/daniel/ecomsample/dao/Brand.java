package sample.daniel.ecomsample.dao;

import java.io.Serializable;

/**
 * Created by danielqiu on 24/3/16.
 */
public class Brand implements Serializable {

    String name;
    String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
