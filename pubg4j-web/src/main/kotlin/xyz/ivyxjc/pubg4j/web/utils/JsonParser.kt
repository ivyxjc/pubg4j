package xyz.ivyxjc.pubg4j.web.utils

import com.google.gson.Gson
import xyz.ivyxjc.pubg4j.web.entity.JsonPlayer
import xyz.ivyxjc.pubg4j.web.entity.PubgMatchDetail
import xyz.ivyxjc.pubg4j.web.entity.PubgPlayer
import java.io.InputStream
import java.io.InputStreamReader


class JsonParser {
    companion object {
        private val gson = Gson()
        fun parserPlayer(input: InputStream): PubgPlayer {
            val jsonReader = InputStreamReader(input)
            val jsonPlayer = gson.fromJson<JsonPlayer>(jsonReader, JsonPlayer::class.java)
            return jsonPlayer.buildPubgPlayer()
        }

        fun parserMatch(input: InputStream): PubgMatchDetail {
            val jsonReader = InputStreamReader(input)
            return PubgMatchDetail()

        }
    }

}


