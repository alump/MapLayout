package org.vaadin.alump.maplayout.client.shared;

import com.vaadin.shared.ui.AbstractLayoutState;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapLayoutState extends AbstractLayoutState {

    public String mapResource = null;

    public Map<String,Set<String>> extraStyleNames = new HashMap<>();

    public boolean addTitles = true;
}