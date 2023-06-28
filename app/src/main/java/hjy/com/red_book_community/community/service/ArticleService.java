package hjy.com.red_book_community.community.service;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import hjy.com.red_book_community.community.bean.ArticleBean;
import hjy.com.red_book_community.community.bean.ImageBean;
import hjy.com.red_book_community.utils.DateUtil;
import hjy.com.red_book_community.utils.ImageUtils;
import hjy.com.red_book_community.utils.MyHelper;

import static android.content.Context.MODE_PRIVATE;

public class ArticleService {
    private MyHelper myHelper;
    private SQLiteDatabase db;
    private SharedPreferences sp_1;
    private SharedPreferences sp_2;
    private Context context;
    private int writerId;
    private int articleId;

    public ArticleService(Context context) {
        this.myHelper = new MyHelper(context);
        this.context = context;
        this.db = myHelper.getReadableDatabase();
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
        byte[] image1 = ImageUtils.bitmapToByteArray(imageBean.getImage1());
        contentValues.put("image1", image1);
        if (imageBean.getImage2() != null) {
            byte[] image2 = ImageUtils.bitmapToByteArray(imageBean.getImage2());
            contentValues.put("image2", image2);
        }
        if (imageBean.getImage3() != null) {
            byte[] image3 = ImageUtils.bitmapToByteArray(imageBean.getImage3());
            contentValues.put("image3", image3);
        }
        return
                db.insert("articles", null, contentValues) > 0;
    }

    //删除数据
    public boolean deleteData(int articleId) {
        String sql = "id=?";
        String[] contentValuesArray = new String[]{String.valueOf(articleId)};
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
        String sql = "select articles.id,articles.writerId," +
                "articles.title,articles.content,articles.postTime," +
                "articles.likeNumber," +
                "user.name,user.avatar " +
                "from articles " +
                "join user on user.id = articles.writerId " +
                "where articles.id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(this.articleId)});
        if (cursor.moveToFirst()) {
            int index = cursor.getColumnIndex("avatar");
            int articleId = cursor.getInt(cursor.getColumnIndex
                    ("id"));
            int writerId = cursor.getInt(cursor.getColumnIndex
                    ("writerId"));
            String title = cursor.getString(cursor.getColumnIndex
                    ("title"));
            String content = cursor.getString(cursor.getColumnIndex
                    ("content"));
            String writerName = cursor.getString(cursor.getColumnIndex
                    ("name"));
            String postTime = cursor.getString(cursor.getColumnIndex
                    ("postTime"));
            Long likeNumber = cursor.getLong(cursor.getColumnIndex
                    ("likeNumber"));
            byte[] writerAvatar = cursor.getBlob(index);
            ImageBean imageBean = new ImageBean(articleId);
            imageBean = queryArticleImage3(
                    queryArticleImage2(queryArticleImage1(imageBean)));
            articleBean = new ArticleBean(articleId, writerId, title, content,
                    writerName, likeNumber, DateUtil.convertTime(postTime), imageBean);
            articleBean.setWriterAvatar(ImageUtils.byteArrayToBitmap(writerAvatar));
        }
        cursor.close();
        return articleBean;
    }


    public ImageBean queryArticleImage1(ImageBean imageBean) {
        String sql = "select articles.image1 " +
                "from articles " +
                "where articles.id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(articleId)});
        if (cursor.moveToFirst()) {
            byte[] image1 = cursor.getBlob(0);
            cursor.close();
            imageBean.setImage1(ImageUtils.byteArrayToBitmap(image1));
            return imageBean;
        } else {
            return null;
        }
    }

    public ImageBean queryArticleImage2(ImageBean imageBean) {
        String sql = "select articles.image2 " +
                "from articles " +
                "where articles.id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(articleId)});
        if (cursor.moveToFirst()) {
            byte[] image1 = cursor.getBlob(0);
            cursor.close();
            imageBean.setImage2(ImageUtils.byteArrayToBitmap(image1));
            return imageBean;
        } else {
            return null;
        }
    }

