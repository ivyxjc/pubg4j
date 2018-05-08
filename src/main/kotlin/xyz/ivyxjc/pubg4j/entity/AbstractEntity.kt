package xyz.ivyxjc.pubg4j.entity

import java.time.LocalDateTime

/**
 * @author Ivyxjc
 * @since 5/7/2018
 */

open class AbstractEntity {
    var dbCreatedAt: LocalDateTime? = null
    var dbCreatedBy: String? = null
    var dbCreatedFrom: String? = null
    var dbUpdatedAt: LocalDateTime? = null
    var dbUpdatedBy: String? = null
    var dbUpdatedFrom: String? = null

}