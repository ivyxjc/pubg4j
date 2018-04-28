package xyz.ivyxjc.pubg4j.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.ivyxjc.pubg4j.dao.PubgMatchDetailMapper;
import xyz.ivyxjc.pubg4j.dao.PubgMatchMapper;
import xyz.ivyxjc.pubg4j.dao.PubgParticipantMapper;
import xyz.ivyxjc.pubg4j.dao.PubgPlayerMapper;
import xyz.ivyxjc.pubg4j.dao.PubgRosterMapper;
import xyz.ivyxjc.pubg4j.entity.PubgMatch;
import xyz.ivyxjc.pubg4j.entity.PubgPlayer;

/**
 * @author Ivyxjc
 * @since 4/25/2018
 */
@Service
public class PubgPlayerService {

    @Autowired
    private PubgPlayerMapper mPubgPlayerMapper;
    @Autowired
    private PubgMatchMapper mPubgMatchMapper;
    @Autowired
    private PubgMatchDetailMapper mPubgMatchDetailMapper;
    @Autowired
    private PubgRosterMapper mPubgRosterMapper;
    @Autowired
    private PubgParticipantMapper mPubgParticipantMapper;

    @Transactional
    public void insertPubgPlayer(PubgPlayer pubgPlayer) {
        mPubgPlayerMapper.insertPubgPlayer(pubgPlayer);
        mPubgMatchMapper.insertPubgMatchList(pubgPlayer.getMatches());
    }

    @Transactional
    public void deleteAllByPlayerId(String playerId) {
        List<PubgMatch> pubgMatches = mPubgMatchMapper.queryByPlayerId(playerId);
        if (pubgMatches.size() == 0) {
            return;
        }
        List<String> matchIds = new ArrayList<>();
        pubgMatches.forEach(t -> matchIds.add(t.getMatchId()));
        mPubgPlayerMapper.deleteByPubgPlayerId(playerId);
        mPubgMatchMapper.deleteByPlayerId(playerId);
        mPubgMatchDetailMapper.deleteByMatchIdList(matchIds);
        mPubgRosterMapper.deleteByMatchIdList(matchIds);
        mPubgParticipantMapper.deleteByMatchIdList(matchIds);
    }

    public PubgPlayer queryByPlayerName(String playerName) {
        return mPubgPlayerMapper.queryByPlayerName(playerName);
    }
}
