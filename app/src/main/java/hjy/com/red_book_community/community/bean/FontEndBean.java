package hjy.com.red_book_community.community.bean;

import android.graphics.drawable.Drawable;
import java.util.Date;

public class FontEndBean {
    private int id;
    private String title;
    private Drawable[] images;
    private Drawable writerAvatar;
    private String writerName;
    private Long likeNumber;
    private Date postTime;

    public FontEndBean() {
    }

    public Drawable[] getImages() {
        return images;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getImages(int position) {
        return images[position];
    }

    public void setImages(Drawable[] image) {
        this.images = image;
    }

    public Drawable getWriterAvatar() {
        return writerAvatar;
    }

    public void setWriterAvatar(Drawable writerAvatar) {
        this.writerAvatar = writerAvatar;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public Long getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(Long likeNumber) {
        this.likeNumber = likeNumber;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }
}