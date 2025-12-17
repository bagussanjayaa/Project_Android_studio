package com.example.catataja;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "catataja.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE notes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "content TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS notes");
        onCreate(db);
    }

    // Tambah catatan baru
    public void addNote(String title, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("title", title);
        cv.put("content", content);

        db.insert("notes", null, cv);
    }

    // Ambil catatan berdasarkan id
    public Note getNote(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM notes WHERE id=?", new String[]{String.valueOf(id)});

        if (c.moveToFirst()) {
            return new Note(c.getInt(0), c.getString(1), c.getString(2));
        }
        return null;
    }

    // Ambil semua catatan, urutkan berdasarkan id terbaru
    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM notes ORDER BY id DESC", null);

        while (c.moveToNext()) {
            notes.add(new Note(c.getInt(0), c.getString(1), c.getString(2)));
        }
        return notes;
    }

    // Revisi: update catatan dengan cara hapus lalu insert ulang
    public void updateNote(int id, String title, String content) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Hapus catatan lama
        db.delete("notes", "id=?", new String[]{String.valueOf(id)});

        // Tambahkan catatan baru dengan id baru (autoincrement)
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("content", content);
        db.insert("notes", null, cv);
    }

    // Hapus catatan
    public void deleteNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("notes", "id=?", new String[]{String.valueOf(id)});
    }
}