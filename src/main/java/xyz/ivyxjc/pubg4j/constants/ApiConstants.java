package xyz.ivyxjc.pubg4j.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Ivyxjc
 * @since 4/24/2018
 */
@NoArgsConstructor(access = AccessLevel.NONE)
public class ApiConstants {
    public static final String FILTER_PLAYER_NAME =
        "https://api.playbattlegrounds.com/shards/%s/players?filter[playerNames]=%s";
    public static final String FILTER_MATCH_ID =
        "https://api.playbattlegrounds.com/shards/%s/matches/%s";
}
