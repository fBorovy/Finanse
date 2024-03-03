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

    @Query("Select * FROM Activity WHERE type == 0 ORDER BY year, month, day")
    fun getSpendingsOrderedByDateAsc(): Flow<List<Activity>>

    @Query("Select * FROM Activity WHERE type == 0 ORDER BY amount DESC")
    fun getSpendingsOrderedByAmount(): Flow<List<Activity>>

    @Query("Select * FROM Activity WHERE type == 0 ORDER BY amount")
    fun getSpendingsOrderedByAmountAsc(): Flow<List<Activity>>

    @Query("Select * FROM Activity WHERE type == 1 ORDER BY year DESC, month DESC,day DESC")
    fun getIncomesOrderedByDate(): Flow<List<Activity>>

    @Query("Select * FROM Activity WHERE type == 1 ORDER BY year, month, day")
    fun getIncomesOrderedByDateAsc(): Flow<List<Activity>>

    @Query("Select * FROM Activity WHERE type == 1 ORDER BY amount DESC")
    fun getIncomesOrderedByAmount(): Flow<List<Activity>>

    @Query("Select * FROM Activity WHERE type == 1 ORDER BY amount")
    fun getIncomesOrderedByAmountAsc(): Flow<List<Activity>>

    @Upsert
    suspend fun upsertInvestment(investment: Investment)

    @Delete
    suspend fun deleteInvestment(investment: Investment)

    @Query("SELECT DISTINCT year FROM Investment")
    fun getInvestmentYearsRange(): Flow<List<Int>>

    @Query("SELECT * FROM Investment ORDER BY year DESC, month DESC, day DESC")
    fun getInvestmentsOrderedByTime(): Flow<List<Investment>>

    @Query("SELECT * FROM Investment ORDER BY year, month, day")
    fun getInvestmentsOrderedByTimeAsc(): Flow<List<Investment>>

    @Query("SELECT * FROM Investment ORDER BY difference DESC")
    fun getInvestmentsOrderedByProfitAmount(): Flow<List<Investment>>

    @Query("SELECT * FROM Investment ORDER BY difference")
    fun getInvestmentsOrderedByProfitAmountAsc(): Flow<List<Investment>>
}