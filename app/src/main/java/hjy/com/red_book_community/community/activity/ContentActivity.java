package hjy.com.red_book_community.community.activity;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import hjy.com.red_book_community.R;
import hjy.com.red_book_community.community.adapter.ImageAdapter;
import hjy.com.red_book_community.community.bean.ArticleBean;
import hjy.com.red_book_community.community.service.ImageService;
import hjy.com.red_book_community.utils.DateUtil;

/**
 * 笔记内容
 */
public class ContentActivity extends AppCompatActivity {
    ViewPager mViewPager;
    LinearLayout mIndicatorLayout;
    ImageAdapter mAdapter;
    TextView innerName;
    ImageView innerHead;
    ImageView back;
    private ImageService imageService;
    private ArticleBean articleBean;
    private TextView title;
    private TextView content;
    private TextView innerPostTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        init();
        addIndicators();
        autoScroll();
    }

    @SuppressLint("NewApi")
    private void init() {
        this.imageService = new ImageService(this);
        this.innerName = (TextView) findViewById(R.id.innerName);
        this.innerHead = (ImageView) findViewById(R.id.innerHead);
        this.back = (ImageView) findViewById(R.id.back);
        this.title = (TextView) findViewById(R.id.innerTitle);
        this.content = (TextView) findViewById(R.id.innerContent);
        this.innerPostTime = (TextView) findViewById(R.id.innerPostTime);
        this.mViewPager = (ViewPager) findViewById(R.id.viewPager);
        this.mIndicatorLayout = findViewById(R.id.indicatorLayout);
        this.articleBean = this.imageService.query();
        this.innerName.setText(articleBean.getWriterName());
        this.content.setText(articleBean.getContent());
        this.title.setText(articleBean.getTitle());
        this.innerPostTime.setText(DateUtil.converString(articleBean.getPostTime()));
        this.innerHead.setBackground(articleBean.getWriterAvatar());
        this.mAdapter = new ImageAdapter(this,articleBean.getImageBean());
        this.mViewPager.setAdapter(mAdapter);
        // 设置返回键
        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    // 添加指示点或指示条
    private void addIndicators() {
        for (int i = 0; i < articleBean.getImageBean().getCount(); i++) {
            View indicator = new View(this);
            int size = getResources().getDimensionPixelSize(R.dimen.indicator_size);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            params.setMargins(10, 0, 10, 0);
            indicator.setLayoutParams(params);
            indicator.setBackgroundResource(R.drawable.indicator_bg);
            mIndicatorLayout.addView(indicator);
        }
    }

    // 自动轮播
    private void autoScroll() {
        final int numPages = mAdapter.getCount();
        final long delayMs = 3000; // 轮播间隔时间，单位：毫秒

        Runnable runnable = new Runnable() {
            int currPage = 0;
            @Override
            public void run() {
                mViewPager.setCurrentItem(currPage, true);
                currPage = (currPage + 1) % numPages;
                mViewPager.postDelayed(this, delayMs);
            }
        };

        mViewPager.postDelayed(runnable, delayMs);
    }
}


