package hjy.com.red_book_community.community.recycleItem;


import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class Space_item extends RecyclerView.ItemDecoration {
    //设置item的间距
    private int space = 5;

    public Space_item(int space) {
        this.space = space;
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = space;
        outRect.top = space;
    }
}
