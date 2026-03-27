package uk.ac.tees.mad.resellit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [ListingEntity::class],
    version = 1 ,
)
@TypeConverters(ListConverter::class)
abstract class ListingDatabase : RoomDatabase(){
    abstract fun getListingDao() : ListingDao
}


/**
 *this is database currently have table my_listings in which only items posted by user
 * will be stored for now
 */