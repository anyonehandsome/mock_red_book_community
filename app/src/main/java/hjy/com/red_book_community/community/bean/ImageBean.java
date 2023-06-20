package hjy.com.red_book_community.community.bean;

import android.graphics.drawable.Drawable;

public class ImageBean {
    private int id;
    private int articleId;
    private Drawable image1;
    private Drawable image2;
    private Drawable image3;

    public ImageBean(int articleId, Drawable image1, Drawable image2, Drawable image3) {
        this.articleId = articleId;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
    }

    public int getCount(){
        if(this.image1 == null){
            return 0;
        }else if(this.image2 ==null){
            return 1;
        }else if(this.image3 == null){
            return 2;
        }else {
            return 3;
        }
    }

    public Drawable get(int position){
        if(position == 1) {
            return this.image1;
        }else if(position == 2){
            return this.image2;
        }else if(position == 3){
            return this.image3;
        }else return null;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public Drawable getImage1() {
        return image1;
    }

    public void setImage1(Drawable image1) {
        this.image1 = image1;
    }

    public Drawable getImage2() {
        return image2;
    }

    public void setImage2(Drawable image2) {
        this.image2 = image2;
    }

    public Drawable getImage3() {
        return image3;
    }

    public void setImage3(Drawable image3) {
        this.image3 = image3;
    }
}
