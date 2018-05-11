package xyz.ivyxjc.pubg4j.web.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import xyz.ivyxjc.pubg4j.web.entity.PubgPlayer;

/**
 * @author Ivyxjc
 * @since 4/26/2018
 */
@Repository
public interface PubgPlayerMapper {
    PubgPlayer queryByPlayerId(String id);

    @ResultMap(value = {"PubgPlayerBaseMap"})
    @Select("SELECT * FROM PUBG_PLAYER WHERE NAME = #{name} ")
    PubgPlayer queryByPlayerName(String name);

    void insertPubgPlayer(PubgPlayer pubgPlayer);

    @Delete("DELETE FROM PUBG_PLAYER WHERE PLAYER_ID=#{playerId}")
    void deleteByPubgPlayerId(String playerId);

    @Delete("DELETE FROM PUBG_PLAYER WHERE NAME=#{playerName}")
    int delteByPubgName(String playerName);
}
