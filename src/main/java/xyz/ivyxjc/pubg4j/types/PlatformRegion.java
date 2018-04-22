package xyz.ivyxjc.pubg4j.types;

import lombok.Getter;

/**
 * @author Ivyxjc
 * @since 4/22/2018
 */
public enum PlatformRegion {
    XBOX_AS("xbox-as"),
    XBOX_EU("xbox-eu"),
    XBOX_NA("xbox-na"),
    XOBX_OC("xbox-oc"),
    PC_KRJP("pc-krjp"),
    PC_JP("pc-jp"),
    PC_NA("pc-na"),
    PC_EU("pc-eu"),
    PC_OC("pc-oc"),
    PC_KAKAO("pc-kakao"),
    PC_SEA("pc-sea"),
    PC_SA("pc-sa"),
    PC_AS("pc-as");

    @Getter
    private final String pltRegion;

    PlatformRegion(String pltRegion) {
        this.pltRegion = pltRegion;
    }

}
