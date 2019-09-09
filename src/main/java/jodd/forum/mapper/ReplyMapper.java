package jodd.forum.mapper;

import jodd.forum.model.Comment;
import jodd.forum.model.Reply;
import jodd.petite.meta.PetiteBean;

import java.util.List;

@PetiteBean
public class ReplyMapper {

    public void insertReply(Reply reply){

    }

    public List<Reply> listReply(String pid){
        return null;
    }

    public void insertComment(Comment comment){

    }

    public List<Comment> listComment(String rid){
        return null;
    }

    public String getContentByRid(String rid){
        return null;
    }
}
