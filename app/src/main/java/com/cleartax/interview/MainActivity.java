package com.cleartax.interview;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> wordList = new ArrayList<>();
    private EditText editText;
    private TextView wordCount;
    private Button undo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        wordCount = findViewById(R.id.word_count);
        undo = findViewById(R.id.undo);

        if (savedInstanceState != null) {
            wordList = savedInstanceState.getStringArrayList("parcelable");
            setUiState();
        }
        findViewById(R.id.full_window).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setFocusable(false);
            }
        });
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
            }
        });

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String stringUpdated = editText.getText().toString();
                    wordList.add(stringUpdated);

                    String[] words = stringUpdated.split(" ");

                    wordCount.setText(words.length + " words");
                }
                undo.setEnabled(!wordList.isEmpty());
            }
        });
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!wordList.isEmpty()) {
                    wordList.remove(wordList.size() - 1);
                }

                setUiState();
            }
        });
    }

    private void setUiState() {
        if (!wordList.isEmpty()) {
            final String text = wordList.get(wordList.size() - 1);
            editText.setText(text);
            String[] words = text.split(" ");
            wordCount.setText(words.length + " words");
        } else {
            editText.setText("");
            wordCount.setText("0 words");
        }
        undo.setEnabled(!wordList.isEmpty());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("parcelable", wordList);
    }

    /*@Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        wordList = savedInstanceState.getStringArrayList("parcelable");
    }*/
}
