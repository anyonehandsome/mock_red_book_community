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

import static android.content.Context.MODE_PRIVATE;

public class ArticleService {
    private MyHelper myHelper;
    private SQLiteDatabase db;
    private SharedPreferences sp_1;
    private SharedPreferences sp_2;
    private AssetManager assetManager;
    private int writerId;
    private int articleId;

    public ArticleService(Context context) {
        this.myHelper = new MyHelper(context);
        this.db = myHelper.getReadableDatabase();
        this.assetManager = context.getAssets();
        this.sp_2 = context.getSharedPreferences("noteMsg", MODE_PRIVATE);
        this.articleId = sp_2.getInt("id", 0);
        this.sp_1 = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        this.writerId = sp_1.getInt("id", 0);
    }

    //添加数据
    public boolean insertData(String title, String content, ImageBean imageBean) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("writerId", this.writerId);
        contentValues.put("title", title);
        contentValues.put("content", content);
        contentValues.put("likeNumber", 0);
        contentValues.put("image1", imageBean.getImage1().toString());
        if (imageBean.getImage2() != null) {
            contentValues.put("image2", imageBean.getImage2().toString());
        }
        if (imageBean.getImage3() != null) {
            contentValues.put("image3", imageBean.getImage3().toString());
        }
        return
                db.insert("articles", null, contentValues) > 0;
    }

    //删除数据
    public boolean deleteData(int id) {
        String sql = "id=?";
        String[] contentValuesArray = new String[]{String.valueOf(id)};
        return
                db.delete("articles", sql, contentValuesArray) > 0;
    }

    //修改数据
    public boolean updateData(String id, String content, String title) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", content);
        contentValues.put("title", title);
        String sql = "id=?";
        String[] strings = new String[]{id};
        return
                db.update("articles", contentValues, sql, strings) > 0;
    }

    public ArticleBean queryCurrentArticle() {
        ArticleBean articleBean = null;
        String sql = "select articles.*,user.name,user.avatar " +
                "from articles " +
                "join user on user.id = articles.writerId " +
                "where articles.id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(this.articleId)});
        if (cursor.moveToFirst()) {
            ImageBean imageBean;
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
            Drawable temp = getImage(writerAvatar, 4);
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
            imageBean = new ImageBean(articleId, getImage(image1, 1),
                    getImage(image2, 2), getImage(image3, 3));
            articleBean = new ArticleBean(articleId, writerId, title, content, temp,
                    writerName, likeNumber, DateUtil.convertTime(postTime), imageBean);
            Drawable image = getImage(writerAvatar, 4);
            articleBean.setWriterAvatar(image);
        }
        return articleBean;
    }

    /**
     * 返回个人笔记
     */
    public ArrayList<ArticleBean> queryOwnArticle() {
        ArrayList<ArticleBean> list = new ArrayList<ArticleBean>();
        String sql = "select articles.*,user.name,user.avatar " +
                "from articles " +
                "join user on user.id = articles.writerId " +
                "where user.id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(this.writerId)});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                ArticleBean articleBean;
                ImageBean imageBean;
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
                Drawable temp = getImage(writerAvatar, 4);
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
                imageBean = new ImageBean(articleId, getImage(image1, 1),
                        getImage(image2, 2), getImage(image3, 3));
                articleBean = new ArticleBean(articleId, writerId, title, content, temp,
                        writerName, likeNumber, DateUtil.convertTime(postTime), imageBean);
                Drawable image = getImage(writerAvatar, 4);
                articleBean.setWriterAvatar(image);
                list.add(articleBean);
            }
            cursor.close();
        }
        return list;
    }

    public ArrayList<ArticleBean> queryOwnArticleCover(){
        ArrayList<ArticleBean> list = new ArrayList<>();
        String sql = "select id,title,postTime,image1,likeNumber from articles " +
                "where writerId = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(this.writerId)});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                ArticleBean articleBean;
                ImageBean imageBean;
                int articleId = cursor.getInt(cursor.getColumnIndex
                        ("id"));
                String title = cursor.getString(cursor.getColumnIndex
                        ("title"));
                String postTime = cursor.getString(cursor.getColumnIndex
                        ("postTime"));
                Long likeNumber = cursor.getLong(cursor.getColumnIndex
                        ("likeNumber"));
                String image1 = cursor.getString(cursor.getColumnIndex
                        ("image1"));
                imageBean = new ImageBean(articleId, getImage(image1, 1), null,null);
                articleBean = new ArticleBean(articleId, title, likeNumber, DateUtil.convertTime(postTime), imageBean);
                list.add(articleBean);
            }
            cursor.close();
        }
        return list;
    }

    //查询数据
    public List<ArticleBean> query() {
        List<ArticleBean> list = new ArrayList<ArticleBean>();
        String sql = "select articles.*,user.name,user.avatar " +
                "from articles " +
                "join user on user.id = articles.writerId";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                ArticleBean articleBean;
                ImageBean imageBean;
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
                Drawable temp = getImage(writerAvatar, 4);
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
                imageBean = new ImageBean(articleId, getImage(image1, 1),
                        getImage(image2, 2), getImage(image3, 3));
                articleBean = new ArticleBean(articleId, writerId, title, content, temp,
                        writerName, likeNumber, DateUtil.convertTime(postTime), imageBean);
                Drawable image = getImage(writerAvatar, 4);
                articleBean.setWriterAvatar(image);
                list.add(articleBean);
            }
            cursor.close();
        }
        return list;
    }

    /**
     * 将数据库内图片转换为drawable
     */

    public Drawable getImage(String image, int position) {
        String fileName = null;
        switch (position) {
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
