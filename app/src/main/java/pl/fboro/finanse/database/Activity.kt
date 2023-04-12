package pl.fboro.finanse.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Activity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val day: Int,
    val month: Int,
    val year: Int,
    val amount: Double,
    val title: String,
    val source: Char,
    val type: Int,
)
