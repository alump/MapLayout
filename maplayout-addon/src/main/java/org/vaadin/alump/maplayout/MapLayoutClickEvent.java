package org.vaadin.alump.maplayout;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import org.vaadin.alump.maplayout.client.shared.MapLayoutMouseEventDetails;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Click event from MapLayout
 */
public class MapLayoutClickEvent<T> extends Button.ClickEvent {

    private final Component clickedComponent;
    private final Component childComponent;
    private final MapLayoutMouseEventDetails details;

    public MapLayoutClickEvent(MapLayout<T> source, MapLayoutMouseEventDetails details) {
        super(source, details.getMouseEventDetails());
        this.details = details;
        this.clickedComponent = null;
        this.childComponent = null;
    }

    public MapLayout<T> getMapLayout() {
        return (MapLayout<T>)getSource();
    }

    /**
     * Get clicked element IDs (outwards ordering, so inner most will be first in the list)
     * @return
     */
    public List<String> getMapElementIds() {
        return Collections.unmodifiableList(details.getElementIds());
    }

    public Optional<Component> getChildComponent() {
        return Optional.ofNullable(childComponent);
    }

    public Optional<Double> getViewBoxX() {
        return Optional.ofNullable(details.getViewBoxX());
    }

    public Optional<Double> getViewBoxY() {
        return Optional.ofNullable(details.getViewBoxY());
    }

    public List<T> getMapItems() {
        return details.getElementIds().stream().map(id -> getMapLayout().getItemForMapId(id).orElse(null))
                .filter(i -> i != null).collect(Collectors.toList());
    }

    public Optional<T> getMapItem() {
        return details.getElementIds().stream().map(id -> getMapLayout().getItemForMapId(id).orElse(null))
                .filter(i -> i != null).findFirst();
    }
}
