package com.example.detectiveapp.room


import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase

@Database(
    entities = [
        cases::class,
        Client::class,
        Note::class,
        Evidence::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun caseDao(): CaseDao
    abstract fun clientDao(): ClientDao
    abstract fun noteDao(): NoteDao
    abstract fun evidenceDao(): EvidenceDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "criminal_cases.db"
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }
}