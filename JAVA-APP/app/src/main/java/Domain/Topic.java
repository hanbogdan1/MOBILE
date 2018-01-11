package Domain;

/**
 * Created by Bogdan on 10-Jan-18.
 */

public class Topic {

    public String Id_topic;
    public String Title;
    public Integer Likes;
    public Integer Comments;
    public String Link;
    public String Picture_link;

    public Topic(String id_topic, String title, Integer likes, Integer comments, String link, String picture_link) {
        Id_topic = id_topic;
        Title = title;
        Likes = likes;
        Comments = comments;
        Link = link;
        Picture_link = picture_link;
    }

    public Topic() {

    }

    public String getId_topic() {
        return Id_topic;
    }

    public void setId_topic(String id_topic) {
        Id_topic = id_topic;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Integer getLikes() {
        return Likes;
    }

    public void setLikes(Integer likes) {
        Likes = likes;

    }

    public Integer getComments() {
        return Comments;
    }

    public void setComments(Integer comments) {
        Comments = comments;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getPicture_link() {
        return Picture_link;
    }

    public void setPicture_link(String picture_link) {
        Picture_link = picture_link;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "Id_topic='" + Id_topic + '\'' +
                ", Title='" + Title + '\'' +
                ", Likes=" + Likes +
                ", Comments=" + Comments +
                ", Link='" + Link + '\'' +
                ", Picture_link='" + Picture_link + '\'' +
                '}';
    }
}
