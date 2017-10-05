package org.vaadin.alump.maplayout;

import com.vaadin.server.ThemeResource;

import java.util.Objects;
import java.util.Optional;

public class EuropeMap extends MapLayout<EuropeanCountry> {

    public final static String SMOOTH_COLOR_TRANSITION_STYLENAME = "smooth-color-transition";

    public EuropeMap() {
        // Using theme resource with static URL to allow caching on client side (large file)
        super(new ThemeResource("../../addons/maplayout-addon/maps/europe.svg"), "europe-map",
                new EuropeMap.CountryCodeMapIdProvider());
    }

    public static class CountryCodeMapIdProvider implements MapIdProvider<EuropeanCountry> {

        @Override
        public String getMapIdForItem(EuropeanCountry countryCode) {
            return Objects.requireNonNull(countryCode).getISO3166().toLowerCase();
        }

        @Override
        public Optional<EuropeanCountry> getItemFromMapId(String mapId) {
            if(mapId == null || mapId.length() != 2) {
                return Optional.empty();
            }
            return Optional.ofNullable(EuropeanCountry.findForISO3166(mapId));
        }
    }
}
