package xyz.ivyxjc.pubg4j.dao;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import xyz.ivyxjc.pubg4j.entity.PubgMatch;

@Repository
public interface PubgMatchMapper {

    int insertPubgMatchList(List<PubgMatch> matches);

    List<PubgMatch> queryByPlayerId(String playerId);

    @ResultMap("PubgMatchBaseMapper")
    @Select("SELECT * FROM PUBG_PLAYER_MATCH WHERE MATCH_ID=#{matchId}")
    PubgMatch queryByMatchId(String matchId);

    @Delete("DELETE FROM PUBG_PLAYER_MATCH WHERE PLAYER_ID=#{playerId}")
    int deleteByPlayerId(String playerId);
}