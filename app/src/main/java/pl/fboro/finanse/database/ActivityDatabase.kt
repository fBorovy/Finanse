package pl.fboro.finanse.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Activity::class, Investment::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class ActivityDatabase: RoomDatabase() {

    abstract val dao: ActivityDao
}