package org.vaadin.alump.maplayout.client.shared;

import java.io.Serializable;

/**
 * Created by alump on 31/03/2017.
 */
public class MapLayoutChildCoords implements Serializable {
    public Double x;
    public Double y;

    public MapLayoutChildCoords() {

    }

    public MapLayoutChildCoords(Double x, Double y) {
        this.x = x;
        this.y = y;
    }
}
