package jodd.forum.async;

import jodd.forum.mapper.MessageMapper;
import jodd.forum.mapper.PostMapper;
import jodd.forum.mapper.ReplyMapper;
import jodd.forum.mapper.UserMapper;
import jodd.forum.model.Message;
import jodd.forum.model.User;
import jodd.forum.util.MyConstant;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;

@PetiteBean
public class MessageTask implements Runnable{

    @PetiteInject
    MessageMapper messageMapper;
    @PetiteInject
    UserMapper userMapper;
    @PetiteInject
    PostMapper postMapper;
    @PetiteInject
    ReplyMapper replyMapper;

    private int pid;
    private int rid;
    private int sessionUid;
    private int operation;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getSessionUid() {
        return sessionUid;
    }

    public void setSessionUid(int sessionUid) {
        this.sessionUid = sessionUid;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public MessageTask() {
    }

    public MessageTask(MessageMapper messageMapper, UserMapper userMapper, PostMapper postMapper, ReplyMapper replyMapper, int pid, int rid, int sessionUid, int operation) {
        this.messageMapper = messageMapper;
        this.userMapper = userMapper;
        this.postMapper = postMapper;
        this.replyMapper = replyMapper;
        this.pid = pid;
        this.rid = rid;
        this.sessionUid = sessionUid;
        this.operation = operation;
    }



    @Override
    public void run() {

            //创建消息对象
            Message message = new Message();
            //设置是谁的消息
            int uid = postMapper.getUidByPid(pid);
            message.setUid(uid);

            //设置点在人id和用户名
            User user = userMapper.selectUserByUid(sessionUid+"");
            message.setOtherId(user.getUid());
            message.setOtherUsername(user.getUsername());
            message.setPostId(pid);

            //设置操作和展示内容
            if(operation== MyConstant.OPERATION_CLICK_LIKE){
                message.setOperation("赞了您的贴子");
                message.setDisplayedContent(postMapper.getTitleByPid(pid));
            }else if(operation==MyConstant.OPERATION_REPLY){
                message.setOperation("回复了您的贴子");
                message.setDisplayedContent(postMapper.getTitleByPid(pid));
            }else if(operation== MyConstant.OPERATION_COMMENT){
                message.setOperation("评论了你贴子的回复");
                String content = replyMapper.getContentByRid(rid);
                message.setDisplayedContent(content.substring(content.indexOf("<p>") + 3,content.indexOf("</p>")));
            }
            //向数据库插入一条消息
            messageMapper.insertMessage(message);

    }
}
