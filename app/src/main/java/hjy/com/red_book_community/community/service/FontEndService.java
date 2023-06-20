package hjy.com.red_book_community.community.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hjy.com.red_book_community.community.bean.FontEndBean;
import hjy.com.red_book_community.utils.DateUtil;
import hjy.com.red_book_community.utils.MyHelper;

public class FontEndService {
    private MyHelper myHelper;
    private SQLiteDatabase db;
    private SharedPreferences sp;
    private ImageService imageService;
    private int writerId;
    public FontEndService(Context context) {
        this.imageService = new ImageService(context);
        this.myHelper = new MyHelper(context);
        this.db = myHelper.getReadableDatabase();
        this.sp = context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        this.writerId = sp.getInt("id", 0);
    }
    //查询数据
    public List<FontEndBean> query(){
        List<FontEndBean> list=new ArrayList<FontEndBean>();
        String sql = "select articles.id,articles.title,articles.postTime,articles.likeNumber, " +
                "user.name,user.avatar," +
                "articlesImages.image1,articlesImages.image2,articlesImages.image3 " +
                "from articles join user on articles.writerId = user.id " +
                "join articlesImages on articles.id = articlesImages.articleId";
        Cursor cursor=db.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            do{
                FontEndBean fontEndBean = new FontEndBean();
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String writerAvatar = cursor.getString
                        (cursor.getColumnIndex("avatar"));
                String title = cursor.getString(cursor.getColumnIndex
                        ("title"));
                String writerName = cursor.getString(cursor.getColumnIndex
                        ("name"));
                Long likeNumber = cursor.getLong(cursor.getColumnIndex
                        ("likeNumber"));
                String time = cursor.getString(cursor.getColumnIndex
                        ("postTime"));
                String image1 = cursor.getString(cursor.getColumnIndex
                        ("image1"));
                String image2 = cursor.getString(cursor.getColumnIndex
                        ("image2"));
                String image3 = cursor.getString(cursor.getColumnIndex
                        ("image3"));
                fontEndBean.setId(id);
                fontEndBean.setTitle(title);
                fontEndBean.setPostTime(DateUtil.convertTime(time));
                fontEndBean.setWriterName(writerName);
                fontEndBean.setWriterAvatar(imageService.getImage(writerAvatar,4));
                fontEndBean.setLikeNumber(likeNumber);
                fontEndBean.setImages(new Drawable[]{imageService.getImage(image1,1),
                        imageService.getImage(image2,2),
                        imageService.getImage(image3,3)});
                list.add(fontEndBean);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }
}
