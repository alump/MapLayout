package org.vaadin.alump.maplayout.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapLayoutState extends com.vaadin.shared.AbstractComponentState {

    public String mapResource = null;

    public Map<String,Set<String>> extraStyleNames = new HashMap<>();

    public boolean addTitles = true;
}