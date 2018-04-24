package xyz.ivyxjc.pubg4j;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.ivyxjc.pubg4j.constants.JsonConstants;
import xyz.ivyxjc.pubg4j.entity.ParticipantDetail;
import xyz.ivyxjc.pubg4j.entity.PubgMatch;
import xyz.ivyxjc.pubg4j.entity.PubgMatchDetail;
import xyz.ivyxjc.pubg4j.entity.PubgPlayer;
import xyz.ivyxjc.pubg4j.entity.Roster;
import xyz.ivyxjc.pubg4j.exception.UnsupportedPubgElementException;
import xyz.ivyxjc.pubg4j.types.GameMode;
import xyz.ivyxjc.pubg4j.types.PlatformRegion;

/**
 * @author Ivyxjc
 * @since 4/22/2018
 */
@Slf4j
@Service
public class JsonBuilder {
    // DateTimeFormatter is immutable and thread-safe.
    private static final DateTimeFormatter dateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    // Gson is thread-safe
    private static final Gson gson = new Gson();

    public PubgPlayer buildPlayer(InputStream in) throws UnsupportedPubgElementException {
        PubgPlayer pubgPlayer = new PubgPlayer();
        Reader jsonReader = new InputStreamReader(in);
        Map map = gson.fromJson(jsonReader, HashMap.class);
        LinkedTreeMap dataMap = null;
        LinkedTreeMap attributeMap = null;
        dataMap = (LinkedTreeMap) ((List) map.get("data")).get(0);
        Assert.notNull(dataMap, "Player's data map should not be null");
        if (dataMap.get("attributes") instanceof LinkedTreeMap) {
            attributeMap = (LinkedTreeMap) dataMap.get("attributes");
        }
        Assert.notNull(attributeMap, "Player's attributea Map should not be null");
        pubgPlayer.setPlayerId((String) dataMap.get("id"));
        pubgPlayer.setCreatedAt(
            LocalDateTime.parse((String) attributeMap.get("createdAt"), dateTimeFormatter));
        pubgPlayer.setName((String) attributeMap.get("name"));
        pubgPlayer.setShardId(PlatformRegion.enumOf((String) attributeMap.get("shardId")));
        pubgPlayer.setTitleId((String) attributeMap.get("titleId"));
        pubgPlayer.setUpdatedAt(
            LocalDateTime.parse((String) attributeMap.get("updatedAt"), dateTimeFormatter));
        List matchList =
            (ArrayList) ((LinkedTreeMap) ((LinkedTreeMap) dataMap.get("relationships")).get(
                "matches")).get("data");
        for (int i = 0; i < matchList.size(); i++) {
            if (matchList.get(i) instanceof LinkedTreeMap) {
                LinkedTreeMap ltm = (LinkedTreeMap) matchList.get(i);
                if (ltm.size() < 2) {
                    continue;
                }
                PubgMatch pubgMatch = new PubgMatch();
                String ltmId = (String) ltm.get("id");
                Assert.notNull(ltmId, "Player's data's match id should not be nul");
                pubgMatch.setMatchId((String) ltm.get("id"));
                pubgPlayer.getMatches().add(pubgMatch);
            }
        }
        return pubgPlayer;
    }

