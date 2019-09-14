package jodd.forum.service;

import jodd.forum.action.UserAction;
import jodd.forum.async.MessageTask;
import jodd.forum.mapper.MessageMapper;
import jodd.forum.mapper.PostMapper;
import jodd.forum.mapper.ReplyMapper;
import jodd.forum.mapper.UserMapper;
import jodd.forum.model.Comment;
import jodd.forum.model.Reply;
import jodd.forum.util.MyConstant;
import jodd.jtx.meta.ReadWriteTransaction;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;

import java.util.List;

@PetiteBean
public class ReplyService {

    @PetiteInject
    ReplyMapper replyMapper;
    @PetiteInject
    UserService userService;


    @PetiteInject
    PostMapper postMapper;

    @PetiteInject
    UserMapper userMapper;

    @PetiteInject
    MessageMapper messageMapper;


    //回复
    @ReadWriteTransaction
    public void reply(int sessionUid, int pid, String content) {
        System.out.println("replying");
        //构造Reply对象
        Reply reply = new Reply();
        reply.setUid(sessionUid);
        reply.setPid(pid);
        reply.setContent(content);
        reply.setUsername(userService.getUsernameByUid(sessionUid));
        reply.setHeadUrl(userService.getHeadUrlByUid(sessionUid));
        //向reply表插入一条记录
        System.out.println("向reply表插入一条记录");
        replyMapper.insertReply(reply);
        //更新帖子的回复数
        System.out.println("更新帖子的回复数");
        postMapper.updateReplyCount(pid);
        //更新最后回复时间
        System.out.println("更新最后回复");
        postMapper.updateReplyTime(pid);

        //插入一条回复消息
        System.out.println("插入一条回复消息");
        MessageTask messageTask = new MessageTask(messageMapper, userMapper, postMapper, replyMapper, pid, 0, sessionUid, MyConstant.OPERATION_REPLY);
        Thread thread = new Thread(messageTask);
        thread.start();

    }

    //评论
    @ReadWriteTransaction
    public void comment(int pid, int sessionUid, int rid, String content) {
        //构造Comment
        Comment comment = new Comment();
        comment.setUid(sessionUid);
        comment.setRid(rid);
        comment.setContent(content);
        comment.setUsername(userMapper.selectUsernameByUid(sessionUid));
        //插入一条评论
        replyMapper.insertComment(comment);
        //更新最后回复时间
        postMapper.updateReplyTime(pid);
        //插入一条评论消息
        MessageTask messageTask = new MessageTask(messageMapper, userMapper, postMapper, replyMapper, pid, rid, sessionUid, MyConstant.OPERATION_COMMENT);
        Thread thread = new Thread(messageTask);
        thread.start();
    }

    @ReadWriteTransaction
    public List<Reply> listReply(int pid) {
        //列出回复
        List<Reply> replyList = replyMapper.listReply(pid);
        for (Reply reply : replyList) {
            //列出每条回复下的评论
            List<Comment> commentList = replyMapper.listComment(reply.getRid());
            reply.setCommentList(commentList);
        }
        return replyList;
    }


}
