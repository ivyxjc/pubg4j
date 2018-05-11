package xyz.ivyxjc.pubg4j.web.dao;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;
import xyz.ivyxjc.pubg4j.web.entity.PubgMatchDetail;

/**
 * @author Ivyxjc
 * @since 4/26/2018
 */
@Repository
public interface PubgMatchDetailMapper {
    int insertPubgMatch(PubgMatchDetail pubgMatchDetail);

    PubgMatchDetail queryByMatchId(String matchId);

    @Delete("DELETE FROM PUBG_MATCH_DETAIL WHERE MATCH_ID=#{matchId}")
    int deleteByMatchId(String matchId);

    int deleteByMatchIdList(List<String> matchIds);
}
