package org.vaadin.alump.maplayout;

import com.neovisionaries.i18n.CountryCode;

import java.util.Arrays;

public enum EuropeanCountry {

    ALBANIA(CountryCode.AL, false),
    ANDORRA(CountryCode.AD, false),
    ARMENIA(CountryCode.AM, false),
    AUSTRIA(CountryCode.AT, true),
    AZERBAIJAN(CountryCode.AZ, false),
    BELARUS(CountryCode.BY, false),
    BELGIUM(CountryCode.BE, true),
    BOSNIA_AND_HERZEGOVINA(CountryCode.BA, false),
    BULGARIA(CountryCode.BG, true),
    CROATIA(CountryCode.HR, false),
    CYPRUS(CountryCode.CY, true),
    CZECH_REPUBLIC(CountryCode.CZ, true),
    DENMARK(CountryCode.DK, true),
    FINLAND(CountryCode.FI, true),
    FRANCE(CountryCode.FR, true),
    GEORGIA(CountryCode.GE, false),
    GERMANY(CountryCode.DE, true),
    GREECE(CountryCode.GR, true),
    HUNGARY(CountryCode.HU, true),
    ICELAND(CountryCode.IS, false),
    IRELAND(CountryCode.IE, true),
    ITALY(CountryCode.IT, true),
    KAZAKHSTAN(CountryCode.KZ, false),
    KOSOVO(CountryCode.XK, false),
    LATVIA(CountryCode.LV, true),
    LIECHTENSTEIN(CountryCode.LI, true),
    LITHUANIA(CountryCode.LT, true),
    LUXEMBOURG(CountryCode.LU, true),
    FYROM(CountryCode.MK, false),
    MALTA(CountryCode.MT, true),
    MOLDOVA(CountryCode.MD, false),
    MONACO(CountryCode.MC, false),
    MONTENEGRO(CountryCode.ME, false),
    NETHERLANDS(CountryCode.NL, true),
    NORWAY(CountryCode.NO, false),
    POLAND(CountryCode.PL, true),
    PORTUGAL(CountryCode.PT, true),
    ROMANIA(CountryCode.RO, true),
    RUSSIA(CountryCode.RU, false),
    SAN_MARINO(CountryCode.SM, false),
    SERBIA(CountryCode.RS, false),
    SLOVAKIA(CountryCode.SK, true),
    SLOVENIA(CountryCode.SI, true),
    SPAIN(CountryCode.ES, true),
    SWEDEN(CountryCode.SE, true),
    SWITZERLAND(CountryCode.CH, false),
    TURKEY(CountryCode.TR, false),
    UKRAINE(CountryCode.UA, false),
    UK(CountryCode.UK, true), // brexit fix required soon
    VATICAN_CITY(CountryCode.VA, false);

    private final CountryCode cc;
    private final boolean eu;

    EuropeanCountry(CountryCode cc, boolean eu) {
        this.cc = cc;
        this.eu = eu;
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

    public static EuropeanCountry findForISO3166(String iso3166) {
        String lowercase = iso3166.toLowerCase();
        return Arrays.asList(EuropeanCountry.values()).stream()
                .filter(ec -> ec.getISO3166().toLowerCase().equals(lowercase)).findFirst().get();
    }

}
