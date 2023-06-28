package hjy.com.red_book_community.user.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hjy.com.red_book_community.R;
import hjy.com.red_book_community.community.activity.HomeActivity;
import hjy.com.red_book_community.user.service.UserService;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private UserService userService;
    private EditText et_account;   //账号输入框
    private EditText et_password;  //密码输入框
    private Button btn_login;      //登录按钮
    private TextView to_Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        userService = new UserService(this);
        et_account = (EditText) findViewById(R.id.et_account);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        to_Register = (TextView) findViewById(R.id.to_Register);
        //设置按钮的点击监听事件
        btn_login.setOnClickListener(this);
        to_Register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                //当点击登录按钮时，获取界面上输入的QQ账号和密码
                String account = et_account.getText().toString().trim();
                String password = et_password.getText().toString();
                //检验输入的账号和密码是否为空
                if (TextUtils.isEmpty(account)) {
                    Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userService.isLoign(account,password)) {
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this,HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "没有账号，请先注册", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this,RegisterActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.to_Register:
                Intent intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}