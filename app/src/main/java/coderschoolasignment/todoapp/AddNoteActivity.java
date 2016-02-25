package coderschoolasignment.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import coderschoolasignment.todoapp.SqliteOpenHelper.SqlOpenHelper;

public class AddNoteActivity extends AppCompatActivity {
    private Toolbar toolbar;
    EditText editText_title,editText_content;
    Button button_reset,button_save;
    SqlOpenHelper helper = new SqlOpenHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note_activity);

        registerWidget();
        setEventforView();

        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


    }

    private void setEventforView() {
        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_title.setText("");
                editText_content.setText("");
            }
        });
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title,content;
                title = editText_title.getText().toString().trim();
                content = editText_content.getText().toString().trim();
                if(content.length()==0)
                {
                    Toast.makeText(AddNoteActivity.this, "Please Insert Content", Toast.LENGTH_SHORT).show();
                }else
                {
                    Note t = new Note();
                    t.setTitle(title);
                    t.setContent(content);
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = df.format(c.getTime());
                    t.setTime(formattedDate);
                    t.setColor(Colors.getInstance());
                    t.setStatus(1);
                    helper.insertNote(t);
                    Intent i = getIntent();
                    i.putExtra("note",helper.getLastNote());
                    setResult(Activity.RESULT_OK,i);
                    finish();

                }

            }
        });
    }

    private void registerWidget() {
        toolbar  = (Toolbar)findViewById(R.id.toolbar);
        editText_title = (EditText)findViewById(R.id.editText_title);
        editText_content = (EditText)findViewById(R.id.editText_content);
        button_reset = (Button) findViewById(R.id.button_reset);
        button_save = (Button) findViewById(R.id.button_save);

    }


}
