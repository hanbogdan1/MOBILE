package Domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bogdan on 10-Jan-18.
 */

public class Response {

    public static String Last_id;
    public static Integer count;

    public List<Topic> Topics;

    public Response() {
        Topics = new ArrayList<Topic>();
    }

    public static String getLast_id() {
        return Last_id;
    }

    public List<Topic> getTopics() {
        return Topics;
    }

    public void add_topic(Topic A){
        Topics.add(A);
    }

    public static void setLast_id(String last_id) {
        Last_id = last_id;
    }

    public static void setCount(Integer count) {

        Response.count = count;
    }

    public static Integer getCount() {

        return count;
    }

    public  Integer get_size(){
        return Topics.size();

    }
}
