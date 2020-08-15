package asienwald.yuitakesnotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lvNotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvNotes = findViewById(R.id.lvNotes);

        LoadFileNames();
        UpdateListView();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        UpdateListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_main_new_note:
                Intent intent = new Intent(this, NoteActivity.class);
                intent.putExtra("NOTE_PURPOSE", "NEW");
                startActivity(intent);

        }
        return true;
    }

    public void UpdateListView(){
        ListAdapter la = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                NoteActivity.fileNames);

        lvNotes.setAdapter(la);
        lvNotes.smoothScrollToPosition(0);

        lvNotes.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                        intent.putExtra("NOTE_PURPOSE", "UPDATE");
                        intent.putExtra("FILE_POSITION", position);
                        startActivity(intent);
                    }
                }
        );
    }

    public void LoadFileNames(){
        SharedPreferences sharedPref = getSharedPreferences("fileNames", Context.MODE_PRIVATE);

        for(int i = 0; i < sharedPref.getAll().size(); i++){
            NoteActivity.fileNames.add(sharedPref.getString(
                    String.valueOf(i), ""));
        }
    }

}
