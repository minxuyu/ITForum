package jodd.forum.service;

import jodd.forum.mapper.ReplyMapper;
import jodd.forum.model.Comment;
import jodd.forum.model.Reply;
import jodd.jtx.meta.ReadWriteTransaction;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;

import java.util.List;

@PetiteBean
public class ReplyService {

    @PetiteInject
    ReplyMapper replyMapper;

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
