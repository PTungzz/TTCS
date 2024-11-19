package com.map.phamthanhtung.vocubularybuilder.database

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.util.Log
import com.map.phamthanhtung.vocubularybuilder.data.Event

class EventDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "events.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "events"
        const val COLUMN_ID = "id"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_DATE = "date"
        const val COLUMN_IS_FAVORITE = "is_favorite"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_DESCRIPTION TEXT NOT NULL,
                $COLUMN_DATE TEXT NOT NULL,
                $COLUMN_IS_FAVORITE INTEGER DEFAULT 0
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Thêm một sự kiện mới
    fun addEvent(event: Event): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DESCRIPTION, event.description)
            put(COLUMN_DATE, event.date)  // Đảm bảo event.date có định dạng đúng, ví dụ 'yyyy-MM-dd'
            put(COLUMN_IS_FAVORITE, if (event.isFavorite) 1 else 0)
        }
        val result = db.insert(TABLE_NAME, null, values)
        Log.d("EventDatabaseHelper", "Event added: $event, Insert result: $result")
        return result
    }


    // Lấy tất cả sự kiện
    fun getAllEvents(): List<Event> {
        val events = mutableListOf<Event>()
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(COLUMN_ID))
                val description = getString(getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                val date = getString(getColumnIndexOrThrow(COLUMN_DATE))
                val isFavorite = getInt(getColumnIndexOrThrow(COLUMN_IS_FAVORITE)) == 1
                events.add(Event(id, description, date, isFavorite))

                Log.d("EventDatabaseHelper", "Event fetched: $events")
            }
            close()
        }
        return events
    }
    @SuppressLint("Range")
    fun getEventsByDate(date: String): List<Event> {
        val events = mutableListOf<Event>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_DATE = ?", arrayOf(date))

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
                val isFavorite = cursor.getInt(cursor.getColumnIndex(COLUMN_IS_FAVORITE)) == 1
                events.add(Event(id, description, date, isFavorite))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return events
    }


    // Xóa một sự kiện
    fun deleteEvent(event: Event): Int {
        val db = writableDatabase
        val result= db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(event.id.toString()))
        Log.d("EventDatabaseHelper", "Event deleted: $event, Rows affected: $result")
        return result
    }

    // Cập nhật trạng thái yêu thích
    fun updateFavorite(event: Event): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_IS_FAVORITE, if (event.isFavorite) 1 else 0)
        }
        return db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(event.id.toString()))
    }
}
