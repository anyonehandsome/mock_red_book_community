package hjy.com.red_book_community.community.service;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import java.util.ArrayList;
import java.util.List;
import hjy.com.red_book_community.community.bean.ArticleBean;
import hjy.com.red_book_community.utils.DateUtil;
import hjy.com.red_book_community.utils.MyHelper;

public class ArticleService {
    private MyHelper myHelper;
    private SQLiteDatabase db;
    private SharedPreferences sp;
    private ImageService imageService;
    private int writerId;
    private String name;
    public ArticleService(Context context) {
        this.myHelper = new MyHelper(context);
        this.db = myHelper.getReadableDatabase();
        this.sp = context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        this.writerId = sp.getInt("id", 0);
        this.name = sp.getString("name",null);
        this.imageService = new ImageService(context);
    }

    //添加数据
    public boolean insertData(String title,String content){
        ContentValues contentValues=new ContentValues();
        contentValues.put("writerId",this.writerId);
        contentValues.put("title",title);
        contentValues.put("content",content);
        contentValues.put("likeNumber",0);
        return
                db.insert("articles",null,contentValues)>0;
    }
    //删除数据
    public boolean deleteData(int id){
        String sql= "id=?";
        String[] contentValuesArray=new String[]{String.valueOf(id)};
        return
                db.delete("articles",sql,contentValuesArray)>0;
    }
    //修改数据
    public boolean updateData(String id,String content,String title){
        ContentValues contentValues=new ContentValues();
        contentValues.put("content",content);
        contentValues.put("title",title);
        String sql="id=?";
        String[] strings=new String[]{id};
        return
                db.update("articles",contentValues,sql,strings)>0;
    }
    //查询数据
    public List<ArticleBean> query(){
        List<ArticleBean> list=new ArrayList<ArticleBean>();
        String sql = "select articles.*, user.name,user.id,user.avatar from articles " +
                "join user on articles.writerId = user.id";
        Cursor cursor=db.rawQuery(sql,null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                ArticleBean articleBean=new ArticleBean();
                String id = String.valueOf(cursor.getInt
                        (cursor.getColumnIndex("id")));
                String title = cursor.getString(cursor.getColumnIndex
                        ("title"));
                String content = cursor.getString(cursor.getColumnIndex
                        ("content"));
                String time = cursor.getString(cursor.getColumnIndex
                        ("createTime"));
                String writerName = cursor.getString(cursor.getColumnIndex
                        ("writerName"));
                String writerAvatar = cursor.getString(cursor.getColumnIndex
                        ("writerAvatar"));
                articleBean.setId(Integer.parseInt(id));
                articleBean.setContent(content);
                articleBean.setTitle(title);
                articleBean.setPostTime(DateUtil.convertTime(time));
                articleBean.setWriterName(writerName);
                Drawable image = this.imageService.getImage(writerAvatar,4);
                articleBean.setWriterAvatar(image);
                list.add(articleBean);
            }
            cursor.close();
        }
        return list;
    }
}
