package coderschoolasignment.todoapp.AddNote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import coderschoolasignment.todoapp.R;

public class AddNewLinkActivity extends AppCompatActivity {
    private EditText editText_add_link;
    private Button button_add_link_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_link);

        registerWidget();
        setEventForWidget();
    }

    private void setEventForWidget() {
        button_add_link_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = editText_add_link.getText().toString().trim();
                if(link.length()==0)
                    setResult(RESULT_CANCELED);
                else
                {
                    Intent i = getIntent();
                    i.putExtra("Link",link);
                    setResult(RESULT_OK,i);
                    finish();
                }
            }
        });
    }

    private void registerWidget() {
        editText_add_link = (EditText)findViewById(R.id.editText_add_link);
        button_add_link_save = (Button)findViewById(R.id.button_add_link_save);
    }
}
