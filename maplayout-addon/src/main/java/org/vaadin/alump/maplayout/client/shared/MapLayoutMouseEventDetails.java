package org.vaadin.alump.maplayout.client.shared;

import com.vaadin.shared.MouseEventDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Details of MapLayout mouse event
 */
public class MapLayoutMouseEventDetails {

    private MouseEventDetails mouseEventDetails;
    private List<String> elementIds;
    private Double viewBoxX;
    private Double viewBoxY;

    public void setMouseEventDetails(MouseEventDetails details) {
        this.mouseEventDetails = details;
    }

    public MouseEventDetails getMouseEventDetails() {
        return mouseEventDetails;
    }

    public void setElementIds(List<String> elementIds) {
        this.elementIds = new ArrayList<>(elementIds);
    }

    public List<String> getElementIds() {
        return Collections.unmodifiableList(elementIds);
    }

    public Double getViewBoxX() {
        return viewBoxX;
    }

    public Double getViewBoxY() {
        return viewBoxY;
    }

    public void setViewBoxX(Double viewBoxX) {
        this.viewBoxX = viewBoxX;
    }

    public void setViewBoxY(Double viewBoxY) {
        this.viewBoxY = viewBoxY;
    }
}
