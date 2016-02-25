package coderschoolasignment.todoapp;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Administrator on 23/02/2016.
 */
public class Note implements Serializable{
    int id;
    String title;
    String content;
    String time;
    String color;
    int status;


    //STATUS: 1:NOTE-NOT-DONE-YES 2:NOTE-DONE


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Note() {
        this.status = 1;
    }

    public Note(int id,String title, String content, String time,String color) {
        this.id = id;
        this.time = time;
        this.title = title;
        this.content = content;
        this.color = color;
        this.status = 1;
    }

    public String getTitle() {
        return title;
    }
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
