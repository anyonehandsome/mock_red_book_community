package hjy.com.red_book_community.user.service;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

import hjy.com.red_book_community.community.bean.ImageBean;
import hjy.com.red_book_community.user.bean.UserBean;
import hjy.com.red_book_community.utils.MyHelper;

import static android.content.Context.MODE_PRIVATE;

public class UserService {
    private MyHelper myHelper;
    private SQLiteDatabase db;
    private SharedPreferences sp;
    private AssetManager assetManager;
    private int writerId;

    public UserService(Context context) {
        this.myHelper = new MyHelper(context);
        this.assetManager = context.getAssets();
        this.db = myHelper.getReadableDatabase();
        this.sp = context.getSharedPreferences("userInfo", MODE_PRIVATE);
        this.writerId = sp.getInt("id", 0);
    }

    public UserBean queryById(int id) {
        String sql = "select * from user where id = ?";
        UserBean userBean = null;
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});
        boolean b = cursor.moveToFirst();
        if (b) {
            userBean = new UserBean();
            userBean.setId((cursor.getInt(cursor.getColumnIndex("id"))));
            userBean.setName(cursor.getString(cursor.getColumnIndex("name")));
            String avatar = cursor.getString(cursor.getColumnIndex("avatar"));
            userBean.setAvatar(getImage(avatar, 4));
            userBean.setSex(cursor.getString(cursor.getColumnIndex("sex")));
            userBean.setAge(cursor.getInt(cursor.getColumnIndex("age")));
            userBean.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
        }
        return userBean;
    }

    public boolean updateInfo(UserBean userBean) {
        String sql = "update user set ";
        if (userBean.getAge() != 0) {
            sql += " age=" + userBean.getAge() + " ,";
        }
        if (userBean.getName() != null) {
            sql += " name='" + userBean.getName() + "' ,";
        }
        if (userBean.getSex() != null) {
            sql += " sex='" + userBean.getSex() + "' ,";
        }
        if (userBean.getPhone() != null) {
            sql += " phone='" + userBean.getPhone() + "' ,";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += "where id =" + this.writerId;
        db.execSQL(sql);
        return true;
    }
    public void updateAvatar(){
        String sql = "update user set avatar = ? where id =" + this.writerId;
        db.execSQL(sql);
    }
    public Boolean isLoign(String userName, String pwd) {
        String sql = "name=?";
        Cursor cursor = db.query("user", null, sql, new String[]{userName}, null,
                null, null);
        if (cursor.getCount() == 0) {
            return false;
        } else {
            cursor.moveToFirst();
            if (cursor.getString(2).equals(pwd)) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("id", cursor.getInt(cursor.getColumnIndex("id")));
                editor.putString("name", cursor.getString(cursor.getColumnIndex("name")));
                editor.putString("avatar", cursor.getString(cursor.getColumnIndex("avatar")));
                editor.commit();
            }
            cursor.close();
            db.close();
            return true;
        }
    }

    public Boolean isRegister(String name, String pwd) {
        int flag = 1;
        Cursor cursor = db.query("user", new String[]{"name"}, null,
                null, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.getString(1).equals(name)) {
                flag = 0;
                break;
            }
        }
        if (flag == 1) {
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("password", pwd);
            db.insert("user", null, values);
            db.close();
            return true;
        } else {
            return false;
        }
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