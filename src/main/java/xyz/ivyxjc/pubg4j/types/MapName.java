package xyz.ivyxjc.pubg4j.types;

/**
 * @author Ivyxjc
 * @since 4/22/2018
 */

import lombok.Getter;

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
}
