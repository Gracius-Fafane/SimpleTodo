package com.example.fafoo.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        // take the select text in mainActivity with his position and put it in EditActivity
        String textBody = getIntent().getStringExtra("text");
        EditText etText = findViewById(R.id.etText);
        etText.setText(textBody);
        etText.setSelection(etText.getText().length());
    }

    //Save after modification when I click on button save
    public void onSubmit (View v){

        EditText etText = findViewById(R.id.etText);
        Intent data = new Intent();
        data.putExtra("text", etText.getText().toString());
        int textPosition = getIntent().getIntExtra("position", 0);
        data.putExtra("position", textPosition);
        setResult(RESULT_OK, data);
        //close editActivity when i finish
        finish();

    }
}
