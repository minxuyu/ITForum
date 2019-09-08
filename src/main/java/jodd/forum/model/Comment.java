package jodd.forum.model;

import jodd.db.oom.meta.DbColumn;
import jodd.db.oom.meta.DbId;
import jodd.db.oom.meta.DbTable;

@DbTable
public class Comment {
    @DbId
    private Integer cid;

    //评论内容
    @DbColumn
    private String content;
    //两个外键，指向Reply和User
    @DbColumn
    private Reply reply;
    @DbColumn
    private User user;

    //评论时间
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

    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
                ", reply=" + reply +
                ", user=" + user +
                ", commentTime='" + commentTime + '\'' +
                '}';
    }
}
