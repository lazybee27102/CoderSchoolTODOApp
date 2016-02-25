package coderschoolasignment.todoapp.RecycleView_NotesList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import coderschoolasignment.todoapp.Note;
import coderschoolasignment.todoapp.R;
import coderschoolasignment.todoapp.Colors;

/**
 * Created by Administrator on 23/02/2016.
 */
public class RecyclerViewAdapter_NotesList extends RecyclerView.Adapter<RecyclerViewAdapter_NotesList.MyNoteHolder> {
    private LayoutInflater layoutInflater;
    private ArrayList<Note> arrNotes;
    private Context context;

    public RecyclerViewAdapter_NotesList(Context context, ArrayList<Note> arrNotes) {
        this.context = context;
        this.arrNotes = arrNotes;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewAdapter_NotesList.MyNoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.note_item,null, false);
        MyNoteHolder viewHolder = new MyNoteHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter_NotesList.MyNoteHolder holder, int position) {
        Typeface font_softElegance = Typeface.createFromAsset(context.getAssets(),"fonts/softElegance.ttf");
        Note note = arrNotes.get(position);
        if (note.getTitle().trim().length()==0)
        {
            holder.cardView_top.setVisibility(View.GONE);
        }else
        {
            holder.textView_title.setText(note.getTitle());
            holder.textView_title.setTypeface(font_softElegance,Typeface.BOLD);
        }


        holder.textView_content.setText(note.getContent());
        holder.textView_content.setTypeface(font_softElegance);
        holder.textView_time.setText(note.getTime());
        holder.cardView.setBackgroundColor(Color.parseColor(note.getColor()));

        if(note.getStatus()==2)
        {
            holder.textView_title.setPaintFlags(holder.textView_title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.textView_content.setPaintFlags(holder.textView_content.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.textView_time.setPaintFlags(holder.textView_time.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    @Override
    public int getItemCount() {
        return arrNotes.size();
    }

    public class MyNoteHolder extends RecyclerView.ViewHolder {
        private TextView textView_title,textView_content,textView_time;

        public TextView getTextView_title() {
            return textView_title;
        }

        public TextView getTextView_content() {
            return textView_content;
        }

        public TextView getTextView_time() {
            return textView_time;
        }

        private LinearLayout cardView;
        private LinearLayout cardView_top;
        public MyNoteHolder(View itemView) {
            super(itemView);
            textView_title = (TextView) itemView.findViewById(R.id.note_item_title);
            textView_content = (TextView) itemView.findViewById(R.id.note_item_content);
            textView_time = (TextView) itemView.findViewById(R.id.note_item_time);
            cardView = (LinearLayout) itemView.findViewById(R.id.card_view);
            cardView_top = (LinearLayout) itemView.findViewById(R.id.cardview_top);

        }
    }
}
