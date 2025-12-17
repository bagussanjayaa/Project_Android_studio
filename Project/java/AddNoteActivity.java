package com.example.catataja;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {

    private EditText inputTitle, inputContent;
    private Button btnSave;
    private ImageView btnBack;   // tambahkan variabel untuk tombol back
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        inputTitle = findViewById(R.id.edtTitle);
        inputContent = findViewById(R.id.edtContent);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBackAdd); // inisialisasi tombol back

        db = new DatabaseHelper(this);

        btnSave.setOnClickListener(v -> {
            String title = inputTitle.getText().toString();
            String content = inputContent.getText().toString();

            db.addNote(title, content);
            finish(); // kembali ke activity sebelumnya setelah simpan
        });

        // listener untuk tombol back
        btnBack.setOnClickListener(v -> {
            finish(); // langsung kembali ke activity sebelumnya
        });
    }
}