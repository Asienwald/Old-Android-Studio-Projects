package asienwald.yuitakesnotes;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class NoteActivity extends AppCompatActivity {

    EditText etTitle;
    EditText etContent;

    String notePurpose;
    boolean updatingNote = false;

    public static List<String> fileNames = new ArrayList<>();
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);

        notePurpose = getIntent().getExtras().getString("NOTE_PURPOSE");
        if(notePurpose.equals("UPDATE")){
            index = getIntent().getExtras().getInt("FILE_POSITION");
            updatingNote = true;
            LoadFile(index);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(updatingNote){
            menu.getItem(1).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_note_save:
                saveFile();
                break;
            case R.id.action_note_delete:
                DeleteFile(index);
                Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
                finish();
        }
        return true;
    }

    public void LoadFile(int i) {
        String fileContent;
        try {
            FileInputStream fis = openFileInput(fileNames.get(i));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer sb = new StringBuffer();

            while ((fileContent = br.readLine()) != null){
                sb.append(fileContent + "\n");
            }

            etContent.setText(sb.toString());
            etTitle.setText(fileNames.get(i));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void DeleteFile(int i){
        File dir = getFilesDir();
        File file = new File(dir, fileNames.get(i));
        if(file.exists()){ file.delete(); }

        fileNames.remove(i);
        SaveFileNames();
    }

    public void saveFile(){
        if(updatingNote){
            DeleteFile(index);

            updatingNote = false;
        }

        if(etTitle.getText().toString().equals("")) {
            Toast.makeText(this, "Please input the title", Toast.LENGTH_SHORT).show();
        }else{

        String fileContent = etContent.getText().toString();
        fileNames.add(etTitle.getText().toString());
        index = fileNames.indexOf(etTitle.getText().toString());


        try{
            FileOutputStream fos = openFileOutput(fileNames.get(index), MODE_PRIVATE);
            fos.write(fileContent.getBytes());
            fos.close();
            Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show();
            etTitle.setText("");
            etContent.setText("");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        SaveFileNames();

        finish();
    }}

    public void SaveFileNames(){
        SharedPreferences sharedPref = getSharedPreferences("fileNames", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear().apply();

        for(int i = 0; i < fileNames.size(); i++){
            editor.putString(String.valueOf(i), fileNames.get(i));
        }
        editor.apply();
    }
}
