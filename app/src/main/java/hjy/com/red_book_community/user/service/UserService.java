package hjy.com.red_book_community.user.service;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.Toast;

import hjy.com.red_book_community.community.service.ImageService;
import hjy.com.red_book_community.user.bean.UserBean;
import hjy.com.red_book_community.utils.MyHelper;

import static android.content.Context.MODE_PRIVATE;

public class UserService {
    private MyHelper myHelper;
    private SQLiteDatabase db;
    private SharedPreferences sp;
    private ImageService imageService;

    public UserService(Context context) {
        this.myHelper = new MyHelper(context);
        this.db = myHelper.getReadableDatabase();
        this.imageService = new ImageService(context);
        this.sp = context.getSharedPreferences("userInfo",MODE_PRIVATE);
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
            userBean.setAvatar(imageService.getImage(avatar,4));
            userBean.setSex(cursor.getString(cursor.getColumnIndex("sex")));
            userBean.setAge(cursor.getInt(cursor.getColumnIndex("age")));
            userBean.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
        }
        return userBean;
    }

    public Boolean isLoign(String userName,String pwd){
        String sql = "name=?";
        Cursor cursor = db.query("user", null, sql, new String[]{userName}, null,
                null, null);
        if (cursor.getCount() == 0){
            return false;
        }else {
            cursor.moveToFirst();
            if (cursor.getString(2).equals(pwd)) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("id",cursor.getInt(cursor.getColumnIndex("id")));
                editor.putString("name",cursor.getString(cursor.getColumnIndex("name")));
                editor.putString("avatar",cursor.getString(cursor.getColumnIndex("avatar")));
                editor.commit();
            }
            cursor.close();
            db.close();
            return true;
        }
    }

    public Boolean isRegister(String name,String pwd){
        int flag = 1;
        Cursor cursor = db.query("user", new String[]{"name"}, null,
                null, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.getString(1).equals(name)) {
                flag = 0;
                break;
            }
        }
        if (flag == 1){
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("password", pwd);
            db.insert("user", null, values);
            db.close();
            return true;
        }else{
            return false;
        }
    }
}