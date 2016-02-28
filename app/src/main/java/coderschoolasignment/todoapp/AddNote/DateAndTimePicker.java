package coderschoolasignment.todoapp.AddNote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import coderschoolasignment.todoapp.R;

public class DateAndTimePicker extends AppCompatActivity {
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button button_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_and_time_picker);

        datePicker = (DatePicker) findViewById(R.id.datePicker_deadline);
        timePicker = (TimePicker) findViewById(R.id.timePicker_deadline);

        Calendar c = Calendar.getInstance();
        datePicker.updateDate(c.YEAR, c.MONTH, c.DAY_OF_MONTH);
        datePicker.setMinDate(c.getTimeInMillis());

        timePicker.is24HourView();
        timePicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));


        button_save = (Button) findViewById(R.id.button_datetime_picker_save);


        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = datePicker.getYear();
                int month = datePicker.getMonth() + 1;
                int dayofmonth = datePicker.getDayOfMonth();
                String date = year + "-" + month + "-" + dayofmonth;

                Calendar c = Calendar.getInstance();
                Time t = new Time();
                t.setToNow();

                if (t.year == year && t.month + 1 == month && t.monthDay == dayofmonth && compareTime(
                        timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute(),
                        t.hour,
                        t.minute) == 0) {
                    Toast.makeText(DateAndTimePicker.this, "Please set Deadline with the Future Time", Toast.LENGTH_SHORT).show();
                } else {
                    String time = pad(timePicker.getCurrentHour()) + ":" + pad(timePicker.getCurrentMinute()) + ":00";
                    Intent i = getIntent();
                    i.putExtra("Deadline", (date + " " + time).trim());
                    setResult(RESULT_OK, i);
                    finish();
                }


            }
        });


    }

    private int compareTime(int hourA, int minuteA, int hourB, int minuteB) {
        if (hourA > hourB)
            return 1;// 1:A later,0:A sooner
        else if (hourA == hourB && minuteA > minuteB)
            return 1;
        else if (hourA == hourB && minuteB == minuteA)
            return 2;
        else
            return 0;
    }

    private String pad(int value) {

        if (value < 10) {
            return "0" + value;
        }
        return "" + value;
    }


}
