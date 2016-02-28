package coderschoolasignment.todoapp.RecycleView_NotesList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import coderschoolasignment.todoapp.Note;
import coderschoolasignment.todoapp.R;
import coderschoolasignment.todoapp.SharedPreference;
import coderschoolasignment.todoapp.TypefaceChoser;

import static android.widget.LinearLayout.*;

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
        View view = layoutInflater.inflate(R.layout.note_item, null, false);
        MyNoteHolder viewHolder = new MyNoteHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyNoteHolder holder, int position) {

        Note note = arrNotes.get(position);
        if (note.getTitle().trim().length() == 0) {
            holder.cardView_top.setVisibility(View.GONE);
        } else {
            holder.textView_title.setText(note.getTitle());
            holder.textView_title.setTypeface(TypefaceChoser.getTypeface_softElegance(context), Typeface.BOLD);
        }


        holder.textView_content.setText(note.getContent());
        holder.textView_content.setTypeface(TypefaceChoser.getTypeface_softElegance(context));
        holder.textView_time.setText("Created: " + note.getTime());
        holder.cardView.setBackgroundColor(Color.parseColor(note.getColor()));

        if (note.getStatus() == 2) {
            holder.textView_title.setPaintFlags(holder.textView_title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.textView_content.setPaintFlags(holder.textView_content.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.textView_time.setPaintFlags(holder.textView_time.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        if (note.getDeadline().length() != 0) {
            holder.textView_deadline.setVisibility(View.VISIBLE);
            holder.textView_deadline.setText("Deadline: " + note.getDeadline());
        }

        if (note.getLink().length() != 0) {
            holder.textView_link.setVisibility(View.VISIBLE);
            holder.textView_link.setText("Your Link: ");
            String[] arr = note.getLink().split(" ");
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].trim().length() != 0) {
                    if (i != arr.length - 1) {
                        holder.textView_link.append(arr[i] + "\n");
                    } else
                        holder.textView_link.append(arr[i]);

                }
            }
        }
        if (note.getImageDir().toString().length() != 0) {
            int width = Integer.parseInt(SharedPreference.ReadFromSharedPreference(context,"DEVICE_SCREEN_WIDTH","").toString());
            int height = Integer.parseInt(SharedPreference.ReadFromSharedPreference(context,"DEVICE_SCREEN_HEIGHT","").toString());

            try {
                Uri mImageCaptureUri = Uri.fromFile(new File(note.getImageDir()));
                InputStream imageStream = context.getContentResolver().openInputStream(mImageCaptureUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                holder.imageView.setVisibility(View.VISIBLE);

                int size  = width/2 -100;
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size,size);
                layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                holder.imageView.setLayoutParams(layoutParams);
                holder.imageView.setImageBitmap(selectedImage);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return arrNotes.size();
    }

    public class MyNoteHolder extends RecyclerView.ViewHolder {
        private TextView textView_title, textView_content, textView_time, textView_deadline, textView_link;
        private ImageView imageView;

        public TextView getTextView_deadline() {
            return textView_deadline;
        }

        public TextView getTextView_link() {
            return textView_link;
        }

        public ImageView getImageView() {
            return imageView;
        }

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
            textView_deadline = (TextView) itemView.findViewById(R.id.note_item_deadline);
            textView_link = (TextView) itemView.findViewById(R.id.note_item_link);
            imageView = (ImageView) itemView.findViewById(R.id.imageView_note_item_image);

        }
    }
}
