package org.vaadin.alump.maplayout;

import com.vaadin.server.ThemeResource;

import java.util.*;

/**
 * United States map with states (and Puerto Rico)
 */
public class USStatesMap extends MapLayout<USState> {

    public final static String HIDE_TEXTS_STYLESNAME = "hide-texts";
    public final static String HIDE_PUERTO_RICO_STYLESNAME = "hide-pr";
    public final static String SMOOTH_COLOR_TRANSITION_STYLENAME = "smooth-color-transition";

    private final static Set<USState> STATES_WITH_BOXES = new HashSet<>(Arrays.asList(USState.VERMONT,
            USState.NEW_HAMPSHIRE, USState.MASSACHUSETTS, USState.RHODE_ISLAND, USState.CONNECTICUT, USState.NEW_JERSEY,
            USState.DELAWARE, USState.MARYLAND, USState.DISTRICT_OF_COLUMBIA));

    public static class USStateIdProvider implements MapIdProvider<USState> {

        @Override
        public String getMapIdForItem(USState state) {
            return Objects.requireNonNull(state).getAbbr();
        }

        @Override
        public Optional<USState> getItemFromMapId(String mapId) {
            if(mapId == null) {
                return Optional.empty();
            }
            if(mapId.endsWith("box")) {
                mapId = mapId.substring(0, mapId.length() - 3);
            }
            return Optional.ofNullable(USState.parse(mapId));
        }
    }

    public USStatesMap() {
        // Using theme resource with static URL to allow caching on client side (large file)
        super(new ThemeResource("../../addons/maplayout-addon/maps/us-states.svg"), "us-states-map",
                new USStatesMap.USStateIdProvider());
    }

    @Override
    public void addStyleNameToItem(String styleName, USState state) {
        super.addStyleNameToItem(styleName, state);
        if(STATES_WITH_BOXES.contains(state)) {
            super.addStyleNameToId(styleName, state.getAbbr() + "box");
        }
    }

    @Override
    public void removeStyleNameFromItem(String styleName, USState state) {
        super.removeStyleNameFromItem(styleName, state);
        if(STATES_WITH_BOXES.contains(state)) {
            super.removeStyleNameFromId(styleName, state.getAbbr() + "box");
        }
    }

    /**
     * As some states have boxes, this will return all element IDs mapping to given state
     * @param state State searched
     * @return IDs of elements of state
     */
    protected Set<String> getStateElementIDs(USState state) {
        Set<String> ids = new HashSet<>();
        String abbr = Objects.requireNonNull(state).getAbbr();
        ids.add(abbr);
        if(STATES_WITH_BOXES.contains(state)) {
            ids.add(abbr + "box");
        }
        return ids;
    }

    /**
     * Set color of state
     * @param state State changed
     * @param red Red value of color (0-255)
     * @param green Green value of color (0-255)
     * @param blue Blue value of color (0-255)
     */
    public void setStateColor(USState state, int red, int green, int blue) {
        setStateColor(state, getColor(red, green, blue));
    }

    /**
     * Set color of state
     * @param state State changed
     * @param color Color of state (eg. blue, #00F)
     */
    public void setStateColor(USState state, String color) {
        getStateElementIDs(Objects.requireNonNull(state)).forEach(id -> {
            setElementStyle(id, "fill", Objects.requireNonNull(color));
        });
    }

    /**
     * Clear dynamic color of state
     * @param state State changed
     */
    public void clearStateColor(USState state) {
        getStateElementIDs(Objects.requireNonNull(state)).forEach(id -> {
            removeElementStyle(id, "fill");
        });
    }
}
