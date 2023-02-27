package geekbrains.ru.translator.db

import androidx.room.RoomDatabase

@androidx.room.Database(entities = [HistoryEntity::class], version = 1, exportSchema = false)
abstract class HistoryDataBase:RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}