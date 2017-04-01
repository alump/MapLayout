package org.vaadin.alump.maplayout;

import java.util.Optional;

/**
 * Created by alump on 30/03/2017.
 */
public interface MapIdProvider<T> {
    String getMapIdForItem(T item);

    Optional<T> getItemFromMapId(String mapId);
}
