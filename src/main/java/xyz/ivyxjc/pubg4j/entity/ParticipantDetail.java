package xyz.ivyxjc.pubg4j.entity;

import lombok.Data;
import xyz.ivyxjc.pubg4j.types.PlatformRegion;

/**
 * @author Ivyxjc
 * @since 4/22/2018
 */
@Data
public class ParticipantDetail {
    private String participantId;
    private PlatformRegion shardId;
    private Integer dbnos;
    private Integer assists;
    private Integer boosts;
    private Double damagedealt;
    private String deathtype;
    private Integer headshotkills;
    private Integer heals;
    private Integer killplace;
    private Integer killpoIntegers;
    private Double killpoIntegersdelta;
    private Integer killstreaks;
    private Integer kills;
    private Integer lastkillpoIntegers;
    private Integer lastwinpoIntegers;
    private Integer longestkill;
    private Integer mostdamage;
    private String name;
    private String playerid;
    private Integer revives;
    private Integer ridedistance;
    private Integer roadkills;
    private Integer teamkills;
    private Integer timesurvived;
    private Integer vehicledestroys;
    private Double walkdistance;
    private Integer weaponsacquired;
    private Integer winplace;
    private Integer winpoIntegers;
    private Double winpoIntegersdelta;
}
