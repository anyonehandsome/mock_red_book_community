package hjy.com.red_book_community.user.activity;

import android.content.Intent;
import android.os.Bundle;;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hjy.com.red_book_community.R;
import hjy.com.red_book_community.user.service.UserService;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText et_account;
    private EditText et_password;
    private Button btn_register;
    private TextView to_login;
    private UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }
    private void initView() {
        userService = new UserService(this);
        et_account = (EditText) findViewById(R.id.account);
        et_password = (EditText) findViewById(R.id.password);
        btn_register = (Button) findViewById(R.id.btn_register);
        to_login = (TextView) findViewById(R.id.to_Login);
        //设置按钮的点击监听事件
        btn_register.setOnClickListener(this);
        to_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                String name = et_account.getText().toString().trim();
                String password = et_password.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(userService.isRegister(name,password)){
                    Intent intent = new Intent(this,LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.to_Login:
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
