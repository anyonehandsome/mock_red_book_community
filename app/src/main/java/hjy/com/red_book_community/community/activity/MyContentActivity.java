package hjy.com.red_book_community.community.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;
import hjy.com.red_book_community.R;
import hjy.com.red_book_community.community.adapter.MyContentAdapter;
import hjy.com.red_book_community.community.bean.ArticleBean;
import hjy.com.red_book_community.community.service.ArticleService;

public class MyContentActivity extends Activity {
    ListView listView;
    ImageView back;
    private ArticleService articleService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        articleService = new ArticleService(this);
        setContentView(R.layout.activity_my_content);
        init();
    }

    private void init() {
        listView = findViewById(R.id.own_content);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ArrayList<ArticleBean> itemList = articleService.queryOwnArticle();
        MyContentAdapter adapter = new MyContentAdapter(this,itemList);
        listView.setAdapter(adapter);
    }
}
