package hjy.com.red_book_community.community.service;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import hjy.com.red_book_community.community.bean.ArticleBean;
import hjy.com.red_book_community.community.bean.ImageBean;
import hjy.com.red_book_community.utils.DateUtil;
import hjy.com.red_book_community.utils.MyHelper;

public class ImageService {
    private MyHelper myHelper;
    private SQLiteDatabase db;
    private SharedPreferences sp_1;
    private SharedPreferences sp_2;
    private AssetManager assetManager;
    private int writerId;
    private int articleId;

    public ImageService(Context context) {
        this.myHelper = new MyHelper(context);
        this.assetManager = context.getAssets();
        this.db = myHelper.getReadableDatabase();
        this.sp_1 = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        this.sp_2 = context.getSharedPreferences("noteMsg", Context.MODE_PRIVATE);
        this.writerId = sp_1.getInt("id",0);
        this.articleId = sp_2.getInt("id",0);
    }

    //添加数据
    public boolean insertData(String title, String[] images) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("writerId", this.writerId);
        contentValues.put("iamge1", images[0]);
        contentValues.put("iamge2", images[1]);
        contentValues.put("iamge3", images[2]);
        return
                db.insert("articlesImages", null, contentValues) > 0;
    }

    //查询数据
    public ArticleBean query() {
        ImageBean imageBean = null;
        ArticleBean articleBean = null;
        String sql = "select articles.*," +
                "articlesImages.image1,articlesImages.image2,articlesImages.image3," +
                "user.name,user.avatar " +
                "from articles " +
                "join articlesImages on articles.id = articlesImages.articleId " +
                "join user on user.id = articles.writerId " +
                "where articlesImages.articleId = ?";
        List<ImageBean> list = new ArrayList<ImageBean>();
        Cursor cursor = db.rawQuery(sql,new String[]{String.valueOf(this.articleId)});
        if (cursor.moveToFirst()) {
            int articleId = cursor.getInt(cursor.getColumnIndex
                    ("id"));
            int writerId = cursor.getInt(cursor.getColumnIndex
                    ("writerId"));
            String title = cursor.getString(cursor.getColumnIndex
                    ("title"));
            String content = cursor.getString(cursor.getColumnIndex
                    ("content"));
            String writerAvatar = cursor.getString(cursor.getColumnIndex
                    ("avatar"));
            Drawable temp = getImage(writerAvatar,4);
            String writerName = cursor.getString(cursor.getColumnIndex
                    ("name"));
            String postTime = cursor.getString(cursor.getColumnIndex
                    ("postTime"));
            Long likeNumber = cursor.getLong(cursor.getColumnIndex
                    ("likeNumber"));
            String image1 = cursor.getString(cursor.getColumnIndex
                    ("image1"));
            String image2 = cursor.getString(cursor.getColumnIndex
                    ("image2"));
            String image3 = cursor.getString(cursor.getColumnIndex
                    ("image3"));
            imageBean = new ImageBean(articleId, getImage(image1,1),
                    getImage(image2,2), getImage(image3,3));
            articleBean = new ArticleBean(articleId,writerId,title,content,temp,
                    writerName,likeNumber,DateUtil.convertTime(postTime),imageBean);
        }
        cursor.close();
        return articleBean;
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
