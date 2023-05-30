package pl.fboro.finanse.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Activity::class, Investment::class],
    version = 1,
)
abstract class ActivityDatabase: RoomDatabase() {

    abstract val dao: ActivityDao
}