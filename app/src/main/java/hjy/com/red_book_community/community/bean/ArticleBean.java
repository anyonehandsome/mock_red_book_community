package hjy.com.red_book_community.community.bean;

import android.graphics.drawable.Drawable;

import java.util.Date;

public class ArticleBean {
    private int id;
    private int writerId;
    private String title;
    private String content;
    private Drawable writerAvatar;
    private String writerName;
    private Long likeNumber;
    private Date postTime;
    private ImageBean imageBean;

    public ArticleBean() {
    }

    public ArticleBean(int id, String title, Long likeNumber, Date postTime, ImageBean imageBean) {
        this.id = id;
        this.title = title;
        this.likeNumber = likeNumber;
        this.postTime = postTime;
        this.imageBean = imageBean;
    }

    public ArticleBean(int id, int writerId, String title, String content,
                       Drawable writerAvatar, String writerName, Long likeNumber,
                       Date postTime, ImageBean imageBean) {
        this.id = id;
        this.writerId = writerId;
        this.title = title;
        this.content = content;
        this.writerAvatar = writerAvatar;
        this.writerName = writerName;
        this.likeNumber = likeNumber;
        this.postTime = postTime;
        this.imageBean = imageBean;
    }

    public int getWriterId() {
        return writerId;
    }

    public void setWriterId(int writerId) {
        this.writerId = writerId;
    }

    public ImageBean getImageBean() {
        return imageBean;
    }

    public void setImageBean(ImageBean imageBean) {
        this.imageBean = imageBean;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String notepadContent) {
        this.content = notepadContent;
    }
    public Date getPostTime() {
        return postTime;
    }
    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }
}
