package coderschoolasignment.todoapp;

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
    String link;
    String imageDir;
    String deadline;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getImageDir() {
        return imageDir;
    }

    public void setImageDir(String imageDir) {
        this.imageDir = imageDir;
    }
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
        this.link="";
        this.time = "";
        this.title = "";
        this.content = "";
        this.color = "";
        this.deadline ="";
        this.imageDir = "";
    }

    public Note(int id,String title, String content, String time,String color,String link,String imageDir,String deadline) {
        this.link = link;
        this.imageDir = imageDir;
        this.id = id;
        this.time = time;
        this.title = title;
        this.content = content;
        this.color = color;
        this.status = 1;
        this.deadline = this.deadline;
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

    @Override
    public String toString() {
        return "Id: " + this.id + " Title:"+this.title +" Content:" + this.content +" Color:"+this.color + " Time:" + this.time
                + " Link:"+this.link +" ImageDir:" + this.imageDir +" Deadline:"+this.deadline;
    }
}