    public ImageBean queryArticleImage3(ImageBean imageBean) {
        String sql = "select articles.image3 " +
                "from articles " +
                "where articles.id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(articleId)});
        if (cursor.moveToFirst()) {
            byte[] image3 = cursor.getBlob(0);
            cursor.close();
            imageBean.setImage3(ImageUtils.byteArrayToBitmap(image3));
            return imageBean;
        } else {
            return null;
        }
    }

    /**
     * 返回个人笔记
     */
    public ArrayList<ArticleBean> queryOwnArticle() {
        ArrayList<ArticleBean> list = new ArrayList<ArticleBean>();
        String sql = "select articles.id,articles.writerId," +
        "articles.title,articles.content,articles.postTime," +
                "articles.likeNumber," +
                "user.name,user.avatar " +
                "from articles " +
                "join user on user.id = articles.writerId " +
                "where user.id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(this.writerId)});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                ArticleBean articleBean;
                int articleId = cursor.getInt(cursor.getColumnIndex
                        ("id"));
                int writerId = cursor.getInt(cursor.getColumnIndex
                        ("writerId"));
                String title = cursor.getString(cursor.getColumnIndex
                        ("title"));
                String content = cursor.getString(cursor.getColumnIndex
                        ("content"));
                byte[] writerAvatar = cursor.getBlob(cursor.getColumnIndex
                        ("avatar"));
                String writerName = cursor.getString(cursor.getColumnIndex
                        ("name"));
                String postTime = cursor.getString(cursor.getColumnIndex
                        ("postTime"));
                Long likeNumber = cursor.getLong(cursor.getColumnIndex
                        ("likeNumber"));
                ImageBean imageBean = new ImageBean(articleId);
                imageBean = queryArticleImage3
                        (queryArticleImage2
                        (queryArticleImage1(imageBean)));
                articleBean = new ArticleBean(articleId, writerId, title, content,
                        writerName, likeNumber, DateUtil.convertTime(postTime), imageBean);
                articleBean.setWriterAvatar(ImageUtils.byteArrayToBitmap(writerAvatar));
                list.add(articleBean);
            }
            cursor.close();
        }
        return list;
    }

    public ArrayList<ArticleBean> queryOwnArticleCover() {
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
                byte[] image1 = cursor.getBlob(cursor.getColumnIndex
                        ("image1"));
                imageBean = new ImageBean(articleId, ImageUtils.byteArrayToBitmap(image1),
                        null, null);
                articleBean = new ArticleBean(articleId, title, likeNumber,
                        DateUtil.convertTime(postTime), imageBean);
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
                byte[] writerAvatar = cursor.getBlob(cursor.getColumnIndex
                        ("avatar"));
                String writerName = cursor.getString(cursor.getColumnIndex
                        ("name"));
                String postTime = cursor.getString(cursor.getColumnIndex
                        ("postTime"));
                Long likeNumber = cursor.getLong(cursor.getColumnIndex
                        ("likeNumber"));
                byte[] image1 = cursor.getBlob(cursor.getColumnIndex
                        ("image1"));
                byte[] image2 = cursor.getBlob(cursor.getColumnIndex
                        ("image2"));
                byte[] image3 = cursor.getBlob(cursor.getColumnIndex
                        ("image3"));
                imageBean = new ImageBean(articleId, ImageUtils.byteArrayToBitmap(image1),
                        ImageUtils.byteArrayToBitmap(image2), ImageUtils.byteArrayToBitmap(image3));
                articleBean = new ArticleBean(articleId, writerId, title, content,
                        writerName, likeNumber, DateUtil.convertTime(postTime), imageBean);
                articleBean.setWriterAvatar(ImageUtils.byteArrayToBitmap(writerAvatar));
                list.add(articleBean);
            }
            cursor.close();
        }
        return list;
    }

    private InputStream convertBlobToStream(byte[] blobData) {
        return new ByteArrayInputStream(blobData);
    }

}
