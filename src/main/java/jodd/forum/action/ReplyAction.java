package jodd.forum.action;

import jodd.forum.service.ReplyService;
import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.In;
import jodd.madvoc.meta.MadvocAction;
import jodd.madvoc.meta.Out;
import jodd.madvoc.meta.method.POST;
import jodd.petite.meta.PetiteInject;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpSession;

@MadvocAction("/")
public class ReplyAction {
    @PetiteInject
    ReplyService replyService;
    @In
    HttpSession session;
    @In
    int pid;
    @In
    String content;
    @In
    int rid;

    @Out
    Integer replyPid;
    @Out
    String redirect;

    @POST
    @Action("/reply.do")
    //回复
    public String reply(){
        System.out.println("reply.do");
        int sessionUid = (int) session.getAttribute("uid");
        replyService.reply(sessionUid,pid,content);
        replyPid = pid;
        //redirect = "toPost.do?pid="+pid;
        return "post";
    }


    @POST
    @Action("/comment.do")
    //评论
    public String comment(){
        System.out.println("comment.do");
        int sessionUid = (int) session.getAttribute("uid");
        replyService.comment(pid,sessionUid,rid,content);
        return "post";
        //redirect="toPost.do?pid="+pid;
        //return "toPost.do?pid="+pid;
    }
}
