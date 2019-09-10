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
public class MessageTask{

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

    public void sendMessage() {
        //������Ϣ����
        Message message = new Message();
        //������˭����Ϣ
        int uid = postMapper.getUidByPid(pid);
        message.setUid(uid);

        //���õ�����id���û���
        User user = userMapper.selectUserByUid(sessionUid+"");
        message.setOtherId(user.getUid());
        message.setOtherUsername(user.getUsername());
        message.setPostId(pid);

        //���ò�����չʾ������
        if(operation== MyConstant.OPERATION_CLICK_LIKE){
            message.setOperation("������������");
            message.setDisplayedContent(postMapper.getTitleByPid(pid));
        }else if(operation==MyConstant.OPERATION_REPLY){
            message.setOperation("�ظ�����������");
            message.setDisplayedContent(postMapper.getTitleByPid(pid));
        }else if(operation== MyConstant.OPERATION_COMMENT){
            message.setOperation("�����������ӵĻظ�");
            String content = replyMapper.getContentByRid(rid);
            message.setDisplayedContent(content.substring(content.indexOf("<p>") + 3,content.indexOf("</p>")));
        }
        //�����ݿ����һ����Ϣ
        messageMapper.insertMessage(message);
    }
}
