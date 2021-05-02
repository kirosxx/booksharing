package gdut.imis.domain;

import java.util.Date;

public class Collect{
    private String id;
    private String book_id;
    private String user_id;
    private Date addtime;

    @java.lang.Override
    public java.lang.String toString() {
        return "Collect{" +
                "id='" + id + '\'' +
                ", book_id='" + book_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", addtime=" + addtime +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

}
