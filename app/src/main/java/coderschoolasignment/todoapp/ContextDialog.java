package coderschoolasignment.todoapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import coderschoolasignment.todoapp.SqliteOpenHelper.SqlOpenHelper;

/**
 * Created by Administrator on 25/02/2016.
 */
public class ContextDialog extends DialogFragment implements View.OnClickListener {
    SqlOpenHelper helper;
    private Button button_context_dialog_done;
    private Button button_context_dialog_delete;
    private Note note;
    ContextDialogListener listener;
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_context_dialog_done:
            {
                if(note.getStatus()==1)
                {
                    note.setStatus(2);
                    helper.UpdateNote(note);
                }else {
                    note.setStatus(1);
                    helper.UpdateNote(note);
                }
                listener = (ContextDialogListener) getActivity();
                listener.onDoneClickListener(note);
                getDialog().dismiss();
            }break;
            case R.id.button_context_dialog_delete:
            {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setTitle("Delete Note");
                builder.setMessage("Do you really want to delete this note?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                helper.deleteNote(note.getId());
                                listener = (ContextDialogListener) getActivity();
                                listener.onDeleteClickListener(note);
                                getDialog().dismiss();
                    }
                }).create();
                builder.show();





            }break;
        }
    }

    public interface ContextDialogListener
    {
        void onDoneClickListener(Note note);
        void onDeleteClickListener(Note note);
    }
    public ContextDialog() {
    }
    public static ContextDialog newInstance(String dialogTitle,Note note)
    {
        ContextDialog contextDialog = new ContextDialog();
        Bundle b = new Bundle();
        b.putString("dialogTitle",dialogTitle);
        b.putSerializable("note",note);
        contextDialog.setArguments(b);
        return contextDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.context_dialog,container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        helper = new SqlOpenHelper(getContext());

        String dialogTitle = getArguments().getString("dialogTitle");
        getDialog().setTitle(dialogTitle);

        note = (Note) getArguments().getSerializable("note");
        if(note.getStatus()==1)
        {
            button_context_dialog_done = (Button)view.findViewById(R.id.button_context_dialog_done);
            button_context_dialog_done.setText("I HAVE DONE THIS #THING");
            button_context_dialog_done.setOnClickListener(this);
        }else if (note.getStatus()==2)
        {
            button_context_dialog_done = (Button)view.findViewById(R.id.button_context_dialog_done);
            button_context_dialog_done.setText("NO,I HAVEN'T DONE THIS #THING");
            button_context_dialog_done.setOnClickListener(this);
        }
        button_context_dialog_delete = (Button)view.findViewById(R.id.button_context_dialog_delete);
        button_context_dialog_delete.setOnClickListener(this);

    }
}
