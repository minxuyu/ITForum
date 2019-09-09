package jodd.forum.action;

import jodd.forum.model.PageBean;
import jodd.forum.model.Post;
import jodd.forum.model.Reply;
import jodd.forum.model.User;
import jodd.forum.service.PostService;
import jodd.forum.service.ReplyService;
import jodd.forum.service.UserService;
import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.In;
import jodd.madvoc.meta.MadvocAction;
import jodd.madvoc.meta.Out;
import jodd.petite.meta.PetiteInject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@MadvocAction("/")
public class PostAction {

    @PetiteInject
    PostService postService;
    @PetiteInject
    UserService userService;
    @PetiteInject
    ReplyService replyService;

    @Out
    PageBean<Post> pageBean;
    @Out
    List<User> userList;
    @Out
    List<User> hotUserList;
    @In
    int curPage;

    @Out
    Post post;
    @Out
    List<Reply> replyList;
    @Out
    boolean liked;

    @In
    HttpSession session;

    @Action("/listPostByTime.do")
    public String listPostByTime(){
        System.out.println("123"+curPage);
        pageBean = postService.listPostByTime(curPage);
        userList = userService.listUserByTime();
        hotUserList = userService.listUserByHot();
        return "index";
    }

    @Action("/toPost.do")
    public String toPost(int pid) {
        Integer sessionUid = (Integer) session.getAttribute("uid");
        //获取帖子信息
        post = postService.getPostByPid(pid);
        //获取评论信息
        replyList = replyService.listReply(pid);

        //判断用户是否已经点赞

        liked = false;
        if (sessionUid != null) {
            liked = postService.getLikeStatus(pid, sessionUid);
        }
        return "post";
    }

    //异步点赞
    @RequestMapping(value = "/ajaxClickLike.do", produces = "text/plain;charset=UTF-8")
    public @ResponseBody
    String ajaxClickLike(int pid, HttpSession session) {
        int sessionUid = (int) session.getAttribute("uid");
        return postService.clickLike(pid, sessionUid);
    }
}
