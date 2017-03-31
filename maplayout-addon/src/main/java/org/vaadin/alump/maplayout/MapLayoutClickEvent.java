package org.vaadin.alump.maplayout;

import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Click event from MapLayout
 */
public class MapLayoutClickEvent extends Button.ClickEvent {

    private final Component clickedComponent;
    private final Component childComponent;
    private final List<String> mapItemIds;

    public MapLayoutClickEvent(Component source, MouseEventDetails mouseEventDetails, List<String> mapItemId) {
        super(source, mouseEventDetails);
        this.clickedComponent = null;
        this.childComponent = null;
        this.mapItemIds = Collections.unmodifiableList(new ArrayList<>(mapItemId));
    }

    public MapLayoutClickEvent(Component source, MouseEventDetails mouseEventDetails, Component clickedComponent,
                               Component childComponent) {
        super(source, mouseEventDetails);
        this.clickedComponent = clickedComponent;
        this.childComponent = childComponent;
        this.mapItemIds = Collections.EMPTY_LIST;
    }

    /*
    public Optional<Double> getLongitude() {
        return Optional.empty();
    }

    public Optional<Double> getLatitude() {
        return Optional.empty();
    }
    */

    /**
     * Get clicked element IDs (outwards ordering, so inner most will be first in the list)
     * @return
     */
    public List<String> getMapItemIds() {
        return mapItemIds;
    }

    public Optional<Component> getChildComponent() {
        return Optional.ofNullable(childComponent);
    }
}
