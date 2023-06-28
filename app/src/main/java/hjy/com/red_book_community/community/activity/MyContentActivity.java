package hjy.com.red_book_community.community.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import hjy.com.red_book_community.R;
import hjy.com.red_book_community.community.adapter.MyContentAdapter;
import hjy.com.red_book_community.community.bean.ArticleBean;
import hjy.com.red_book_community.community.service.ArticleService;

public class MyContentActivity extends Activity {
    ListView listView;
    ImageView back;
    ImageView remove;
    private ArticleService articleService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        articleService = new ArticleService(this);
        setContentView(R.layout.activity_my_content);
        init();
    }

    private void init() {
        final ArrayList<ArticleBean> itemList = articleService.queryOwnArticleCover();
        listView = findViewById(R.id.own_content);
        back = findViewById(R.id.back);
        remove = findViewById(R.id.remove);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 检查check
                for (int i = itemList.size()- 1; i >= 0; i--) {
                    if (itemList.get(i).isChecked()) {
                        if (articleService.deleteData(itemList.get(i).getId())) {
                            itemList.remove(i);
                            recreate();
                        }
                    }
                }
                Toast.makeText(v.getContext(), "删除成功", Toast.LENGTH_SHORT).show();
            }
        });
        MyContentAdapter adapter = new MyContentAdapter(this,itemList);
        listView.setAdapter(adapter);
    }
}
