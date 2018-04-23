package xyz.ivyxjc.pubg4j.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.ivyxjc.pubg4j.types.PlatformRegion;

/**
 * @author Ivyxjc
 * @since 4/22/2018
 */
@Data
@EqualsAndHashCode(of = {"participantId"})
public class ParticipantDetail {
    private String participantId;
    private PlatformRegion shardId;
    private Integer dbnos;
    private Integer assists;
    private Integer boosts;
    private Double damageDealt;
    private String deathType;
    private Integer headshotKills;
    private Integer heals;
    private Integer killPlace;
    private Integer killPoints;
    private Double killPointsDelta;
    private Integer killStreaks;
    private Integer kills;
    private Integer lastKillPoints;
    private Integer lastWinPoints;
    private Double longestKill;
    private Integer mostDamage;
    private String name;
    private String playerId;
    private Integer revives;
    private Double rideDistance;
    private Integer roadKills;
    private Integer teamKills;
    private Integer timeSurvived;
    private Integer vehicleDestroys;
    private Double walkDistance;
    private Integer weaponsAcquired;
    private Integer winPlace;
    private Integer winPoints;
    private Double winPointsDelta;
}
