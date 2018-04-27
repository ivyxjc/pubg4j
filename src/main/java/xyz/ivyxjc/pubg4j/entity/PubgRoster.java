package xyz.ivyxjc.pubg4j.entity;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.ivyxjc.pubg4j.types.PlatformRegion;

/**
 * @author Ivyxjc
 * @since 4/22/2018
 */
@Data
@EqualsAndHashCode(of = {"rosterId"})
public class PubgRoster {
    private String matchId;
    private String rosterId;
    private PlatformRegion shardId;
    private Integer rank;
    private Integer teamId;
    private Boolean win;
    private List<PubgParticipant> participantList;

    public PubgRoster() {
        participantList = new ArrayList<>();
    }
}
