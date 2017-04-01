package org.vaadin.alump.maplayout;

import com.neovisionaries.i18n.CountryCode;
import com.vaadin.server.ThemeResource;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Default world map, currently only out-of-box supported map
 */
public class WorldMap extends MapLayout<CountryCode> {

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
        super(new ThemeResource("../../addons/maplayout-addon/maps/world.svg"),
                new CountryCodeMapIdProvider());
    }
}
