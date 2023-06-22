package hjy.com.red_book_community.community.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hjy.com.red_book_community.community.bean.FontEndBean;
import hjy.com.red_book_community.utils.DateUtil;
import hjy.com.red_book_community.utils.MyHelper;

/**
 * 首页展示
 */
public class FontEndService {
    private MyHelper myHelper;
    private SQLiteDatabase db;
    private AssetManager assetManager;
    public FontEndService(Context context) {
        this.myHelper = new MyHelper(context);
        this.db = myHelper.getReadableDatabase();
        this.assetManager = context.getAssets();
    }
    //查询首页展示
    public List<FontEndBean> query(){
        List<FontEndBean> list=new ArrayList<FontEndBean>();
        String sql = "select articles.id,articles.title,articles.likeNumber," +
                "articles.postTime,articles.image1," +
                " user.name,user.avatar " +
                "from articles join user on articles.writerId = user.id";
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
                fontEndBean.setId(id);
                fontEndBean.setTitle(title);
                fontEndBean.setPostTime(DateUtil.convertTime(time));
                fontEndBean.setWriterName(writerName);
                fontEndBean.setWriterAvatar(getImage(writerAvatar,4));
                fontEndBean.setLikeNumber(likeNumber);
                fontEndBean.setImages(getImage(image1,1));
                list.add(fontEndBean);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    /**
     * 将数据库内图片转换为drawable
     */
    public Drawable getImage(String image,int position){
        String fileName = null;
        switch (position){
            case 1:
                fileName = "images1/";
                break;
            case 2:
                fileName = "images2/";
                break;
            case 3:
                fileName = "images3/";
                break;
            case 4:
                fileName = "avatar/";
                break;
        }
        try {
            InputStream inputStream = this.assetManager.open(fileName + image + ".png");
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            inputStream.close();
            return drawable;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
