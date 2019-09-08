package jodd.forum.mapper;

import jodd.forum.model.Comment;
import jodd.forum.model.Reply;
import jodd.petite.meta.PetiteBean;

import java.util.List;

@PetiteBean
public class ReplyMapper {
    void insertReply(Reply reply){

    }

    List<Reply> listReply(int pid){
        return null;
    }

    void insertComment(Comment comment){

    }

    List<Comment> listComment(Integer rid){
        return null;
    }

    String getContentByRid(int rid){
        return null;
    }
}
