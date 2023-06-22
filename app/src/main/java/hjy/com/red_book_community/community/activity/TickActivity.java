package hjy.com.red_book_community.community.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;
import hjy.com.red_book_community.R;

public class TickActivity extends Activity implements View.OnClickListener {

    CalendarView calendarView;
    Button btn_tick;
    ImageView back_3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tick);
        btn_tick = findViewById(R.id.btn_tick);
        back_3 = findViewById(R.id.back_3);
        calendarView = findViewById(R.id.calendar);
        back_3.setOnClickListener(this);
        btn_tick.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_tick:
                calendarView.setBackgroundColor(000);
                calendarView.setBackgroundResource(R.drawable.tick_selector);
                Toast.makeText(this, "签到成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.back_2:
                Intent intent = new Intent(this,HomeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
