package org.vaadin.alump.maplayout;

import com.neovisionaries.i18n.CountryCode;
import com.vaadin.server.ThemeResource;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Default world map, currently only out-of-box supported map
 */
public class DefaultWorldMapLayout extends MapLayout {

    public DefaultWorldMapLayout() {
        super(new ThemeResource("../../addons/maplayout-addon/maps/world.svg"));
    }

    public static Collection<String> resolveCountryCodeIds(CountryCode ... countryCodes) {
        return resolveCountryCodeIds(Arrays.stream(countryCodes).collect(Collectors.toList()));
    }

    public static Collection<String> resolveCountryCodeIds(Collection<CountryCode> countryCodes) {
        return countryCodes.stream().map(cc -> cc.getAlpha2().toLowerCase()).collect(Collectors.toSet());
    }

    public void setCountryStyleNames(String styleName, CountryCode ... countryCodes) {
        setStyleNamesToIds(styleName, resolveCountryCodeIds(countryCodes));
    }

    public void setCountryStyleName(String styleName, CountryCode countryCode, boolean enabled) {
        setStyleNameOfId(styleName, Objects.requireNonNull(countryCode).getAlpha2().toLowerCase(), enabled);
    }

    public Optional<CountryCode> resolveCountryCodeFromElementIds(List<String> elementIds) {
        return elementIds.stream()
                .filter(id -> id.length() == 2)
                .map(id -> CountryCode.getByCode(id, false))
                .filter(code -> code != null)
                .findFirst();
    }
}
