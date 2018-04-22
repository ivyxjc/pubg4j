package xyz.ivyxjc.pubg4j.entity;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import xyz.ivyxjc.pubg4j.types.GameMode;
import xyz.ivyxjc.pubg4j.types.MapName;
import xyz.ivyxjc.pubg4j.types.PlatformRegion;

/**
 * @author Ivyxjc
 * @since 4/22/2018
 */
@Data
public class PubgMatchDetail {

    private String matchId;
    /**
     * Time this match object was stored in the API
     */
    private LocalDateTime createdAt;

    /**
     * Length of the match
     */
    private int duration;

    /**
     * Game mode played
     */
    private GameMode gameMode;

    /**
     * Map name
     */
    private MapName mapName;

    /**
     * Platform-region shard
     */
    private PlatformRegion shardId;
    /**
     * Identifies the studio and game
     */
    private String titleId;

    private List<Roster> rosterList;

    private List<ParticipantDetail> participantDetailList;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String patchVersion;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String stats;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String tags;
}
