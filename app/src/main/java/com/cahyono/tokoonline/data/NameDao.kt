package com.cahyono.tokoonline.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.cahyono.tokoonline.model.Produk

@Dao
interface NameDao {
    @Query("SELECT * FROM name")
    fun getAll(): List<Produk>

    @Insert
    fun insertAll(name: Produk)

    @Delete
    fun delete(user: Produk)
}