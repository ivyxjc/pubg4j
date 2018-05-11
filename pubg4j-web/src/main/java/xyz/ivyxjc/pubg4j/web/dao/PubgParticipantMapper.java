package xyz.ivyxjc.pubg4j.web.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import xyz.ivyxjc.pubg4j.web.entity.PubgParticipant;

@Repository
public interface PubgParticipantMapper {

    PubgParticipant queryByParticipantId(String participantId);

    List<PubgParticipant> queryByMatchId(String matchId);

    List<PubgParticipant> queryByRosterId(String rosterId);

    int insertPubgParticipantList(List<PubgParticipant> list);

    @ResultMap("PubgParticipantBaseMap")
    @Select("SELECT * FROM PUBG_PARTICIPANT WHERE MATCH_ID=#{matchId}")
    @MapKey("participantId")
    Map<String, PubgParticipant> queryMapByMatchId(String matchId);

    @Delete("DELETE FROM PUBG_PARTICIPANT WHERE PARTICIPANT_ID=#{participantId}")
    int deleteByParticipantId(String participantId);

    @Delete("DELETE FROM PUBG_PARTICIPANT WHERE ROSTER_ID=#{rosterId}")
    int deleteByRosterId(String rosterId);

    @Delete("DELETE FROM PUBG_PARTICIPANT WHERE MATCH_ID=#{matchId}")
    int deleteByMatchId(String matchId);

    int deleteByMatchIdList(List<String> matchIds);
}