package com.example.fafoo.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
   //Create an ArrayList
    ArrayList<String> items;
    //Create an ArrayAdapter
    ArrayAdapter<String> itemsAdapter;
    ListView LvItems;
    EditText etEditText;
    private final int REQUEST_CODE=20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Attach adapter to ListView
        setContentView(R.layout.activity_main);
        LvItems = (ListView) findViewById(R.id.LvItems);
        //Load items during onCreate()
        readItems();
        itemsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, items);
        LvItems.setAdapter(itemsAdapter);
        //Invoke listener from onCreate()
        SetupListViewListener();
    }

    //Method for setting up the listener
    private void SetupListViewListener() {
        LvItems.setOnItemLongClickListener(
                //Attach a LongClickListener to each item for the ListView that can Removes that item and Refreshes the adapter
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }

                });

        LvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(getApplicationContext(), EditItemActivity.class);
                         //put "extras" into the bundle for access in the second activity
                                i.putExtra("text", items.get(position));
                                i.putExtra("position", position);
                        // brings up the second activity
                        startActivityForResult(i, REQUEST_CODE);
                    }

                    @Nullable
                        public final AdapterView.OnItemClickListener getOnItemClickListener() {
                        return null;
                    }
                });
    }

    //Method to open a file and read a newline-delimited list of items
    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    //Method to save a file and write a newline-delimited list of items
    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Method for adding a item to the list
    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        //Save items when a new list item is added
        writeItems();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
          String text = data.getExtras().getString("text");
          int position = data.getExtras().getInt("position", 0);
          items.set(position,text);
          itemsAdapter.notifyDataSetChanged();
          writeItems();
        }

    }
}





