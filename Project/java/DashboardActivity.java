package com.example.catataja;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private DatabaseHelper databaseHelper;
    private ImageView fab;   // pakai ImageView sebagai tombol tambah

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerView = findViewById(R.id.recyclerNotes);
        fab = findViewById(R.id.btnAddNote);

        databaseHelper = new DatabaseHelper(this);

        // load catatan pertama kali
        loadNotes();

        // tombol tambah catatan
        fab.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, AddNoteActivity.class));
        });
    }

    // method untuk load catatan dari database
    private void loadNotes() {
        List<Note> notes = databaseHelper.getAllNotes(); // ORDER BY id DESC
        adapter = new NoteAdapter(notes, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // refresh daftar catatan setiap kali kembali dari Add/Edit
        loadNotes();
    }
}