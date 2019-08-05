package com.example.sudoku.game

import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.File
import java.io.FileOutputStream



/* use with android api 28

class DataHandler(val context: Context):SQLiteOpenHelper(context ,DATABASE_NAME,null, DATABASE_VERSION){

    private  val preferences: SharedPreferences = context.getSharedPreferences(
        "${context.packageName}.database_versions",
        Context.MODE_PRIVATE
    )

    private fun installDatabaseIsOutdated():Boolean{
        return preferences.getInt(DATABASE_NAME, 0) < DATABASE_VERSION
    }

    private fun writeDatabaseVersionInPreferences(){
        preferences.edit().apply{
            putInt(DATABASE_NAME, DATABASE_VERSION)
            apply()
        }
    }

    private fun installDatabaseFromAssets(){
        val inputStream = context.assets.open("$ASSETS_PATH/$DATABASE_NAME")

        try{
            val outputFile = File(context.getDatabasePath(DATABASE_NAME).path)
            val outputStream = FileOutputStream(outputFile)

            inputStream.copyTo(outputStream)
            inputStream.close()

            outputStream.flush()
            outputStream.close()

        }catch (exception: Throwable){
            throw RuntimeException("The $DATABASE_NAME database couldn't be installed", exception)
        }
    }

    private fun installOrUpdateIfNecessary(){
        if(installDatabaseIsOutdated()){
            context.deleteDatabase(DATABASE_NAME)
            installDatabaseFromAssets()
            writeDatabaseVersionInPreferences()
        }
    }

    override fun getWritableDatabase(): SQLiteDatabase {
        throw RuntimeException("The $DATABASE_NAME database is not writeable")
    }

    override fun getReadableDatabase(): SQLiteDatabase {
        installOrUpdateIfNecessary()
        return super.getReadableDatabase()
    }
    override fun onCreate(db: SQLiteDatabase?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    companion object{
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "SudokuDB.db"
        const val ASSETS_PATH = "databases"
    }

}
*/