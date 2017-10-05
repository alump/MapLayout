package org.vaadin.alump.maplayout;

import com.neovisionaries.i18n.CountryCode;
import com.vaadin.server.ThemeResource;

import java.util.Objects;
import java.util.Optional;

public class EuropeMap extends MapLayout<CountryCode> {

    public final static String SMOOTH_COLOR_TRANSITION_STYLENAME = "smooth-color-transition";

    public EuropeMap() {
        // Using theme resource with static URL to allow caching on client side (large file)
        super(new ThemeResource("../../addons/maplayout-addon/maps/europe.svg"), "europe-map",
                new EuropeMap.CountryCodeMapIdProvider());
    }

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
}
