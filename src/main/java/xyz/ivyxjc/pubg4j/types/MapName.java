package xyz.ivyxjc.pubg4j.types;

/**
 * @author Ivyxjc
 * @since 4/22/2018
 */

import lombok.Getter;
import xyz.ivyxjc.pubg4j.exception.UnsupportedMapException;

/**
 * string
 *
 * Map name
 * Enum:
 * [ Desert_Main, Erangel_Main ]
 */
@Getter
public enum MapName {
    DESERT_MAIN("Desert_Main"),
    ERANGEL_MAIN("Erangel_Main");

    private String mapName;

    MapName(String mapName) {
        this.mapName = mapName;
    }

    public static MapName enumOf(String s) throws UnsupportedMapException {
        switch (s) {
            case "Desert_Main":
                return DESERT_MAIN;
            case "Erangel_Main":
                return ERANGEL_MAIN;
            default:
                throw new UnsupportedMapException("Not support this map");
        }
    }
}
