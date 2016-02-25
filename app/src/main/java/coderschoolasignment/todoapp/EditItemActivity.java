package coderschoolasignment.todoapp;

import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import coderschoolasignment.todoapp.SqliteOpenHelper.SqlOpenHelper;

public class EditItemActivity extends DialogFragment implements Button.OnClickListener{
    private EditText editText_edit_item_title;
    private EditText editText_edit_item_content;
    private Button button;
    private SqlOpenHelper helper;
    private Note n;

    public EditItemActivity() {

    }

    EditItemListener listener;
    @Override
    public void onClick(View v) {
        String title = editText_edit_item_title.getText().toString().trim();
        String content = editText_edit_item_content.getText().toString().trim();
        if(content.length()==0 && title.length()==0)
        {
            Toast.makeText(getContext(), "I will delete it", Toast.LENGTH_SHORT).show();
        }else if(content.length()==0 && title.length()!=0)
        {
            Toast.makeText(getContext(), "Please Insert the Content", Toast.LENGTH_SHORT).show();
        }else
        {
            n.setTitle(title);
            n.setContent(content);
            helper.UpdateNote(n);

            listener = (EditItemListener)getActivity();
            listener.onSaveClicked(n);
            getDialog().dismiss();
        }


    }


    public interface EditItemListener{
        void onSaveClicked(Note n);
    }

    public static EditItemActivity newInstance(String dialogTitle,Note n)
    {
        EditItemActivity editItemActivity = new EditItemActivity();
        Bundle arg = new Bundle();
        arg.putString("dialogTitle",dialogTitle);
        arg.putSerializable("note",n);
        editItemActivity.setArguments(arg);
        return editItemActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_edit_item,container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        helper = new SqlOpenHelper(getContext());

        editText_edit_item_title = (EditText)view.findViewById(R.id.editText_edit_item_title);
        editText_edit_item_content = (EditText)view.findViewById(R.id.editText_edit_item_content);
        button = (Button) view.findViewById(R.id.button_edit_item_save);




        String dialogTitle = getArguments().getString("dialogTitle","");
        getDialog().setTitle(dialogTitle);

        n = (Note) getArguments().getSerializable("note");
        editText_edit_item_title.setText(n.getTitle());
        editText_edit_item_content.setText(n.getContent());
        editText_edit_item_content.setTypeface(TypefaceChoser.getTypeface_softElegance(getContext()));
        editText_edit_item_title.setTypeface(TypefaceChoser.getTypeface_softElegance(getContext()));

        button.setOnClickListener(this);
    }
}
