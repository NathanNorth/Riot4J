package io.github.nathannorth.riot4j.enums;

/**
 * Val regions represent valid regions to execute api calls against
 */
public enum ValRegion {
    NORTH_AMERICA("na"),
    BRAZIL("br"),
    EUROPE("eu"),
    LATIN_AMERICA("latam"),
    ASIA_PACIFIC("ap"),
    KOREA("kr"),
    E_SPORTS("esports"); //note: NOT VALID for ranked endpoint

    private final String value;
    ValRegion(String value) {
        this.value = value;
    }
    public String toString() {
        return value;
    }
}
