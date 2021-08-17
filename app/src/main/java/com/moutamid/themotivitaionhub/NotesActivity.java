package com.moutamid.themotivitaionhub;

import static com.moutamid.themotivitaionhub.Utils.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity {

    private ImageView saveNotesBtn;
    private AppCompatLineEditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        saveNotesBtn = findViewById(R.id.saveBtnNotes);
        saveNotesBtn.setOnClickListener(saveNotesBtnClickListener());


        findViewById(R.id.settingsBtnNotes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotesActivity.this, SettingsActivity.class));
            }
        });

        editText = findViewById(R.id.notesEdittext);
        editText.addTextChangedListener(notesEditTextWatcher());

    }

    private View.OnClickListener saveNotesBtnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().isEmpty()) {
                    toast("Please enter some text in the notes!");
                    return;
                }

                // THIS IS THE ARRAY LIST STORED IN THE SHARED PREFERENCES
                ArrayList<String> arrayList = getArrayList(Constants.NOTES_LIST);
                if (arrayList.get(0).equals("Error")) {
                    ArrayList<String> notesArrayList = new ArrayList<>();
                    notesArrayList.add(editText.getText().toString());
                    store(Constants.NOTES_LIST, notesArrayList);
                } else {
                    arrayList.add(editText.getText().toString());
                    store(Constants.NOTES_LIST, arrayList);
                }
                editText.setText("");
                toast("Saved!");
                saveNotesBtn.setVisibility(View.GONE);

            }
        };
    }

    private TextWatcher notesEditTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // THIS IS USED WHEN A USER TYPES SOMETHING IN THE NOTES THEN THE SAVE BUTTON APPEARS
                saveNotesBtn.setVisibility(View.VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }
}