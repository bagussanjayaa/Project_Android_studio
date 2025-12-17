package com.example.catataja;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class EditNoteActivity extends AppCompatActivity {

    private EditText inputTitle, inputContent;
    private Button btnSave;
    private ImageView btnBack;   // tambahkan variabel untuk tombol back
    private int noteId;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_note);

        inputTitle = findViewById(R.id.edtEditTitle);
        inputContent = findViewById(R.id.edtEditContent);
        btnSave = findViewById(R.id.btnSaveEdit);
        btnBack = findViewById(R.id.btnBackEdit); // inisialisasi tombol back

        db = new DatabaseHelper(this);

        noteId = getIntent().getIntExtra("NOTE_ID", -1);
        Note note = db.getNote(noteId);

        inputTitle.setText(note.getTitle());
        inputContent.setText(note.getContent());

        btnSave.setOnClickListener(v -> {
            db.updateNote(noteId, inputTitle.getText().toString(), inputContent.getText().toString());
            finish(); // kembali setelah update
        });

        // Listener tombol back
        btnBack.setOnClickListener(v -> {
            finish(); // kembali ke activity sebelumnya
        });
    }
}