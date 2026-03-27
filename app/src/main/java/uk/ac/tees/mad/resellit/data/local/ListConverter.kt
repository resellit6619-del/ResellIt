package uk.ac.tees.mad.resellit.data.local

import androidx.room.TypeConverter


class ListConverter {
    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toList(data: String): List<String> {
        return if (data.isEmpty()) emptyList()
        else data.split(",")
    }
}