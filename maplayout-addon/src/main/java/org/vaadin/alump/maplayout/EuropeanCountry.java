package org.vaadin.alump.maplayout;

import com.neovisionaries.i18n.CountryCode;

import java.util.Arrays;
import java.util.stream.Stream;

public enum EuropeanCountry {

    ALBANIA(CountryCode.AL, false, "\uD83C\uDDE6\uD83C\uDDF1"),
    ANDORRA(CountryCode.AD, false, "\uD83C\uDDE6\uD83C\uDDE9"),
    ARMENIA(CountryCode.AM, false, "\uD83C\uDDE6\uD83C\uDDF2"),
    AUSTRIA(CountryCode.AT, true, "\uD83C\uDDE6\uD83C\uDDF9"),
    AZERBAIJAN(CountryCode.AZ, false, "\uD83C\uDDE6\uD83C\uDDFF"),
    BELARUS(CountryCode.BY, false, "\uD83C\uDDE7\uD83C\uDDFE"),
    BELGIUM(CountryCode.BE, true, "\uD83C\uDDE7\uD83C\uDDEA"),
    BOSNIA_AND_HERZEGOVINA(CountryCode.BA, false, "\uD83C\uDDE7\uD83C\uDDE6"),
    BULGARIA(CountryCode.BG, true, "\uD83C\uDDE7\uD83C\uDDEC"),
    CROATIA(CountryCode.HR, false, "\uD83C\uDDED\uD83C\uDDF7"),
    CYPRUS(CountryCode.CY, true, "\uD83C\uDDE8\uD83C\uDDFE"),
    CZECHIA(CountryCode.CZ, true, "\uD83C\uDDE8\uD83C\uDDFF"),
    DENMARK(CountryCode.DK, true, "\uD83C\uDDE9\uD83C\uDDF0"),
    ESTONIA(CountryCode.EE, true, "\uD83C\uDDEA\uD83C\uDDEA"),
    FINLAND(CountryCode.FI, true, "\uD83C\uDDEB\uD83C\uDDEE"),
    FRANCE(CountryCode.FR, true, "\uD83C\uDDEB\uD83C\uDDF7"),
    GEORGIA(CountryCode.GE, false, "\uD83C\uDDEC\uD83C\uDDEA"),
    GERMANY(CountryCode.DE, true, "\uD83C\uDDE9\uD83C\uDDEA"),
    GREECE(CountryCode.GR, true, "\uD83C\uDDEC\uD83C\uDDF7"),
    HUNGARY(CountryCode.HU, true, "\uD83C\uDDED\uD83C\uDDFA"),
    ICELAND(CountryCode.IS, false, "\uD83C\uDDEE\uD83C\uDDF8"),
    IRELAND(CountryCode.IE, true, "\uD83C\uDDEE\uD83C\uDDEA"),
    ITALY(CountryCode.IT, true, "\uD83C\uDDEE\uD83C\uDDF9"),
    KAZAKHSTAN(CountryCode.KZ, false, "\uD83C\uDDF0\uD83C\uDDFF"),
    KOSOVO(CountryCode.XK, false, "\uD83C\uDDFD\uD83C\uDDF0"),
    LATVIA(CountryCode.LV, true, "\uD83C\uDDF1\uD83C\uDDFB"),
    LIECHTENSTEIN(CountryCode.LI, true, "\uD83C\uDDF1\uD83C\uDDEE"),
    LITHUANIA(CountryCode.LT, true, "\uD83C\uDDF1\uD83C\uDDF9"),
    LUXEMBOURG(CountryCode.LU, true, "\uD83C\uDDF1\uD83C\uDDFA"),
    NORTH_MACEDONIA(CountryCode.MK, false, "\uD83C\uDDF2\uD83C\uDDF0"),
    MALTA(CountryCode.MT, true, "\uD83C\uDDF2\uD83C\uDDF9"),
    MOLDOVA(CountryCode.MD, false, "\uD83C\uDDF2\uD83C\uDDE9"),
    MONACO(CountryCode.MC, false, "\uD83C\uDDF2\uD83C\uDDE8"),
    MONTENEGRO(CountryCode.ME, false, "\uD83C\uDDF2\uD83C\uDDEA"),
    NETHERLANDS(CountryCode.NL, true, "\uD83C\uDDF3\uD83C\uDDF1"),
    NORWAY(CountryCode.NO, false, "\uD83C\uDDF3\uD83C\uDDF4"),
    POLAND(CountryCode.PL, true, "\uD83C\uDDF5\uD83C\uDDF1"),
    PORTUGAL(CountryCode.PT, true, "\uD83C\uDDF5\uD83C\uDDF9"),
    ROMANIA(CountryCode.RO, true, "\uD83C\uDDF7\uD83C\uDDF4"),
    RUSSIA(CountryCode.RU, false, "\uD83C\uDDF7\uD83C\uDDFA"),
    SAN_MARINO(CountryCode.SM, false, "\uD83C\uDDF8\uD83C\uDDF2"),
    SERBIA(CountryCode.RS, false, "\uD83C\uDDF7\uD83C\uDDF8"),
    SLOVAKIA(CountryCode.SK, true, "\uD83C\uDDF8\uD83C\uDDF0"),
    SLOVENIA(CountryCode.SI, true, "\uD83C\uDDF8\uD83C\uDDEE"),
    SPAIN(CountryCode.ES, true, "\uD83C\uDDEA\uD83C\uDDF8"),
    SWEDEN(CountryCode.SE, true, "\uD83C\uDDF8\uD83C\uDDEA"),
    SWITZERLAND(CountryCode.CH, false, "\uD83C\uDDE8\uD83C\uDDED"),
    TURKEY(CountryCode.TR, false, "\uD83C\uDDF9\uD83C\uDDF7"),
    UKRAINE(CountryCode.UA, false, "\uD83C\uDDFA\uD83C\uDDE6"),
    UK(CountryCode.UK, true, "\uD83C\uDDEC\uD83C\uDDE7"), // brexit fix required soon... June 2019 update: nope, still waiting...
    VATICAN_CITY(CountryCode.VA, false, "\uD83C\uDDFB\uD83C\uDDE6");

    private final CountryCode cc;
    private final boolean eu;
    private final String flag;

    EuropeanCountry(CountryCode cc, boolean eu) {
        this(cc, eu, null);
    }

    EuropeanCountry(CountryCode cc, boolean eu, String flag) {
        this.cc = cc;
        this.eu = eu;
        this.flag = flag;
    }

    public boolean isLeavingEU() {
        return this == UK;
    }

    public CountryCode getCountryCode() {
        return cc;
    }

    public boolean isEU() {
        return eu;
    }

    public String getISO3166() {
        return cc.name();
    }

    public String getFlag() {
        return flag;
    }

    public static EuropeanCountry findForISO3166(String iso3166) {
        String lowercase = iso3166.toLowerCase();
        return Arrays.asList(EuropeanCountry.values()).stream()
                .filter(ec -> ec.getISO3166().toLowerCase().equals(lowercase))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid ISO 3166 code: " + iso3166));
    }

    public static Stream<EuropeanCountry> stream() {
        return Arrays.stream(EuropeanCountry.values());
    }

}
