package hjy.com.red_book_community.user.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import hjy.com.red_book_community.R;
import hjy.com.red_book_community.community.activity.EditActivity;
import hjy.com.red_book_community.community.activity.HomeActivity;
import hjy.com.red_book_community.community.activity.MyContentActivity;
import hjy.com.red_book_community.user.bean.UserBean;
import hjy.com.red_book_community.user.service.UserService;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int PICK_IMAGE_REQUEST = 1;
    EditText name;
    EditText age;
    EditText phone;
    EditText sex;
    ImageView avatar;
    ImageView to_edit_1;
    ImageView home_page_2;
    ImageView me_1;
    ImageView tick_1;
    Button update_info;
    Button change_head;
    RelativeLayout tuichu;
    RelativeLayout my_content;
    private UserService userService;
    private UserBean userBean;
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
        update_info = findViewById(R.id.update_info);
        tuichu = findViewById(R.id.tuichu);
        change_head = findViewById(R.id.change_head);
        my_content = findViewById(R.id.my_content);
        my_content.setOnClickListener(this);
        change_head.setOnClickListener(this);
        tuichu.setOnClickListener(this);
        update_info.setOnClickListener(this);
        to_edit_1.setOnClickListener(this);
        home_page_2.setOnClickListener(this);
        int id = sp.getInt("id", 0);
        UserBean userBean = userService.queryById(id);
        this.age.setText(String.valueOf(userBean.getAge()));
        this.name.setText(userBean.getName());
        this.phone.setText(userBean.getPhone());
        this.sex.setText(userBean.getSex());
        this.avatar.setImageBitmap(userBean.getAvatar());
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
            case R.id.update_info:
                int age = Integer.parseInt(this.age.getText().toString());
                String sex = this.sex.getText().toString();
                String name = this.name.getText().toString();
                String phone = this.phone.getText().toString();
                userBean = new UserBean(name,sex,age,phone);
                if(userService.updateInfo(userBean)){
                    Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tuichu:
                intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                Toast.makeText(this, "退出账号成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.change_head:
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,PICK_IMAGE_REQUEST);
                break;
            case R.id.my_content:
                intent = new Intent(this,MyContentActivity.class);
                startActivity(intent);
                break;
        }
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            // 获取选择的图片URI
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                avatar.setImageBitmap(bitmap);
                if (userService.updateAvatar(bitmap)) {
                    Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
