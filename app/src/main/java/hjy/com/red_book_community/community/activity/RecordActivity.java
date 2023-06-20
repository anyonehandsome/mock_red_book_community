package hjy.com.red_book_community.community.activity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import hjy.com.red_book_community.R;
import hjy.com.red_book_community.community.service.ArticleService;
import hjy.com.red_book_community.utils.MyHelper;

public class RecordActivity extends Activity implements View.OnClickListener {
    private ArticleService articleService;
    ImageView note_back;
    TextView note_time;
    EditText title;
    EditText content;
    ImageView delete;
    ImageView note_save;
    MyHelper mSQLiteHelper;
    TextView noteName;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        note_back = (ImageView) findViewById(R.id.note_back);
        note_time = (TextView) findViewById(R.id.tv_time);
        title = (EditText) findViewById(R.id.note_title);
        content = (EditText) findViewById(R.id.note_content);
        delete = (ImageView) findViewById(R.id.delete);
        note_save = (ImageView) findViewById(R.id.note_save);
        noteName = (TextView) findViewById(R.id.note_name);
        note_back.setOnClickListener(this);
        delete.setOnClickListener(this);
        note_save.setOnClickListener(this);
        initData();
    }

    protected void initData() {
        mSQLiteHelper = new MyHelper(this);
        articleService = new ArticleService(this);
        noteName.setText("添加记录");
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            if (id != null) {
                noteName.setText("修改记录");
                content.setText(intent.getStringExtra("content"));
                note_time.setText(intent.getStringExtra("time"));
                note_time.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.note_back:
                finish();
                break;
            case R.id.delete:
                title.setText("");
                content.setText("");
                break;
            case R.id.note_save:
                String noteContent = content.getText().toString().trim();
                String title = content.getText().toString().trim();
                if (id != null) {//修改操作
                    if (noteContent.length() > 0) {
                        if (articleService.updateData(id, noteContent,title)) {
                            showToast("修改成功");
                            setResult(2);
                            finish();
                        } else {
                            showToast("修改失败");
                        }
                    } else {
                        showToast("修改内容不能为空!");
                    }
                } else {
                    //向数据库中添加数据
                    if (noteContent.length() > 0) {
                        if (articleService.insertData(title,noteContent)) {
                            showToast("保存成功");
                            setResult(2);
                            finish();
                        } else {
                            showToast("保存失败");
                        }
                    } else {
                        showToast("修改内容不能为空!");
                    }
                }
                break;
        }
    }

    public void showToast(String message) {
        Toast.makeText(RecordActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}

