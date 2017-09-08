package licancan.com.horizontaltabhost.bean;

/**
 * Created by robot on 2017/8/30.
 */

public class News {
    public String id;
    public String name;
    public boolean state;


    public News(String name, boolean state) {
        this.name=name;
        this.state=state;
    }

    public News() {
    }
}
