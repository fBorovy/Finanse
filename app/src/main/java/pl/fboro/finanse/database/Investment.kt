package pl.fboro.finanse.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Investment(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val day: Int?,
    val month: Int?,
    val year: Int?,
    val investedIn: Double?,
    val takenOut: Double?,
    val difference: Double?,
) // fields are nullable because it fixed the
// "Android Room migrations did not properly handled" error... found and excepted
