package jodd.forum.service;

import jodd.forum.mapper.ReplyMapper;
import jodd.forum.model.Comment;
import jodd.forum.model.Reply;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;

import java.util.List;

@PetiteBean
public class ReplyService {

    @PetiteInject
    ReplyMapper replyMapper;

    public List<Reply> listReply(int pid) {
        //�г��ظ�
        List<Reply> replyList = replyMapper.listReply(pid + "");
        for (Reply reply : replyList) {
            //�г�ÿ���ظ��µ�����
            List<Comment> commentList = replyMapper.listComment(reply.getRid() + "");
            reply.setCommentList(commentList);
        }
        return replyList;
    }

}
