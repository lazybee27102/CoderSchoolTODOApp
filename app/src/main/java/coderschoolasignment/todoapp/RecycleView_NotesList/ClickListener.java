package coderschoolasignment.todoapp.RecycleView_NotesList;

import android.view.View;

/**
 * Created by Administrator on 25/02/2016.
 */
public interface ClickListener
{
    public void onClickViewHolder(View v, int position);
    public void onLongClickViewHolder(View v,int position);
}