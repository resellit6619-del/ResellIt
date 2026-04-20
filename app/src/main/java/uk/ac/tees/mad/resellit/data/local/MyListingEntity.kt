package uk.ac.tees.mad.resellit.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_listings")
data class MyListingEntity(
    @PrimaryKey val listingId: String,
    val title: String,
    val description: String,
    val price: String,
    val location: String,
    val imageUrls: List<String>,
    val userId: String ,
//    val createdAt : String
)