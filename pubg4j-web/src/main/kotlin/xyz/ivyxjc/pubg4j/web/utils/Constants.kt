package xyz.ivyxjc.pubg4j.web.utils

/**
 * @author Ivyxjc
 * @since 5/7/2018
 */

object ApiConstants {
    @JvmField
    val FILTER_PLAYER_NAME = "https://api.playbattlegrounds.com/shards/%s/players?filter[playerNames]=%s"
    @JvmField
    val FILTER_MATCH_ID = "https://api.playbattlegrounds.com/shards/%s/matches/%s"
}

object JsonConstants {
    @JvmField
    val TYPE_PARTICIPANT = "participant"
    @JvmField
    val TYPE_ROSTER = "roster"
    @JvmField
    val TYPE_ASSET = "asset"
    @JvmField
    val ROSTER_WON_TRUE = "true"
}

object KafkaConstans {

    @JvmField
    val TOPIC_PUBG_PLAYER = "pubg_player"
    @JvmField
    val TOPIC_PUBG_MATCH = "pubg_match"
}