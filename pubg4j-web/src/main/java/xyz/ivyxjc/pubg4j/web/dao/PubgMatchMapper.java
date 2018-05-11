package xyz.ivyxjc.pubg4j.web.dao;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import xyz.ivyxjc.pubg4j.web.entity.PubgMatch;

@Repository
public interface PubgMatchMapper {

    int insertPubgMatchList(List<PubgMatch> matches);

    List<PubgMatch> queryByPlayerId(String playerId);

    @ResultMap("PubgMatchBaseMapper")
    @Select("SELECT * FROM PUBG_PLAYER_MATCH WHERE MATCH_ID=#{matchId}")
    PubgMatch queryByMatchId(String matchId);

    @Delete("DELETE FROM PUBG_PLAYER_MATCH WHERE PLAYER_ID=#{playerId}")
    int deleteByPlayerId(String playerId);

    @ResultMap("PubgMatchBaseMapper")
    @Select(
        "SELECT * FROM PUBG_PLAYER_MATCH WHERE SHARD_ID=#{shardId} AND PLAYER_ID=#{playerId} ORDER BY DB_UPDATED_AT DESC LIMIT 0,1")
    PubgMatch queryNewestMatch(@Param("shardId") String shardId,
        @Param("playerId") String playerId);
}