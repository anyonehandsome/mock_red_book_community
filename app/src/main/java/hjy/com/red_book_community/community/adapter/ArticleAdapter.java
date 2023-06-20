package hjy.com.red_book_community.community.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hjy.com.red_book_community.R;
import hjy.com.red_book_community.community.bean.ArticleBean;

public class ArticleAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<ArticleBean> list;
    public ArticleAdapter(Context context, List<ArticleBean> list){
        this.layoutInflater=LayoutInflater.from(context);
        this.list=list;
    }
    @Override
    public int getCount() {
        return list==null ? 0 : list.size();
    }
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=layoutInflater.inflate(R.layout.activity_record_item,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        ArticleBean articleBean=(ArticleBean) getItem(position);
        viewHolder.tvTitle.setText(articleBean.getTitle());
        viewHolder.tvCreateTime.setText(articleBean.getPostTime().toString());
        return convertView;
    }
    class ViewHolder{
        TextView tvTitle;
        TextView tvCreateTime;
        public ViewHolder(View view){
            tvTitle=(TextView) view.findViewById(R.id.item_title);
            tvCreateTime=(TextView) view.findViewById(R.id.item_time);
        }
    }
}
