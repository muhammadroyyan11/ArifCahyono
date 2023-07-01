package com.cahyono.tokoonline.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cahyono.tokoonline.model.Keranjang

@Database(entities = [Keranjang::class] /* List model Ex:NoteModel */, version = 1)
abstract class MyDatabase : RoomDatabase() {
//    abstract fun daoNote(): DaoKeranjang // DaoNote
//
//    companion object {
//        private var INSTANCE: MyDatabase? = null
//
//        fun getInstance(context: Context): MyDatabase? {
//            if (INSTANCE == null) {
//                synchronized(MyDatabase::class) {
//                    INSTANCE = Room.databaseBuilder(
//                        context.applicationContext,
//                        MyDatabase::class.java, "MyDatabaseName" // Database Name
//                    ).allowMainThreadQueries().build()
//                }
//            }
//            return INSTANCE
//        }
//
//        fun destroyInstance() {
//            INSTANCE = null
//        }
//    }
}