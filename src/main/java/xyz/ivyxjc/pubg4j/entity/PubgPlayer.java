package xyz.ivyxjc.pubg4j.entity;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import xyz.ivyxjc.pubg4j.types.PlatformRegion;

/**
 * @author Ivyxjc
 * @since 4/22/2018
 */

@Data
public class PubgPlayer {
    /**
     * Player ID
     */
    private String playerId;
    private LocalDateTime createdAt;
    private String name;

    /**
     * Platform-region shard
     */
    private PlatformRegion shardId;

    /**
     * Identifies the studio and game
     */
    private String titleId;

    private LocalDateTime updatedAt;

    private List<PubgMatch> matches;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String patchVersion;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String stats;
}
