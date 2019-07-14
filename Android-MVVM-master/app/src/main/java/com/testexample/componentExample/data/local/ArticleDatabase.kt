package com.testexample.componentExample.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.testexample.componentExample.data.local.dao.ArticleDao
import com.testexample.componentExample.data.local.entity.ArticleEntity
import com.testexample.componentExample.utils.Converters

/**
 * File Description
 *
 */
@Database(entities = [ArticleEntity::class], version = 2)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}