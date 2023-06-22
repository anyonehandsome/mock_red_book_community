package hjy.com.red_book_community.community.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import hjy.com.red_book_community.R;
import hjy.com.red_book_community.community.activity.ContentActivity;
import hjy.com.red_book_community.community.bean.ArticleBean;
import hjy.com.red_book_community.utils.DateUtil;

import static android.content.Context.MODE_PRIVATE;

/**
 * "我的内容"页面适配器
 */
public class MyContentAdapter extends BaseAdapter {
    private Context context;
    private List<ArticleBean> itemList;
    private SharedPreferences sp;

    public MyContentAdapter(Context context, List<ArticleBean> itemList) {
        this.context = context;
        sp = context.getSharedPreferences("ownArticle",MODE_PRIVATE);
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        // 重用已存在的View
        if (convertView == null) {
            // 如果convertView为空，则创建新的布局
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item,
                    parent, false);
            // 初始化ViewHolder，并保存到convertView的Tag中
            viewHolder = new ViewHolder();
            viewHolder.image1 = convertView.findViewById(R.id.image_content_view);
            viewHolder.title = convertView.findViewById(R.id.own_title);
            viewHolder.likeNumber= convertView.findViewById(R.id.own_likeNumber);
            viewHolder.postTime = convertView.findViewById(R.id.own_postTime);
            convertView.setTag(viewHolder);
        } else {
            // 如果convertView已存在，则从Tag中获取ViewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 根据position获取对应的数据项
        final ArticleBean item = itemList.get(position);
        // 设置ImageView和TextView的内容
        viewHolder.image1.setBackground(item.getImageBean().getImage1());
        viewHolder.title.setText(item.getTitle());
        viewHolder.postTime.setText(DateUtil.converString(item.getPostTime()));
        viewHolder.likeNumber.setText(String.valueOf(item.getLikeNumber()));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("noteMsg",item.getId());
                editor.commit();
                Intent intent = new Intent(context, ContentActivity.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    // ViewHolder用于缓存View的子组件，避免重复查找
    private static class ViewHolder {
        ImageView image1;
        TextView postTime;
        TextView title;
        TextView likeNumber;
    }

}
