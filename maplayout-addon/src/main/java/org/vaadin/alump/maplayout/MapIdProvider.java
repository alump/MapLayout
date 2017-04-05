package org.vaadin.alump.maplayout;

import java.io.Serializable;
import java.util.Optional;

/**
 * Map Id provider can be used to convert item class instances to string IDs of elements on SVG file
 */
public interface MapIdProvider<T> extends Serializable {

    /**
     * Get SVG element ID for item
     * @param item Item mapped
     * @return Element ID on SVG file
     */
    String getMapIdForItem(T item);

    /**
     * Resolve item from SVG element ID
     * @param mapId Element ID on SVG file
     * @return Item matching with element ID, or empty if no match
     */
    Optional<T> getItemFromMapId(String mapId);
}
