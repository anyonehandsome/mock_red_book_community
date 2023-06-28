package hjy.com.red_book_community.community.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hjy.com.red_book_community.R;
import hjy.com.red_book_community.community.bean.ImageBean;
import hjy.com.red_book_community.community.service.ArticleService;
import hjy.com.red_book_community.utils.ImageUtils;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * 编辑笔记
 */
public class EditActivity extends Activity implements View.OnClickListener {
    private static final int PICK_IMAGE_REQUEST = 1;
    ImageView back_2;
    ImageView add_images;
    EditText edit_content;
    EditText edit_title;
    Button submit;
    ImageView image1;
    ImageView image2;
    ImageView image3;
    private ArticleService articleService;
    private ImageBean imageBean = new ImageBean();
    private List<Bitmap> bitmapList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        articleService = new ArticleService(this);
        init();
    }

    private void init() {
        add_images = findViewById(R.id.add_images);
        edit_content = findViewById(R.id.edit_content);
        edit_title = findViewById(R.id.edit_title);
        submit = findViewById(R.id.submit);
        image1 = findViewById(R.id.image_1);
        image2 = findViewById(R.id.image_2);
        image3 = findViewById(R.id.image_3);
        back_2 = findViewById(R.id.back_2);
        back_2.setOnClickListener(this);
        submit.setOnClickListener(this);
        add_images.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.back_2:
                onBackPressed();
                break;
            case R.id.add_images:
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
                break;
            case R.id.submit:
                String title = edit_title.getText().toString();
                String content = edit_content.getText().toString();
                if (imageBean.getImage1() == null) {
                    Toast.makeText(this, "请放入图片", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (articleService.insertData(title, content, imageBean)) {
                    Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
                    intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "发布失败，请重试", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            try {
                if (data!=null){
                    Uri uri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    bitmapList.add(bitmap);
                }
                // 对获取到的照片进行显示或处理
                if (bitmapList.size() > 0 && bitmapList.size()<2) {
                    image1.setImageBitmap(bitmapList.get(0));
                    imageBean.setImage1(bitmapList.get(0));
                    image1.setVisibility(VISIBLE);
                }
                if (bitmapList.size() > 1 && bitmapList.size()<3) {
                    image2.setImageBitmap(bitmapList.get(1));
                    imageBean.setImage2(bitmapList.get(1));
                    image2.setVisibility(VISIBLE);
                }
                if (bitmapList.size() > 2 && bitmapList.size()<=3) {
                    image3.setImageBitmap(bitmapList.get(2));
                    imageBean.setImage3(bitmapList.get(2));
                    image3.setVisibility(VISIBLE);
                    add_images.setVisibility(GONE);
                }else return;
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}