package xyz.ivyxjc.pubg4j;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.ivyxjc.pubg4j.constants.JsonConstants;
import xyz.ivyxjc.pubg4j.entity.Participant;
import xyz.ivyxjc.pubg4j.entity.ParticipantDetail;
import xyz.ivyxjc.pubg4j.entity.PubgMatch;
import xyz.ivyxjc.pubg4j.entity.PubgMatchDetail;
import xyz.ivyxjc.pubg4j.entity.PubgPlayer;
import xyz.ivyxjc.pubg4j.entity.Roster;
import xyz.ivyxjc.pubg4j.exception.UnsupportedPubgElementException;
import xyz.ivyxjc.pubg4j.types.GameMode;
import xyz.ivyxjc.pubg4j.types.MapName;
import xyz.ivyxjc.pubg4j.types.PlatformRegion;

/**
 * @author Ivyxjc
 * @since 4/22/2018
 */
@Slf4j
@Service
public class JsonBuilder {
    //private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ISO_INSTANT;
    private static final DateTimeFormatter dateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public static void main(String[] args)
        throws IOException, UnsupportedPubgElementException {
        JsonBuilder jsonBuilder = new JsonBuilder();
        long t1 = System.currentTimeMillis();
        //for (int i = 0; i < 100; i++) {
        InputStream in = JsonBuilder.class.getClassLoader().getResourceAsStream("match.json");
        jsonBuilder.buildMatch(in);
        //}
        long t2 = System.currentTimeMillis();
        log.debug((t2 - t1) + "");
    }

