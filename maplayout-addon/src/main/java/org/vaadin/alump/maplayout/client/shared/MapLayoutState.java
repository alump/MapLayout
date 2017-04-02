package org.vaadin.alump.maplayout.client.shared;

import com.vaadin.shared.Connector;
import com.vaadin.shared.ui.AbstractLayoutState;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("GwtInconsistentSerializableClass")
public class MapLayoutState extends AbstractLayoutState {

    public String mapResource = null;

    public Map<String,Set<String>> extraStyleNames = new HashMap<>();

    public boolean addTitles = true;

    public Map<Connector, MapLayoutChildCoords> childCoordinates = new HashMap<>();

    public MapLayoutViewBox viewBox = null;
}