package xyz.ivyxjc.pubg4j.types;

import xyz.ivyxjc.pubg4j.exception.UnsupportedPlatformRegionException;

/**
 * @author Ivyxjc
 * @since 4/22/2018
 */
public enum PlatformRegion {
    XBOX_AS("xbox-as"),
    XBOX_EU("xbox-eu"),
    XBOX_NA("xbox-na"),
    XBOX_OC("xbox-oc"),
    PC_KRJP("pc-krjp"),
    PC_JP("pc-jp"),
    PC_NA("pc-na"),
    PC_EU("pc-eu"),
    PC_OC("pc-oc"),
    PC_KAKAO("pc-kakao"),
    PC_SEA("pc-sea"),
    PC_SA("pc-sa"),
    PC_AS("pc-as");

    private final String pltRegion;

    PlatformRegion(String pltRegion) {
        this.pltRegion = pltRegion;
    }

    public String getPltRegion() {
        return pltRegion;
    }

    public static PlatformRegion enumOf(String s) throws UnsupportedPlatformRegionException {
        switch (s) {
            case "xbox-as":
                return XBOX_AS;
            case "xbox-eu":
                return XBOX_EU;
            case "xbox-na":
                return XBOX_NA;
            case "xbox-oc":
                return XBOX_OC;
            case "pc-krjp":
                return PC_KRJP;
            case "pc-jp":
                return PC_JP;
            case "pc-na":
                return PC_NA;
            case "pc-KAKAO":
                return PC_KAKAO;
            case "pc-sea":
                return PC_SEA;
            case "pc-sa":
                return PC_SA;
            case "pc-as":
                return PC_AS;
            default:
                throw new UnsupportedPlatformRegionException(
                    "Not support this platform region:" + s);
        }
    }

}
