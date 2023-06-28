package hjy.com.red_book_community.community.bean;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import java.util.Date;

public class FontEndBean {
    private int id;
    private String title;
    private Bitmap images;
    private Bitmap writerAvatar;
    private String writerName;
    private Long likeNumber;
    private Date postTime;

    public FontEndBean() {
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

    public Bitmap getImages() {
        return images;
    }

    public void setImages(Bitmap image) {
        this.images = image;
    }

    public Bitmap getWriterAvatar() {
        return writerAvatar;
    }

    public void setWriterAvatar(Bitmap writerAvatar) {
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