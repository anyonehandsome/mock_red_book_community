package hjy.com.red_book_community.user.bean;

import android.graphics.drawable.Drawable;

public class UserBean {
    private int id;
    private String name;
    private String password;
    private String sex;
    private Drawable avatar;
    private int age;
    private String phone;

    public UserBean() {
    }

    public UserBean(String name, String sex, int age, String phone) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.phone = phone;
    }

    public Drawable getAvatar() {
        return avatar;
    }

    public void setAvatar(Drawable avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}