    public PubgPlayer buildPlayer(InputStream in)
        throws IOException, UnsupportedPubgElementException {
        PubgPlayer pubgPlayer = new PubgPlayer();
        Reader reader = new InputStreamReader(in);
        //Gson gson=new Gson();
        JsonReader jsonReader = new JsonReader(reader);
        while (jsonReader.hasNext()) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                //data
                log.debug(jsonReader.nextName());
                jsonReader.beginArray();
                jsonReader.beginObject();
                //type
                log.debug(jsonReader.nextName());
                //"player"
                jsonReader.nextString();
                //id
                jsonReader.nextName();
                //id's value
                pubgPlayer.setPlayerId(jsonReader.nextString());
                log.debug(pubgPlayer.getPlayerId());
                //attributes
                jsonReader.nextName();
                jsonReader.beginObject();
                //createdAt
                jsonReader.nextName();
                pubgPlayer.setCreatedAt(
                    LocalDateTime.parse(jsonReader.nextString(), dateTimeFormatter));
                log.debug(pubgPlayer.getCreatedAt().toString());
                //name
                jsonReader.nextName();
                pubgPlayer.setName(jsonReader.nextString());
                //patchVersion
                jsonReader.nextName();
                jsonReader.nextString();
                //shardId
                log.debug(jsonReader.nextName());
                //String s=jsonReader.nextString();
                //log.debug(s);
                pubgPlayer.setShardId(PlatformRegion.enumOf(jsonReader.nextString()));
                log.debug(pubgPlayer.getShardId().getPltRegion());
                //stats
                jsonReader.nextName();
                jsonReader.skipValue();
                //titleId
                jsonReader.nextName();
                pubgPlayer.setTitleId(jsonReader.nextString());
                log.debug(pubgPlayer.getTitleId());
                //updated
                jsonReader.nextName();
                pubgPlayer.setUpdatedAt(
                    LocalDateTime.parse(jsonReader.nextString(), dateTimeFormatter));
                log.debug(pubgPlayer.getUpdatedAt().toString());
                jsonReader.endObject();
                //relationships
                jsonReader.nextName();
                jsonReader.beginObject();
                //assets
                jsonReader.nextName();
                jsonReader.beginObject();
                //data
                jsonReader.nextName();
                jsonReader.beginArray();
                jsonReader.endArray();
                jsonReader.endObject();
                //matches
                jsonReader.nextName();
                jsonReader.beginObject();
                //data
                jsonReader.nextName();
                jsonReader.beginArray();
                while (!jsonReader.peek().equals(JsonToken.END_ARRAY)) {
                    PubgMatch pm = new PubgMatch();
                    jsonReader.beginObject();
                    //type
                    jsonReader.nextName();
                    jsonReader.nextString();
                    //id
                    jsonReader.nextName();
                    pm.setMatchId(jsonReader.nextString());
                    jsonReader.endObject();
                    pubgPlayer.getMatches().add(pm);
                }
                break;
            }
            break;
        }
        return pubgPlayer;
    }

    public PubgMatchDetail buildMatch(InputStream in)
        throws IOException, UnsupportedPubgElementException {
        PubgMatchDetail pubgMatch = new PubgMatchDetail();
        Reader reader = new InputStreamReader(in);
        //Gson gson=new Gson();
        JsonReader jsonReader = new JsonReader(reader);
        while (jsonReader.hasNext()) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                //data
                log.debug(jsonReader.nextName());
                jsonReader.beginObject();
                //type
                log.debug(jsonReader.nextName());
                //"player"
                jsonReader.nextString();
                //id
                jsonReader.nextName();
                //id's value
                pubgMatch.setMatchId(jsonReader.nextString());
                log.debug(pubgMatch.getMatchId());
                //attributes
                jsonReader.nextName();
                jsonReader.beginObject();
                //createdAt
                jsonReader.nextName();
                pubgMatch.setCreatedAt(
                    LocalDateTime.parse(jsonReader.nextString(), dateTimeFormatter));
                log.debug(pubgMatch.getCreatedAt().toString());
                //duration
                jsonReader.nextName();
                pubgMatch.setDuration(Integer.parseInt(jsonReader.nextString()));
                //gameMode
                jsonReader.nextName();
                pubgMatch.setGameMode(GameMode.enumOf(jsonReader.nextString()));
                //mapName
                jsonReader.nextName();
                pubgMatch.setMapName(MapName.enumOf(jsonReader.nextString()));
                //shardId
                jsonReader.nextName();
                pubgMatch.setShardId(PlatformRegion.enumOf(jsonReader.nextString()));
                //stats
                jsonReader.nextName();
                jsonReader.skipValue();
                //tags
                jsonReader.nextName();
                jsonReader.skipValue();
                //titleId
                jsonReader.nextName();
                pubgMatch.setTitleId(jsonReader.nextString());
                jsonReader.endObject();

                //relationships
                jsonReader.nextName();
                jsonReader.beginObject();
                //assets
                jsonReader.nextName();
                jsonReader.beginObject();
                //data
                jsonReader.nextName();
                jsonReader.beginArray();
                jsonReader.beginObject();
                //type
                jsonReader.nextName();
                jsonReader.skipValue();
                //id
                jsonReader.nextName();
                jsonReader.skipValue();
                jsonReader.endObject();
                jsonReader.endArray();
                jsonReader.endObject();

                //rosters
                jsonReader.nextName();
                jsonReader.beginObject();
                //data
                jsonReader.nextName();
                jsonReader.beginArray();
                while (!jsonReader.peek().equals(JsonToken.END_ARRAY)) {
                    Roster roster = new Roster();
                    jsonReader.beginObject();
                    //type
                    jsonReader.nextName();
                    jsonReader.nextString();
                    //id
                    jsonReader.nextName();
                    roster.setRosterId(jsonReader.nextString());
                    pubgMatch.getRostetMap().put(roster.getRosterId(), roster);
                    jsonReader.endObject();
                }
                jsonReader.endArray();
                jsonReader.endObject();
                jsonReader.endObject();

                //links
                jsonReader.nextName();
                jsonReader.beginObject();
                //schema
                jsonReader.nextName();
                jsonReader.skipValue();
                //self
                jsonReader.nextName();
                jsonReader.skipValue();
                jsonReader.endObject();
                jsonReader.endObject();

                //included
                jsonReader.nextName();
                jsonReader.beginArray();
                while (!jsonReader.peek().equals(JsonToken.END_ARRAY)) {
                    //type
                    jsonReader.beginObject();
                    jsonReader.nextName();
                    String type = jsonReader.nextString();
                    if (JsonConstants.TYPE_PARTICIPANT.equals(type)) {
                        parseParticipant(pubgMatch, jsonReader);
                    }
                    if (JsonConstants.TYPE_ROSTER.equals(type)) {
                        parseRoster(pubgMatch, jsonReader);
                    }
                    if (JsonConstants.TYPE_ASSET.equals(type)) {
                        parsetAsset(pubgMatch, jsonReader);
                    }
                }
                break;
            }
            break;
        }
        log.debug("RosterList's size: {}", pubgMatch.getRostetMap().size());
        return pubgMatch;
    }

    public void parseRoster(PubgMatchDetail pubgMatch, JsonReader jsonReader) throws IOException {
        //id
        jsonReader.nextName();
        Roster roster = pubgMatch.getRostetMap().get(jsonReader.nextString());
        //attributes
        jsonReader.nextName();
        jsonReader.beginObject();

        //shardId
        jsonReader.nextName();
        jsonReader.skipValue();
        //stats
        jsonReader.nextName();
        jsonReader.beginObject();
        //rank
        jsonReader.nextName();
        roster.setRank(jsonReader.nextInt());
        //teamId
        jsonReader.nextName();
        roster.setTeamId(jsonReader.nextInt());
        jsonReader.endObject();
        //won
        jsonReader.nextName();
        if (JsonConstants.ROSTER_WON_TRUE.equals(jsonReader.nextString())) {
            roster.setWin(true);
        } else {
            roster.setWin(false);
        }
        jsonReader.endObject();
        //relationships
        jsonReader.nextName();
        jsonReader.beginObject();
        //participants
        jsonReader.nextName();
        jsonReader.beginObject();
        //data
        jsonReader.nextName();
        jsonReader.beginArray();
        while (!JsonToken.END_ARRAY.equals(jsonReader.peek())) {
            Participant p = new Participant();
            jsonReader.beginObject();
            //type
            jsonReader.nextName();
            jsonReader.skipValue();
            //id
            jsonReader.nextName();
            p.setParticipantId(jsonReader.nextString());
            roster.getParticipantList().add(p);
            jsonReader.endObject();
        }
        jsonReader.endArray();
        jsonReader.endObject();
        //team
        jsonReader.nextName();
        jsonReader.beginObject();
        //data
        jsonReader.nextName();
        jsonReader.skipValue();
        jsonReader.endObject();
        jsonReader.endObject();
        jsonReader.endObject();
    }

    private void parseParticipant(PubgMatchDetail pubgMatch, JsonReader jsonReader)
        throws IOException {
        ParticipantDetail participant = new ParticipantDetail();
        //id
        jsonReader.nextName();
        participant.setParticipantId(jsonReader.nextString());
        //attributes
        jsonReader.nextName();
        jsonReader.beginObject();
        //actor
        jsonReader.nextName();
        jsonReader.skipValue();
        //shardId
        jsonReader.nextName();
        jsonReader.skipValue();
        //stats
        jsonReader.nextName();
        jsonReader.beginObject();
        //DBNOs
        jsonReader.nextName();
        participant.setDbnos(jsonReader.nextInt());
        jsonReader.nextName();
        participant.setAssists(jsonReader.nextInt());
        jsonReader.nextName();
        participant.setBoosts(jsonReader.nextInt());
        jsonReader.nextName();
        participant.setDamageDealt(jsonReader.nextDouble());
        jsonReader.nextName();
        participant.setDeathType(jsonReader.nextString());
        jsonReader.nextName();
        participant.setHeadshotKills(jsonReader.nextInt());
        jsonReader.nextName();
        participant.setHeals(jsonReader.nextInt());
        jsonReader.nextName();
        participant.setKillPlace(jsonReader.nextInt());
        jsonReader.nextName();
        participant.setKillPoints(jsonReader.nextInt());
        jsonReader.nextName();
        participant.setKillPointsDelta(jsonReader.nextDouble());
        jsonReader.nextName();
        participant.setKillStreaks(jsonReader.nextInt());
        jsonReader.nextName();
        participant.setKills(jsonReader.nextInt());
        jsonReader.nextName();
        participant.setLastKillPoints(jsonReader.nextInt());
        jsonReader.nextName();
        participant.setLastWinPoints(jsonReader.nextInt());
        jsonReader.nextName();
        participant.setLongestKill(jsonReader.nextDouble());
        jsonReader.nextName();
        participant.setMostDamage(jsonReader.nextInt());
        jsonReader.nextName();
        participant.setName(jsonReader.nextString());
        jsonReader.nextName();
        participant.setPlayerId(jsonReader.nextString());
        jsonReader.nextName();
        participant.setRevives(jsonReader.nextInt());
        jsonReader.nextName();
        participant.setRideDistance(jsonReader.nextDouble());
        jsonReader.nextName();
        participant.setRoadKills(jsonReader.nextInt());
        jsonReader.nextName();
        participant.setTeamKills(jsonReader.nextInt());
        jsonReader.nextName();
        participant.setTimeSurvived(jsonReader.nextInt());
        jsonReader.nextName();
        participant.setVehicleDestroys(jsonReader.nextInt());
        jsonReader.nextName();
        participant.setWalkDistance(jsonReader.nextDouble());
        jsonReader.nextName();
        participant.setWeaponsAcquired(jsonReader.nextInt());
        jsonReader.nextName();
        participant.setWinPlace(jsonReader.nextInt());
        jsonReader.nextName();
        participant.setWinPoints(jsonReader.nextInt());
        jsonReader.nextName();
        participant.setWinPointsDelta(jsonReader.nextDouble());
        jsonReader.endObject();
        jsonReader.endObject();
        jsonReader.endObject();
    }

    private void parsetAsset(PubgMatchDetail pubgMatch, JsonReader jsonReader) throws IOException {
        //id
        jsonReader.nextName();
        jsonReader.nextString();
        //attributes
        jsonReader.nextName();
        jsonReader.beginObject();
        //url
        jsonReader.nextName();
        jsonReader.nextString();
        //createdAt
        jsonReader.nextName();
        LocalDateTime.parse(jsonReader.nextString(), dateTimeFormatter);
        //description
        jsonReader.nextName();
        jsonReader.skipValue();
        //name
        jsonReader.nextName();
        jsonReader.skipValue();
        jsonReader.endObject();
        jsonReader.endObject();
    }
}
