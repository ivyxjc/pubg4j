package xyz.ivyxjc.pubg4j.entity;

import java.util.List;
import lombok.Data;
import xyz.ivyxjc.pubg4j.types.PlatformRegion;

/**
 * @author Ivyxjc
 * @since 4/22/2018
 */
@Data
public class Roster {
    private String rosterId;
    private PlatformRegion shardId;
    private Integer rank;
    private Integer teamId;
    private Boolean win;
    private List<Participant> participantList;
}
