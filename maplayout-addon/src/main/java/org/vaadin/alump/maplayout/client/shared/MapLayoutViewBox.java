package org.vaadin.alump.maplayout.client.shared;

/**
 * Created by alump on 02/04/2017.
 */
public class MapLayoutViewBox {
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
