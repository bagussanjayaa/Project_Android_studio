package com.example.catataja;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailNoteActivity extends AppCompatActivity {

    private TextView detailTitle, detailContent;
    private Button btnEdit, btnDelete;
    private ImageView btnBack;   // tambahkan variabel untuk tombol back
    private DatabaseHelper db;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_note);

        detailTitle = findViewById(R.id.txtNoteTitleDetail);
        detailContent = findViewById(R.id.txtNoteContentDetail);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        btnBack = findViewById(R.id.btnBackDetail); // inisialisasi tombol back

        db = new DatabaseHelper(this);

        noteId = getIntent().getIntExtra("NOTE_ID", -1);
        Note note = db.getNote(noteId);

        detailTitle.setText(note.getTitle());
        detailContent.setText(note.getContent());

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditNoteActivity.class);
            intent.putExtra("NOTE_ID", noteId);
            startActivity(intent);
        });

        btnDelete.setOnClickListener(v -> {
            db.deleteNote(noteId);
            finish();
        });

        // Listener tombol back
        btnBack.setOnClickListener(v -> {
            finish(); // kembali ke activity sebelumnya (dashboard/list catatan)
        });
    }
}