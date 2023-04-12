package pl.fboro.finanse.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {

    @Upsert
    suspend fun upsertActivity(activity: Activity)

    @Delete
    suspend fun deleteActivity(activity: Activity)

    @Query("Select * FROM Activity WHERE type == 0 ORDER BY year,month,day")
    fun getSpendingsOrderedByDate(): Flow<List<Activity>>

    @Query("Select * FROM Activity WHERE type == 0 ORDER BY amount")
    fun getSpendingsOrderedByAmount(): Flow<List<Activity>>

    @Query("Select * FROM Activity WHERE type == 1 ORDER BY year,month,day")
    fun getIncomesOrderedByDate(): Flow<List<Activity>>

    @Query("Select * FROM Activity WHERE type == 1 ORDER BY amount")
    fun getIncomesOrderedByAmount(): Flow<List<Activity>>
}