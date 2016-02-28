package coderschoolasignment.todoapp.AddNote;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import coderschoolasignment.todoapp.Colours;
import coderschoolasignment.todoapp.Note;
import coderschoolasignment.todoapp.R;
import coderschoolasignment.todoapp.SqliteOpenHelper.SqlOpenHelper;

public class AddNoteActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText editText_title, editText_content;
    private Button button_reset, button_save;
    private SqlOpenHelper helper = new SqlOpenHelper(this);
    private static final int START_CHANGE_COLOR = 100;
    private static final int START_CHANGE_DEADLINE = 101;
    private static final int START_CHANGE_LINK = 102;
    private static final int START_CHANGE_IMAGEDIR = 102;
    private LinearLayout linearLayout;
    private TableRow tableRow_link;

    private TableRow tableRow_deadline;
    private TextView textView_deadline;

    private ImageView imageView_add_note_image;

    //ListView
    private ArrayList<String> arrLinks;
    private ArrayAdapter<String> adapter;
    private ListView listView;

    //imagepicker
    private Uri mImageCaptureUri;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_FILE = 2;

    //NEW NOTE HEREEEEEEEEEEEEEEEEE
    private String imageDir = "";
    private String color = Colours.getColor1();


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
                String title, content;
                title = editText_title.getText().toString().trim();
                content = editText_content.getText().toString().trim();
                if (content.length() == 0) {
                    Toast.makeText(AddNoteActivity.this, "Please Insert Content", Toast.LENGTH_SHORT).show();
                } else {
                    //New Note
                    Note t = new Note();
                    //Title
                    t.setTitle(title);
                    //Content
                    t.setContent(content);
                    //Time
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String formattedDate = df.format(c.getTime());
                    t.setTime(formattedDate);

                    //Color
                    t.setColor(color);

                    //Status
                    t.setStatus(1);

                    //Deadline
                    if (textView_deadline.getText().toString().length() != 0)
                        t.setDeadline(textView_deadline.getText().toString().trim());

                    //ImagerDir
                    if (imageDir.trim().length() != 0)
                        t.setImageDir(imageDir);
                    //Link
                    StringBuilder link = new StringBuilder();
                    if (arrLinks.size() > 0) {
                        for (String s : arrLinks) {
                            link.append(s + " ");
                        }
                    }
                    t.setLink(link.toString());

                    helper.insertNote(t);

                    Intent i = getIntent();
                    setResult(Activity.RESULT_OK, i);
                    finish();

                }

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setBackgroundColor(Color.WHITE);
                String linkchosen = arrLinks.get(position);

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkchosen));
                startActivity(browserIntent);
            }
        });

        textView_deadline.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(AddNoteActivity.this).setTitle("Delete Deadline");
                builder.setMessage("Do you really want to delete this deadline time?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textView_deadline.setText("");
                    }
                }).create();
                builder.show();


                return false;
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(AddNoteActivity.this).setTitle("Delete Link");
                builder.setMessage("Do you really want to delete this link?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        arrLinks.remove(position);
                        adapter.notifyDataSetChanged();
                        if(arrLinks.size()==0)
                        {
                            tableRow_link.setVisibility(View.GONE);
                            listView.setVisibility(View.GONE);
                        }

                    }
                }).create();
                builder.show();
                return true;
            }
        });

        imageView_add_note_image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(AddNoteActivity.this).setTitle("Delete Image");
                builder.setMessage("Do you really want to delete this image?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        imageDir = "";
                        imageView_add_note_image.setVisibility(View.GONE);
                    }
                }).create();
                builder.show();
                return false;
            }
        });

    }

    private void registerWidget() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        imageView_add_note_image = (ImageView) findViewById(R.id.imageView_add_note_image);

        editText_title = (EditText) findViewById(R.id.editText_title);
        editText_content = (EditText) findViewById(R.id.editText_content);
        button_reset = (Button) findViewById(R.id.button_reset);
        button_save = (Button) findViewById(R.id.button_save);
        linearLayout = (LinearLayout) findViewById(R.id.add_note_background);
        tableRow_link = (TableRow) findViewById(R.id.tableRow_link);
        tableRow_deadline = (TableRow) findViewById(R.id.tableRow_deadline);
        textView_deadline = (TextView) findViewById(R.id.textview_add_note_deadline);

        arrLinks = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.black_link_for_textview, arrLinks);
        listView = (ListView) findViewById(R.id.listView_links);
        listView.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu_add_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_changeColor: {
                Intent i = new Intent(this, ChangeColorActivity.class);
                startActivityForResult(i, START_CHANGE_COLOR);
            }
            break;
            case R.id.menu_action_addLink: {
                Intent i = new Intent(this, AddNewLinkActivity.class);
                startActivityForResult(i, START_CHANGE_LINK);
            }
            break;
            case R.id.menu_action_addImage: {
                if (Build.VERSION.SDK_INT < 23 && Build.VERSION.SDK_INT >= 16) {
                    createImagerPicker();
                }
            }
            break;
            case R.id.menu_action_changeDeadline: {
                Intent i = new Intent(this, DateAndTimePicker.class);
                startActivityForResult(i, START_CHANGE_DEADLINE);
                /*DialogFragment datePickerFragment = new DatePickerFormat();
                datePickerFragment.show(getSupportFragmentManager(),"Date Picker");*/
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case START_CHANGE_COLOR: {
                if (resultCode == RESULT_OK) {
                    color = data.getStringExtra("Color");
                    linearLayout.setBackgroundColor(Color.parseColor(color));
                } else return;
            }
            break;
            case START_CHANGE_LINK: {
                if (resultCode == RESULT_OK) {
                    listView.setVisibility(View.VISIBLE);
                    String linkadded = data.getStringExtra("Link");
                    if (!linkadded.startsWith("http://") && !linkadded.startsWith("https://"))
                        linkadded = "http://" + linkadded;
                    arrLinks.add(linkadded);
                    adapter.notifyDataSetChanged();

                } else return;
            }
            break;
            case START_CHANGE_DEADLINE: {
                if (resultCode == RESULT_OK) {
                    String deadline = data.getStringExtra("Deadline");
                    textView_deadline.setVisibility(View.VISIBLE);
                    textView_deadline.setText(deadline);
                } else return;
            }
            break;
            case PICK_FROM_FILE: {
                if (resultCode != RESULT_CANCELED) {

                    String realPath;
                    if (Build.VERSION.SDK_INT < 19)
                        realPath = getRealPathFromURI_API11to18(this, data.getData());
                    else
                        realPath = getRealPathFromURI_API19(this, data.getData());

                    mImageCaptureUri = Uri.fromFile(new File(realPath));

                    try {
                        final InputStream imageStream = getContentResolver().openInputStream(mImageCaptureUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageView_add_note_image.setVisibility(View.VISIBLE);
                        imageView_add_note_image.setImageBitmap(selectedImage);
                        imageDir = realPath;

                    } catch (Exception e) {

                    }

                } else return;
            }
            break;
            /*case PICK_FROM_CAMERA: {
                if (resultCode == RESULT_OK) {

                    try {
                        final InputStream imageStream = getContentResolver().openInputStream(mImageCaptureUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                        imageView_add_note_image.setVisibility(View.VISIBLE);
                        imageView_add_note_image.setImageBitmap(selectedImage);
                        Toast.makeText(AddNoteActivity.this, imageDir, Toast.LENGTH_SHORT).show();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
            break;*/
        }
    }

    /*public static class DatePickerFormat extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        TableRow tableRow_deadline;
        TextView textView_deadline;
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar cal =Calendar.getInstance();
            int year  = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            textView_deadline = (TextView) getActivity().findViewById(R.id.textview_add_note_deadline);
            tableRow_deadline = (TableRow) getActivity().findViewById(R.id.tableRow_deadline);
            return new DatePickerDialog(getContext(),this,year,month,day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            tableRow_deadline.setVisibility(View.VISIBLE);
            textView_deadline.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
        }
    }*/

    private void createImagerPicker() {
                    Intent intent = new Intent();

                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String getRealPathFromURI_API19(Context context, Uri uri) {
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = {MediaStore.Images.Media.DATA};

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{id}, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if (cursor != null) {
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
        }
        return result;
    }


}
