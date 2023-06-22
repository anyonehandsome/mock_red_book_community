package hjy.com.red_book_community.community.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import hjy.com.red_book_community.R;
import hjy.com.red_book_community.community.bean.ImageBean;

public class ImageAdapter extends PagerAdapter  {
    private Context context;
    private ImageBean mImages;

    public ImageAdapter(Context context,ImageBean mImages) {
        this.context = context;
        this.mImages = mImages;
    }

    @Override
    public int getCount() {
        return mImages.getCount();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @SuppressLint("NewApi")
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_content_item, container, false);
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setBackground(mImages.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
