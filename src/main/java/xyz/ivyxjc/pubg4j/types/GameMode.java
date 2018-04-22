package xyz.ivyxjc.pubg4j.types;

/**
 * @author Ivyxjc
 * @since 4/22/2018
 */

import lombok.Getter;

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
}
