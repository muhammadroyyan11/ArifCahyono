package com.cahyono.tokoonline.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.cahyono.tokoonline.model.Keranjang
import com.cahyono.tokoonline.model.Produk

@Dao
interface DaoKeranjang {

//    @Insert(onConflict = REPLACE)
//    fun insert(data: Keranjang)
//
//    @Delete
//    fun delete(data: Keranjang)
//
//    @Update
//    fun update(data: Keranjang): Int
//
//    @Query("SELECT * from keranjang ORDER BY id ASC")
//    fun getAll(): List<Produk>
//
//    @Query("SELECT * FROM keranjang WHERE id = :id LIMIT 1")
//    fun getNote(id: Int): Produk
//
//    @Query("DELETE FROM keranjang")
//    fun deleteAll(): Int
}