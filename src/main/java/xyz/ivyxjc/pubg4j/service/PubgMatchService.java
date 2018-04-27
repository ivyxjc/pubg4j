package xyz.ivyxjc.pubg4j.service;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.ivyxjc.pubg4j.dao.PubgMatchDetailMapper;
import xyz.ivyxjc.pubg4j.dao.PubgMatchMapper;
import xyz.ivyxjc.pubg4j.dao.PubgParticipantMapper;
import xyz.ivyxjc.pubg4j.dao.PubgRosterMapper;
import xyz.ivyxjc.pubg4j.entity.PubgMatchDetail;

/**
 * @author Ivyxjc
 * @since 4/27/2018
 */
@Service
public class PubgMatchService {

    @Autowired
    private PubgMatchDetailMapper mPubgMatchDetailMapper;

    @Autowired
    private PubgRosterMapper mPubgRosterMapper;

    @Autowired
    private PubgParticipantMapper mPubgParticipantMapper;

    @Autowired
    private PubgMatchMapper mPubgMatchMapper;

    @Transactional
    public void insertPubgMatch(PubgMatchDetail matchDetail) {
        mPubgMatchDetailMapper.insertPubgMatch(matchDetail);
        mPubgRosterMapper.insertPubgRosterList(
            new ArrayList<>(matchDetail.getRosterMap().values()));
        mPubgParticipantMapper.insertPubgParticipantList(
            new ArrayList<>(matchDetail.getParticipantDetailMap().values()));
    }

    @Transactional
    public void deleteAllByMatchId(String matchId) {
        mPubgMatchDetailMapper.deleteByMatchId(matchId);
        mPubgRosterMapper.delteByMatchId(matchId);
        mPubgParticipantMapper.deleteByMatchId(matchId);
    }

    public PubgMatchDetail queryByMatchId(String matchId) {
        PubgMatchDetail pubgMatchDetail = mPubgMatchDetailMapper.queryByMatchId(matchId);
        pubgMatchDetail.setParticipantDetailMap(mPubgParticipantMapper.queryMapByMatchId(matchId));
        pubgMatchDetail.setRosterMap(mPubgRosterMapper.queryMapByMatchId(matchId));
        return pubgMatchDetail;
    }
}
