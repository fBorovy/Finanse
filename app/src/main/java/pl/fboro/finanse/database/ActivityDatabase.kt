package pl.fboro.finanse.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Activity::class, Investment::class],
    version = 2,
)
abstract class ActivityDatabase: RoomDatabase() {

    abstract val dao: ActivityDao

    companion object {
        val migration1To2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE investment(" +
                        "month INTEGER, year INTEGER, difference REAL, takenOut REAL," +
                        "id INTEGER PRIMARY KEY NOT NULL, day INTEGER, investedIn REAL)")
            }
        }
    }

}