package xyz.ivyxjc.pubg4j.web.types;

/**
 * @author Ivyxjc
 * @since 4/22/2018
 */

import lombok.Getter;
import xyz.ivyxjc.pubg4j.web.exception.UnsupportedGameModeException;

/**
 * Enum:
 * [ duo, duo-fpp, solo, solo-fpp, squad, squad-fpp ]
 */
@Getter
public enum GameMode {
    DUO("duo"),
    DUO_FPP("duo-fpp"),
    SOLO("solo"),
    SOLO_FPP("solo-fpp"),
    SQUAD("squad"),
    SQUAD_FPP("squad-fpp");

    private String mode;

    GameMode(String mode) {
        this.mode = mode;
    }

    public static GameMode enumOf(String s) {
        switch (s) {
            case "duo":
                return DUO;
            case "duo-fpp":
                return DUO_FPP;
            case "solo":
                return SOLO;
            case "solo-fpp":
                return SOLO_FPP;
            case "squad":
                return SQUAD;
            case "squad-fpp":
                return SQUAD_FPP;
            default:
                throw new UnsupportedGameModeException("Not support this game mode: " + s);
        }
    }
}
