package jodd.forum.model;

import jodd.db.oom.meta.DbColumn;
import jodd.db.oom.meta.DbId;
import jodd.db.oom.meta.DbTable;
import jodd.madvoc.meta.In;

@DbTable
public class Comment {
    @DbId
    private Integer cid;

    //评论内容
    @DbColumn
    private String content;
    //两个外键，指向Reply和User
    @DbColumn
    private Integer rid;
    @DbColumn
    private Integer uid;
    @DbColumn
    private String username;
    //评论时间

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @DbColumn
    private String commentTime;

    public Comment() {}

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "cid=" + cid +
                ", content='" + content + '\'' +
                ", rid=" + rid +
                ", uid=" + uid +
                ", commentTime='" + commentTime + '\'' +
                '}';
    }
}
