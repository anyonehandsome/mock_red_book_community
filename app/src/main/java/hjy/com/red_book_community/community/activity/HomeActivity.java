package hjy.com.red_book_community.community.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import java.util.List;

import hjy.com.red_book_community.R;
import hjy.com.red_book_community.community.adapter.HomeAdapter;
import hjy.com.red_book_community.community.bean.FontEndBean;
import hjy.com.red_book_community.community.recycleItem.Space_item;
import hjy.com.red_book_community.community.service.FontEndService;
import hjy.com.red_book_community.user.activity.InfoActivity;

/**
 * 主页
 */
public class HomeActivity extends Activity implements View.OnClickListener {
    RecyclerView listView;
    ImageView to_edit_1;
    ImageView community_1;
    ImageView me_1;
    ImageView tick_1;
    private FontEndService fontEndService;
    private List<FontEndBean> fontEndList;
    private HomeAdapter homeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initData();
        showQueryData();
    }

    @SuppressLint("WrongViewCast")
    private void initData() {
        this.fontEndService = new FontEndService(this);
        listView = (RecyclerView) findViewById(R.id.home_item);
        to_edit_1 = (ImageView) findViewById(R.id.to_edit_1);
        community_1 = (ImageView) findViewById(R.id.community_1);
        me_1 = (ImageView) findViewById(R.id.me_1);
        tick_1 = (ImageView) findViewById(R.id.tick_1);
        to_edit_1.setOnClickListener(this);
        community_1.setOnClickListener(this);
        me_1.setOnClickListener(this);
        tick_1.setOnClickListener(this);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
        listView.addItemDecoration(new Space_item(5));
    }

    private void showQueryData() {
        if (fontEndList != null) {
            fontEndList.clear();
        }
        fontEndList = fontEndService.query();
        homeAdapter = new HomeAdapter(fontEndList);
        listView.setAdapter(homeAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.startActivityForResult(intent, requestCode);
        if (requestCode == 1 && resultCode == 2) {
            showQueryData();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.to_edit_1:
                intent = new Intent(HomeActivity.this, EditActivity.class);
                startActivity(intent);
                break;
            case R.id.community_1:
                intent = new Intent(HomeActivity.this, EditActivity.class);
                startActivity(intent);
                break;
            case R.id.me_1:
                intent = new Intent(HomeActivity.this, InfoActivity.class);
                startActivity(intent);
                break;
            case R.id.tick_1:
                intent = new Intent(HomeActivity.this, EditActivity.class);
                startActivity(intent);
                break;
        }
    }
}