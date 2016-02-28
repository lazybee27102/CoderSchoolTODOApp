package coderschoolasignment.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.style.StrikethroughSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import coderschoolasignment.todoapp.AddNote.AddNoteActivity;
import coderschoolasignment.todoapp.RecycleView_NotesList.ClickListener;
import coderschoolasignment.todoapp.RecycleView_NotesList.RecyclerViewAdapter_NotesList;
import coderschoolasignment.todoapp.RecycleView_NotesList.RecyclerView_TouchListener;
import coderschoolasignment.todoapp.SqliteOpenHelper.SqlOpenHelper;

public class MainActivity extends AppCompatActivity implements EditItemActivity.EditItemListener,ContextDialog.ContextDialogListener{
    public static final int START_ACTIVITY_ADD_NOTE  = 9999;


    private TextView textView_mainTitle;
    private TextView textView_mainTitle2;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private RecyclerViewAdapter_NotesList adapter_notesList;
    private RecyclerView recyclerView_notes;
    private ArrayList<Note> arrNotes;
    private Note LongClickNoteChose;
    private RecyclerViewAdapter_NotesList.MyNoteHolder myNoteHolder;
    private ContextDialog contextDialog;
    private EditItemActivity editItemActivity;
    private static final StrikethroughSpan STRIKE_THROUGH_SPAN = new StrikethroughSpan();
    //Sqlite
    SqlOpenHelper helper = new SqlOpenHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //save Device Sceen
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        SharedPreference.WritetoSharePreference(this,"DEVICE_SCREEN_WIDTH",displaymetrics.widthPixels +"");
        SharedPreference.WritetoSharePreference(this,"DEVICE_SCREEN_HEIGHT",displaymetrics.widthPixels +"");

        registerWidgets();
        setUpWidgets();



       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFB300")));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fab.setImageDrawable(getResources().getDrawable(R.mipmap.ic_mode_edit_white_48dp));
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,AddNoteActivity.class);

                startActivityForResult(i,START_ACTIVITY_ADD_NOTE);
            }
        });
    }

    private void setUpWidgets() {
        //TextView
        textView_mainTitle2.setTypeface(TypefaceChoser.getTypeface_aircraft(this));

        //RecycleView
        recyclerView_notes.setHasFixedSize(true);
        recyclerView_notes.setLayoutManager(staggeredGridLayoutManager);
        recyclerView_notes.setAdapter(adapter_notesList);

        recyclerView_notes.setPadding(5,5,5,5);
        recyclerView_notes.addOnItemTouchListener(new RecyclerView_TouchListener(MainActivity.this, recyclerView_notes, new ClickListener() {
            @Override
            public void onClickViewHolder(View v, int position) {
                Note n = helper.getAllNotes().get(position);
                ShowEditItemDialog(n);
            }

            @Override
            public void onLongClickViewHolder(View v, int position) {
                LongClickNoteChose = arrNotes.get(position);
                ShowContextDialog(LongClickNoteChose);
                myNoteHolder = (RecyclerViewAdapter_NotesList.MyNoteHolder) recyclerView_notes.findViewHolderForLayoutPosition(position);
            }
        }));


    }

    private void ShowContextDialog(Note longClickNoteChose) {
        contextDialog = ContextDialog.newInstance(longClickNoteChose.getTitle(),longClickNoteChose);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        contextDialog.show(fragmentManager,"LONG CLICK NOTE");
    }

    private void ShowEditItemDialog(Note n) {
        editItemActivity = EditItemActivity.newInstance("Edit Note",n);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        editItemActivity.show(fragmentManager,"CLICK NOTE");
    }

    private void registerWidgets() {
        //TextView
        textView_mainTitle = (TextView) findViewById(R.id.textView_mainTitle);
        textView_mainTitle2 = (TextView) findViewById(R.id.textView_mainTitle2);

        //ArrayList
        arrNotes = new ArrayList<>();
        arrNotes.clear();
        arrNotes.addAll(helper.getAllNotes());
        /*Note first = new Note("What is Lorem Ipsum?","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.","23/12/2016", Colours.getInstance());
        Note second = new Note("Why do we use it?","It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).","24/12/2016",Colours.getInstance());
        Note third = new Note("Why do we use it?","It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. ', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).","24/12/2016",Colours.getInstance());
        Note fourth = new Note("Where can I get some?","There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable.","27/02/2016",Colours.getInstance());
        arrNotes.add(first);
        arrNotes.add(second);
        arrNotes.add(third);
        arrNotes.add(fourth);

        arrNotes.add(first);
        arrNotes.add(second);
        arrNotes.add(third);
        arrNotes.add(fourth);

        arrNotes.add(first);
        arrNotes.add(second);
        arrNotes.add(third);
        arrNotes.add(fourth);*/

        //RecycleView
        recyclerView_notes = (RecyclerView)findViewById(R.id.recyclerview_notes);
        adapter_notesList = new RecyclerViewAdapter_NotesList(MainActivity.this,arrNotes);

        //StaggedGridLayoutManager
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==START_ACTIVITY_ADD_NOTE)
        {
            if(resultCode==Activity.RESULT_OK)
            {
                arrNotes = helper.getAllNotes();
                adapter_notesList = new RecyclerViewAdapter_NotesList(this,arrNotes);
                recyclerView_notes.setAdapter(adapter_notesList);
            }
        }
    }



    @Override
    public void onSaveClicked(Note n) {
        for(int i = 0;i<arrNotes.size();i++)
        {
            Note note = arrNotes.get(i);
            if(note.getId() == n.getId())
            {
                arrNotes.set(i,n);
                adapter_notesList.notifyItemChanged(i);
                break;
            }
        }
    }

    @Override
    public void onDoneClickListener(Note note) {
        if(note.getStatus()==2)
            UpdateUI(true);
        else
            UpdateUI(false);
    }

    private void UpdateUI(boolean b) {
        if(b)
        {
            myNoteHolder.getTextView_content().setPaintFlags(myNoteHolder.getTextView_content().getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            myNoteHolder.getTextView_title().setPaintFlags(myNoteHolder.getTextView_title().getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            myNoteHolder.getTextView_time().setPaintFlags(myNoteHolder.getTextView_time().getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else
        {
            myNoteHolder.getTextView_content().setPaintFlags(myNoteHolder.getTextView_content().getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            myNoteHolder.getTextView_title().setPaintFlags(myNoteHolder.getTextView_title().getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            myNoteHolder.getTextView_time().setPaintFlags(myNoteHolder.getTextView_time().getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    @Override
    public void onDeleteClickListener(Note note) {
        for(int i = 0;i<arrNotes.size();i++)
        {
            Note n = arrNotes.get(i);
            if(n.getId() == note.getId())
            {
                arrNotes.remove(i);
                adapter_notesList.notifyItemRemoved(i);
                break;
            }
        }

    }
}
