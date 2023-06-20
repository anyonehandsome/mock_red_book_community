package hjy.com.red_book_community.user.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import hjy.com.red_book_community.R;
import hjy.com.red_book_community.community.activity.EditActivity;
import hjy.com.red_book_community.community.activity.HomeActivity;
import hjy.com.red_book_community.user.bean.UserBean;
import hjy.com.red_book_community.user.service.UserService;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener{
    EditText name;
    EditText age;
    EditText phone;
    EditText sex;
    ImageView avatar;
    ImageView to_edit_1;
    ImageView home_page_2;
    ImageView me_1;
    ImageView tick_1;
    private UserService userService;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        userService = new UserService(this);
        this.sp = getSharedPreferences("userInfo",MODE_PRIVATE);
        init();
    }

    @SuppressLint("NewApi")
    private void init() {
        this.age = (EditText) findViewById(R.id.age);
        this.name = (EditText) findViewById(R.id.userName);
        this.phone = (EditText) findViewById(R.id.phone);
        this.sex = (EditText) findViewById(R.id.sex);
        this.avatar = (ImageView) findViewById(R.id.iv_avatar);
        to_edit_1 = (ImageView) findViewById(R.id.to_edit_2);
        home_page_2 = (ImageView) findViewById(R.id.home_page_2);
        me_1 = (ImageView) findViewById(R.id.me_2);
        tick_1 = (ImageView) findViewById(R.id.tick_2);
        to_edit_1.setOnClickListener(this);
        home_page_2.setOnClickListener(this);
        int id = sp.getInt("id", 0);
        UserBean userBean = userService.queryById(id);
        this.age.setText(String.valueOf(userBean.getAge()));
        this.name.setText(userBean.getName());
        this.phone.setText(userBean.getPhone());
        this.sex.setText(userBean.getSex());
        this.avatar.setBackground(userBean.getAvatar());
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.home_page_2:
                intent = new Intent(InfoActivity.this, HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.to_edit_2:
                intent = new Intent(InfoActivity.this, EditActivity.class);
                startActivity(intent);
                break;
            case R.id.community_2:
                intent = new Intent(InfoActivity.this, EditActivity.class);
                startActivity(intent);
                break;
            case R.id.tick_2:
                intent = new Intent(InfoActivity.this, EditActivity.class);
                startActivity(intent);
                break;
        }
    }
}
