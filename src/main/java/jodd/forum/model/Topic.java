package jodd.forum.model;

import jodd.db.oom.meta.DbColumn;
import jodd.db.oom.meta.DbId;
import jodd.db.oom.meta.DbTable;

@DbTable
public class Topic {
    @DbId
    private Integer tid;

    //名称
    @DbColumn
    private String name;

    //描述
    @DbColumn
    private String content;

    //图片
    @DbColumn
    private String image;

    public Topic() {}

    public Topic(Integer tid, String name, String content, String image) {
        this.tid = tid;
        this.name = name;
        this.content = content;
        this.image = image;
    }

    public Topic(Integer tid) {
        this.tid = tid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "tid=" + tid +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
