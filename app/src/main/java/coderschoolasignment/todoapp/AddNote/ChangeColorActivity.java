package coderschoolasignment.todoapp.AddNote;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import coderschoolasignment.todoapp.Colours;
import coderschoolasignment.todoapp.R;
import coderschoolasignment.todoapp.SharedPreference;

public class ChangeColorActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView_color1;
    private ImageView imageView_color2;
    private ImageView imageView_color3;
    private ImageView imageView_color4;
    private String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_color);

        int width = Integer.parseInt(SharedPreference.ReadFromSharedPreference(this,"DEVICE_SCREEN_WIDTH","").toString());
        int height = Integer.parseInt(SharedPreference.ReadFromSharedPreference(this,"DEVICE_SCREEN_HEIGHT","").toString());

        imageView_color1 = (ImageView)findViewById(R.id.imageView_changecolor_1);
        imageView_color2 = (ImageView)findViewById(R.id.imageView_changecolor_2);
        imageView_color3 = (ImageView)findViewById(R.id.imageView_changecolor_3);
        imageView_color4 = (ImageView)findViewById(R.id.imageView_changecolor_4);

        int imageWidth = ((width-100)/4);
        int imageHeight = imageWidth;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(imageWidth,imageHeight);

        imageView_color1.setLayoutParams(layoutParams);
        imageView_color2.setLayoutParams(layoutParams);
        imageView_color3.setLayoutParams(layoutParams);
        imageView_color4.setLayoutParams(layoutParams);

        imageView_color1.setPadding(5,5,5,5);
        imageView_color2.setPadding(0,5,5,5);
        imageView_color3.setPadding(0,5,5,5);
        imageView_color4.setPadding(0,5,5,5);

        imageView_color1.setBackgroundColor(Color.parseColor(Colours.getColor1()));
        imageView_color2.setBackgroundColor(Color.parseColor(Colours.getColor2()));
        imageView_color3.setBackgroundColor(Color.parseColor(Colours.getColor3()));
        imageView_color4.setBackgroundColor(Color.parseColor(Colours.getColor4()));

        imageView_color1.setOnClickListener(this);
        imageView_color2.setOnClickListener(this);
        imageView_color3.setOnClickListener(this);
        imageView_color4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imageView_changecolor_1:
            {
                imageView_color1.setBackgroundColor(Color.parseColor(Colours.getColor1_shadow1()));
                result = Colours.getColor1();

            }break;

            case R.id.imageView_changecolor_2:
            {
                imageView_color2.setBackgroundColor(Color.parseColor(Colours.getColor1_shadow2()));
                result = Colours.getColor2();
            }break;

            case R.id.imageView_changecolor_3:
            {
                imageView_color3.setBackgroundColor(Color.parseColor(Colours.getColor1_shadow3()));
                result = Colours.getColor3();
            }break;

            case R.id.imageView_changecolor_4:
            {
                imageView_color4.setBackgroundColor(Color.parseColor(Colours.getColor1_shadow4()));
                result = Colours.getColor4();
            }break;
        }


        if (result==null)
        {
            setResult(RESULT_CANCELED);
        }
        else
        {
            Intent i = getIntent();
            i.putExtra("Color",result);
            setResult(RESULT_OK,i);
        }

        finish();

    }
}