    public PubgMatchDetail buildMatch(InputStream in) throws UnsupportedPubgElementException {
        PubgMatchDetail matchDetail = new PubgMatchDetail();
        HashMap map = gson.fromJson(new InputStreamReader(in), HashMap.class);
        LinkedTreeMap dataMap = (LinkedTreeMap) map.get("data");
        Assert.notNull(dataMap, "dataMap should not be null");
        LinkedTreeMap attributesMap = (LinkedTreeMap) dataMap.get("attributes");
        Assert.notNull(attributesMap, "attributesMap should not be null");
        LinkedTreeMap relationshipsMap = (LinkedTreeMap) dataMap.get("relationships");
        Assert.notNull(relationshipsMap, "relationshipsMap should not be null");
        ArrayList rosterMapList =
            (ArrayList) ((LinkedTreeMap) relationshipsMap.get("rosters")).get("data");
        Assert.notNull(rosterMapList, "rosterMapList should not be null");
        ArrayList includedList = (ArrayList) map.get("included");
        Assert.notNull(includedList, "includedList should not be null");
        matchDetail.setMatchId((String) dataMap.get("id"));
        matchDetail.setCreatedAt(
            LocalDateTime.parse((String) attributesMap.get("createdAt"), dateTimeFormatter));
        matchDetail.setDuration(((Double) attributesMap.get("duration")).intValue());
        matchDetail.setGameMode(GameMode.enumOf((String) attributesMap.get("gameMode")));
        matchDetail.setShardId(PlatformRegion.enumOf((String) attributesMap.get("shardId")));
        matchDetail.setTitleId((String) attributesMap.get("titleId"));
        for (int i = 0; i < rosterMapList.size(); i++) {
            Roster roster = new Roster();
            LinkedTreeMap ltm = (LinkedTreeMap) rosterMapList.get(i);
            Assert.notNull(includedList, "ltm should not be null");
            String tmpId = (String) ltm.get("id");
            Assert.notNull(tmpId, "ltm.get(id) should not be null");
            roster.setRosterId(tmpId);
            matchDetail.getRostetMap().put(roster.getRosterId(), roster);
        }

        for (int i = 0; i < includedList.size(); i++) {
            if (includedList.get(i) instanceof LinkedTreeMap) {
                LinkedTreeMap ltm = (LinkedTreeMap) includedList.get(i);
                if (JsonConstants.TYPE_PARTICIPANT.equals(ltm.get("type"))) {
                    ParticipantDetail participantDetail = new ParticipantDetail();
                    String tmpId = (String) ltm.get("id");
                    Assert.notNull(tmpId, "participant's id should not be null");
                    participantDetail.setParticipantId(tmpId);
                    LinkedTreeMap statsLtm =
                        (LinkedTreeMap) ((LinkedTreeMap) ltm.get("attributes")).get("stats");
                    parseParticipant(participantDetail, statsLtm);
                }
                if (JsonConstants.TYPE_ROSTER.equals(ltm.get("type"))) {
                    String tmpId = (String) ltm.get("id");
                    Assert.notNull(tmpId, "roster's id should not be null");
                    Roster roster = matchDetail.getRostetMap().get(tmpId);
                    LinkedTreeMap atbLtm = (LinkedTreeMap) ltm.get("attributes");
                    LinkedTreeMap statsLtm = (LinkedTreeMap) atbLtm.get("stats");
                    roster.setShardId(PlatformRegion.enumOf((String) atbLtm.get("shardId")));
                    if ("true".equals((String) atbLtm.get("won"))) {
                        roster.setWin(true);
                    } else {
                        roster.setWin(false);
                    }
                    roster.setRank(((Double) statsLtm.get("rank")).intValue());
                    roster.setTeamId(((Double) statsLtm.get("teamId")).intValue());
                }
            }
        }
        return matchDetail;
    }

    private void parseParticipant(ParticipantDetail participant, LinkedTreeMap treeMap) {
        participant.setDbnos(((Double) treeMap.get("DBNOs")).intValue());
        participant.setAssists(((Double) treeMap.get("assists")).intValue());
        participant.setBoosts(((Double) treeMap.get("boosts")).intValue());
        participant.setDamageDealt((Double) treeMap.get("damageDealt"));
        participant.setDeathType((String) treeMap.get("deathType"));
        participant.setHeadshotKills(((Double) treeMap.get("headshotKills")).intValue());
        participant.setHeals(((Double) treeMap.get("heals")).intValue());
        participant.setKillPlace(((Double) treeMap.get("killPlace")).intValue());
        participant.setKillPoints(((Double) treeMap.get("killPoints")).intValue());
        participant.setKillPointsDelta((Double) treeMap.get("killPointsDelta"));
        participant.setKillStreaks(((Double) treeMap.get("killStreaks")).intValue());
        participant.setKills(((Double) treeMap.get("kills")).intValue());
        participant.setLastKillPoints(((Double) treeMap.get("lastKillPoints")).intValue());
        participant.setLastWinPoints(((Double) treeMap.get("lastWinPoints")).intValue());
        participant.setLongestKill((Double) treeMap.get("longestKill"));
        participant.setMostDamage(((Double) treeMap.get("mostDamage")).intValue());
        participant.setName((String) treeMap.get("name"));
        participant.setPlayerId((String) treeMap.get("playerId"));
        participant.setRevives(((Double) treeMap.get("revives")).intValue());
        participant.setRideDistance((Double) treeMap.get("rideDistance"));
        participant.setRoadKills(((Double) treeMap.get("roadKills")).intValue());
        participant.setTeamKills(((Double) treeMap.get("teamKills")).intValue());
        participant.setTimeSurvived(((Double) treeMap.get("timeSurvived")).intValue());
        participant.setVehicleDestroys(((Double) treeMap.get("vehicleDestroys")).intValue());
        participant.setWalkDistance((Double) treeMap.get("walkDistance"));
        participant.setWeaponsAcquired(((Double) treeMap.get("weaponsAcquired")).intValue());
        participant.setWinPlace(((Double) treeMap.get("winPlace")).intValue());
        participant.setWinPoints(((Double) treeMap.get("winPoints")).intValue());
        participant.setWinPointsDelta((Double) treeMap.get("winPointsDelta"));
    }
}
