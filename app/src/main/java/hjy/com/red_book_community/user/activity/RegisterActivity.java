package hjy.com.red_book_community.user.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hjy.com.red_book_community.R;
import hjy.com.red_book_community.user.service.UserService;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText et_account;
    private EditText et_password;
    private Button btn_register;
    private TextView to_login;
    private UserService userService;
    private ImageView first_head;
    private Bitmap bitmap;
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
        first_head = (ImageView) findViewById(R.id.first_head);
        //设置按钮的点击监听事件
        btn_register.setOnClickListener(this);
        to_login.setOnClickListener(this);
        first_head.setOnClickListener(this);
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
                if(userService.isRegister(name,password,bitmap )){
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
            case R.id.first_head:
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent,PICK_IMAGE_REQUEST);
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
                // 使用BitmapFactory解码URI为Bitmap对象
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                // 将Bitmap设置到ImageView中显示
                first_head.setImageBitmap(bitmap);
                Toast.makeText(this, "头像初始化成功", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
