package pl.fboro.finanse.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Investment(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val day: Int,
    val month: Int,
    val year: Int,
    val instrument: String = "",
    val investedIn: Double,
    val takenOut: Double,
    val difference: Double,
)
