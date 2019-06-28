package org.vaadin.alump.maplayout;

import com.neovisionaries.i18n.CountryCode;
import com.vaadin.server.ThemeResource;

import java.util.*;

/**
 * World map with countries
 */
public class WorldMap extends MapLayout<CountryCode> {

    // These values are here if you want to play around with fixed sizing and calculate correct pixel sizes
    public final static int DEFAULT_VIEWPORT_WIDTH_PX = 2446;
    public final static int DEFAULT_VIEWPORT_HEIGTH_PX = 1383;
    public final static double DEFAULT_ASPECT_RATIO = ((double)DEFAULT_VIEWPORT_WIDTH_PX) / ((double)DEFAULT_VIEWPORT_HEIGTH_PX);

    public final static String SMOOTH_COLOR_TRANSITION_STYLENAME = "smooth-color-transition";

    public static class CountryCodeMapIdProvider implements MapIdProvider<CountryCode> {

        @Override
        public String getMapIdForItem(CountryCode countryCode) {
            return Objects.requireNonNull(countryCode).getAlpha2().toLowerCase();
        }

        @Override
        public Optional<CountryCode> getItemFromMapId(String mapId) {
            if(mapId == null || mapId.length() != 2) {
                return Optional.empty();
            }
            return Optional.ofNullable(CountryCode.getByCode(mapId, false));
        }
    }

    public WorldMap() {
        // Using theme resource with static URL to allow caching on client side (large file)
        super(new ThemeResource("../../addons/maplayout-addon/maps/world.svg"), "world-map",
                new CountryCodeMapIdProvider());
    }
}
