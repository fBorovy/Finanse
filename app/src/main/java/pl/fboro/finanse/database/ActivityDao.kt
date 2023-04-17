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

    @Query("SELECT DISTINCT year FROM Activity")
    fun getYearsRange(): Flow<List<Int>>

    @Query("Select * FROM Activity WHERE type == 0 ORDER BY year DESC,month DESC,day DESC")
    fun getSpendingsOrderedByDate(): Flow<List<Activity>>

    @Query("Select * FROM Activity WHERE type == 0 ORDER BY amount DESC")
    fun getSpendingsOrderedByAmount(): Flow<List<Activity>>

    @Query("Select * FROM Activity WHERE type == 1 ORDER BY year DESC, month DESC,day DESC")
    fun getIncomesOrderedByDate(): Flow<List<Activity>>

    @Query("Select * FROM Activity WHERE type == 1 ORDER BY amount DESC")
    fun getIncomesOrderedByAmount(): Flow<List<Activity>>
}