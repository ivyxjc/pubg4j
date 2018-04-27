package xyz.ivyxjc.pubg4j.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import xyz.ivyxjc.pubg4j.entity.PubgRoster;

@Repository
public interface PubgRosterMapper {
    List<PubgRoster> queryByMatchId(String matchId);

    @ResultMap("PubgRosterBaseMap")
    @Select("SELECT * FROM PUBG_ROSTER WHERE ROSTER_ID=#{rosterId}")
    PubgRoster queryByRosterId(String rosterId);

    int insertPubgRosterList(List<PubgRoster> list);

    @MapKey("rosterId")
    @ResultMap("PubgRosterBaseMap")
    @Select("SELECT * FROM PUBG_ROSTER WHERE MATCH_ID=#{matchId}")
    Map<String, PubgRoster> queryMapByMatchId(String matchId);

    @Delete("DELETE FROM PUBG_ROSTER WHERE MATCH_ID=#{matchId}")
    int delteByMatchId(String matchId);

    int deleteByMatchIdList(List<String> matchIds);
}