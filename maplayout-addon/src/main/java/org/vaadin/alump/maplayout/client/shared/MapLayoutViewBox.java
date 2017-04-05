package org.vaadin.alump.maplayout.client.shared;

import java.io.Serializable;

/**
 * ViewBox coordinates
 */
public class MapLayoutViewBox implements Serializable {
    public Double minX;
    public Double minY;
    public Double width;
    public Double height;

    public MapLayoutViewBox() {

    }

    public MapLayoutViewBox(Double minX, Double minY, Double width, Double height) {
        this.minX = minX;
        this.minY = minY;
        this.width = width;
        this.height = height;
    }
}
