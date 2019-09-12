package jodd.forum.model;

import jodd.db.oom.meta.DbColumn;
import jodd.db.oom.meta.DbId;
import jodd.db.oom.meta.DbTable;
import jodd.forum.mapper.UserMapper;
import jodd.petite.meta.PetiteInject;

@DbTable
public class Post {

    @PetiteInject
    UserMapper userMapper;

    @DbId
    private Integer pid;

    //标题和内容
    @DbColumn
    private String title;
    @DbColumn
    private String content;

    //两个时间
    @DbColumn
    private String publishTime;
    @DbColumn
    private String replyTime;

    //三个数量
    @DbColumn
    private Integer replyCount;
    @DbColumn
    private Integer likeCount;
    @DbColumn
    private Integer scanCount;

    //两个外键
    @DbColumn
    private Integer uid;
    @DbColumn
    private Integer tid;
    @DbColumn
    private String headUrl;
    @DbColumn
    private String username;

    public Post(String title, String content, Integer uid, Integer tid, String headUrl, String username) {
        this.title = title;
        this.content = content;
        this.uid = uid;
        this.tid = tid;
        this.headUrl = headUrl;
        this.username = username;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Post() {

    }

    public Post(Integer uid, Integer pid, String title, String publishTime, String replyTime, Integer replyCount, Integer likeCount, Integer scanCount, String username, String headUrl) {
        this.uid = uid;
        this.pid = pid;
        this.title = title;
        this.publishTime = publishTime;
        this.replyTime = replyTime;
        this.replyCount = replyCount;
        this.likeCount = likeCount;
        this.scanCount = scanCount;
        this.username = username;
        this.headUrl = headUrl;
    }

    public Post(Integer pid) {
        this.pid = pid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getScanCount() {
        return scanCount;
    }

    public void setScanCount(Integer scanCount) {
        this.scanCount = scanCount;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    @Override
    public String toString() {
        return "Post{" +
                "userMapper=" + userMapper +
                ", pid=" + pid +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", replyTime='" + replyTime + '\'' +
                ", replyCount=" + replyCount +
                ", likeCount=" + likeCount +
                ", scanCount=" + scanCount +
                ", uid=" + uid +
                ", tid=" + tid +
                ", headUrl='" + headUrl + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
