package org.vaadin.alump.maplayout;

import com.vaadin.server.ThemeResource;

/**
 * Default world map, currently only out-of-box supported map
 */
public class DefaultWorldMapLayout extends MapLayout {

    /**
     * Country codes to be used with this map. Most of countries are still missing, will be filled later.
     */
    public enum CountryCode implements MapIdProvider {
        ANGOLA("ao"),
        ARGENTINA("ar"),
        AUSTRALIA("au"),
        BRAZIL("br"),
        CANADA("ca"),
        CHINA("cn"),
        DENMARK("dk"),
        EGYPT("eg"),
        FINLAND("fi"),
        GERMANY("de"),
        NORWAY("no"),
        POLAND("pl"),
        SWEDEN("se"),
        UNITED_STATES("us");

        private final String code;

        CountryCode(String code) {
            this.code = code;
        }

        public String getMapId() {
            return code;
        }
    }

    public DefaultWorldMapLayout() {
        super(new ThemeResource("../../addons/maplayout-addon/maps/world.svg"));
    }
}
