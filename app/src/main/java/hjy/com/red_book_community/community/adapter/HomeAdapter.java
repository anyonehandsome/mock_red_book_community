package hjy.com.red_book_community.community.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hjy.com.red_book_community.R;
import hjy.com.red_book_community.community.activity.ContentActivity;
import hjy.com.red_book_community.community.bean.FontEndBean;

import static android.content.Context.MODE_PRIVATE;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private List<FontEndBean> list;
    private Context context;
    private SharedPreferences sp;

    public HomeAdapter(List<FontEndBean> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_item,
                parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    public FontEndBean getItem(int position){
        return this.list.get(position);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final FontEndBean fontEndBean = getItem(position);
        holder.img.setBackground(fontEndBean.getImages(1));
        holder.title.setText(fontEndBean.getTitle());
        holder.username.setText(fontEndBean.getWriterName());
        holder.head.setBackground(fontEndBean.getWriterAvatar());
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = context.getSharedPreferences("noteMsg",MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("id",fontEndBean.getId());
                editor.commit();
                Intent intent = new Intent(context, ContentActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //获取列表条目总数
        return list == null ? 0 : list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img, head;
        TextView title, username;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.home_item_img);
            title = itemView.findViewById(R.id.home_item_title);
            head = itemView.findViewById(R.id.home_item_head);
            username = itemView.findViewById(R.id.home_item_username);
        }
    }
}