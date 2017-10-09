package org.vaadin.alump.maplayout;

import com.vaadin.server.ThemeResource;

import java.util.Objects;
import java.util.Optional;

/**
 * Map of Europe with countries
 */
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

    /**
     * Set color of country
     * @param country Country changed
     * @param red Red value of color (0-255)
     * @param green Green value of color (0-255)
     * @param blue Blue value of color (0-255)
     */
    public void setCountryColor(EuropeanCountry country, int red, int green, int blue) {
        setCountryColor(country, getColor(red, green, blue));
    }

    /**
     * Set color of country
     * @param country Country changed
     * @param color Color of country (eg. #00F, blue)
     */
    public void setCountryColor(EuropeanCountry country, String color) {
        setCountryStyle(Objects.requireNonNull(country), "fill", Objects.requireNonNull(color));
    }

    /**
     * Clear dynamic color of country
     * @param country Country changed
     */
    public void clearCountryColor(EuropeanCountry country) {
        removeCountryStyle(Objects.requireNonNull(country), "fill");
    }

    /**
     * Set style value to given country
     * @param country Country changed
     * @param style Style changed (eg. fill, stroke)
     * @param value Value changed (eg. blue)
     */
    public void setCountryStyle(EuropeanCountry country, String style, String value) {
        setElementStyle(getElementID(country), style, value);
    }

    /**
     * Remove style value from country
     * @param country
     * @param style
     */
    public void removeCountryStyle(EuropeanCountry country, String style) {
        removeElementStyle(getElementID(country), style);
    }
}
