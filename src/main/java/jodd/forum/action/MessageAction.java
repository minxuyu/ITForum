package jodd.forum.action;

import jodd.forum.mapper.UserMapper;
import jodd.forum.model.Message;
import jodd.forum.service.MessageService;
import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.In;
import jodd.madvoc.meta.MadvocAction;
import jodd.madvoc.meta.Out;
import jodd.petite.meta.PetiteInject;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@MadvocAction("/")
public class MessageAction {

    @PetiteInject
    MessageService messageService;
    @PetiteInject
    UserMapper userMapper;

    @Out
    Map<String,List<Message>> map;


    @In
    HttpSession session;

    @Action("/toMessage.do")
    public String toMessage() {
        System.out.println("toMessage.do");
        Integer sessionUid = (Integer) session.getAttribute("uid");
        map = messageService.listMessageByUid(sessionUid);
        System.out.println(map);
        return "message";
    }
}
