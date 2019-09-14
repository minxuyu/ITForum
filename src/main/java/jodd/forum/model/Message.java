package jodd.forum.model;
import jodd.db.oom.meta.DbColumn;
import jodd.db.oom.meta.DbId;
import jodd.db.oom.meta.DbTable;

import java.util.Date;

@DbTable
public class Message {
    @DbId
    private Integer mid;


    @DbColumn
    private Integer uid;

    //哪个用户
    @DbColumn
    private Integer otherId;
    @DbColumn
    private String otherUsername;
    //什么操作
    @DbColumn
    private String operation;
    //操作了什么内容
    @DbColumn
    private Integer postId;
    //帖子，回复，评论
    @DbColumn
    private String displayedContent;
    @DbColumn
    private Date MsgTime;

    public Message() {}


    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getOtherId() {
        return otherId;
    }

    public void setOtherId(Integer otherId) {
        this.otherId = otherId;
    }

    public String getOtherUsername() {
        return otherUsername;
    }

    public void setOtherUsername(String otherUsername) {
        this.otherUsername = otherUsername;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getDisplayedContent() {
        return displayedContent;
    }

    public void setDisplayedContent(String displayedContent) {
        this.displayedContent = displayedContent;
    }

    public Date getMsgTime() {
        return MsgTime;
    }

    public void setMsgTime(Date msgTime) {
        MsgTime = msgTime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "mid=" + mid +
                ", uid=" + uid +
                ", otherId=" + otherId +
                ", otherUsername='" + otherUsername + '\'' +
                ", operation='" + operation + '\'' +
                ", postId=" + postId +
                ", displayedContent='" + displayedContent + '\'' +
                ", MsgTime=" + MsgTime +
                '}';
    }
}
