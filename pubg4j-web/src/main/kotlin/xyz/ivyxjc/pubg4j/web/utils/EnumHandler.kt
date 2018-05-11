package xyz.ivyxjc.pubg4j.web.utils

import org.apache.ibatis.type.BaseTypeHandler
import org.apache.ibatis.type.JdbcType
import xyz.ivyxjc.pubg4j.web.types.MapName
import xyz.ivyxjc.pubg4j.web.types.PlatformRegion
import java.sql.CallableStatement
import java.sql.PreparedStatement
import java.sql.ResultSet

/**
 * @author Ivyxjc
 * @since 5/8/2018
 */

class PlatformRegionHandler : BaseTypeHandler<PlatformRegion?>() {

    val map = mutableMapOf<String, String>()

    init {
        map.put("PC_AS", "pc-as")
    }

    override fun getNullableResult(rs: ResultSet?, columnName: String?): PlatformRegion? {
        if (rs == null) {
            return null
        }
        val str = rs.getString(columnName)
        return PlatformRegion.enumOf(map.get(str) ?: str)
    }

    override fun getNullableResult(rs: ResultSet?, columnIndex: Int): PlatformRegion? {
        if (rs == null) {
            return null
        }
        val str = rs.getString(columnIndex)
        return PlatformRegion.enumOf(map.get(str) ?: str)
    }

    override fun getNullableResult(cs: CallableStatement?, columnIndex: Int): PlatformRegion? {
        if (cs == null || cs.wasNull()) {
            return null
        }
        val str = cs.getString(columnIndex)
        return PlatformRegion.enumOf(map.get(str) ?: str)
    }

    override fun setNonNullParameter(ps: PreparedStatement?, i: Int, parameter: PlatformRegion?, jdbcType: JdbcType?) {
        ps?.setString(i, parameter?.pltRegion)
    }
}

class MapNameHandler : BaseTypeHandler<MapName?>() {

    override fun getNullableResult(rs: ResultSet?, columnName: String?): MapName? {
        if (rs == null) {
            return null
        }
        val str = rs.getString(columnName)
        return MapName.enumOf(str)
    }

    override fun getNullableResult(rs: ResultSet?, columnIndex: Int): MapName? {
        if (rs == null) {
            return null
        }
        val str = rs.getString(columnIndex)
        return MapName.enumOf(str)
    }

    override fun getNullableResult(cs: CallableStatement?, columnIndex: Int): MapName? {
        if (cs == null || cs.wasNull()) {
            return null
        }
        val str = cs.getString(columnIndex)
        return MapName.enumOf(str)
    }

    override fun setNonNullParameter(ps: PreparedStatement?, i: Int, parameter: MapName?, jdbcType: JdbcType?) {
        ps?.setString(i, parameter?.mapName)
    }
}