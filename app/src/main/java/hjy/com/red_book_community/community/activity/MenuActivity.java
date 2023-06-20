package hjy.com.red_book_community.community.activity;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;

import hjy.com.red_book_community.R;
import hjy.com.red_book_community.community.adapter.ArticleAdapter;
import hjy.com.red_book_community.community.bean.ArticleBean;
import hjy.com.red_book_community.community.service.ArticleService;

public class MenuActivity extends Activity {
    ListView listView;
    List<ArticleBean> list;
    ArticleAdapter adapter;
    ImageView add;
    private ArticleService articleService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initData();
    }

    protected void initData() {
        articleService = new ArticleService(this);
        listView = (ListView) findViewById(R.id.listview);
        add = (ImageView) findViewById(R.id.add);
        showQueryData();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,
                        RecordActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArticleBean articleBean = list.get(position);
                Intent intent = new Intent(MenuActivity.this,
                        RecordActivity.class);
                intent.putExtra("id", articleBean.getId());
                intent.putExtra("time", articleBean.getPostTime());
                intent.putExtra("content", articleBean.getContent());
                MenuActivity.this.startActivityForResult(intent, 1);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int
                    position, long id) {
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this)
                        .setMessage("是否删除此事件？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ArticleBean articleBean = list.get(position);
                                if (articleService.deleteData(articleBean.getId())) {
                                    list.remove(position);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(MenuActivity.this, "删除成功",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                dialog = builder.create();
                dialog.show();
                return true;
            }
        });
    }

    private void showQueryData() {
        if (list != null) {
            list.clear();
        }
        list = articleService.query();
        adapter = new ArticleAdapter(this, list);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            showQueryData();
        }
    }
}
