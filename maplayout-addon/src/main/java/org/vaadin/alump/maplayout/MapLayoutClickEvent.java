package org.vaadin.alump.maplayout;

import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

import java.util.Optional;

/**
 * Click event from MapLayout
 */
public class MapLayoutClickEvent extends Button.ClickEvent {

    private final Component clickedComponent;
    private final Component childComponent;
    private final String mapItemId;

    public MapLayoutClickEvent(Component source, MouseEventDetails mouseEventDetails, String mapItemId) {
        super(source, mouseEventDetails);
        this.clickedComponent = null;
        this.childComponent = null;
        this.mapItemId = mapItemId;
    }

    public MapLayoutClickEvent(Component source, MouseEventDetails mouseEventDetails, Component clickedComponent,
                               Component childComponent) {
        super(source, mouseEventDetails);
        this.clickedComponent = clickedComponent;
        this.childComponent = childComponent;
        this.mapItemId = null;
    }

    /*
    public Optional<Double> getLongitude() {
        return Optional.empty();
    }

    public Optional<Double> getLatitude() {
        return Optional.empty();
    }
    */

    public Optional<String> getMapItemId() {
        return Optional.ofNullable(mapItemId);
    }

    public Optional<Component> getChildComponent() {
        return Optional.ofNullable(childComponent);
    }
}
