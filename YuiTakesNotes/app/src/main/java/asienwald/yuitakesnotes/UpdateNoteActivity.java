package asienwald.yuitakesnotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class UpdateNoteActivity extends AppCompatActivity {

    EditText etTitle;
    EditText etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
    }
}
