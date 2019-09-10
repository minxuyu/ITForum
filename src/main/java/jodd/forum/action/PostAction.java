package jodd.forum.action;

import jodd.forum.model.*;
import jodd.forum.service.PostService;
import jodd.forum.service.ReplyService;
import jodd.forum.service.TopicService;
import jodd.forum.service.UserService;
import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.In;
import jodd.madvoc.meta.MadvocAction;
import jodd.madvoc.meta.Out;
import jodd.petite.meta.PetiteInject;
import org.springframework.ui.Model;
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
    @PetiteInject
    TopicService topicService;

    @Out
    PageBean<Post> pageBean;
    @Out
    List<User> userList;
    @Out
    List<User> hotUserList;
    @Out
    Post post;
    @Out
    List<Reply> replyList;
    @Out
    boolean liked;
    @Out
    List<Topic> topicList;
    @Out
    String redirect;
    @Out
    int publishPost_id;


    @In
    HttpSession session;
    @In
    int curPage;
    @In
    int post_tid;
    @In
    int post_uid;
    @In
    String post_title;
    @In
    String post_content;
    @In
    int pid;

    @Action("/toPublish.do")
    public String toPublish(){
        topicList = topicService.listTopic();
        return "publish";
    }

    //发帖
    @RequestMapping("/publishPost.do")
    public String publishPost() {
        String post_headUrl = userService.getHeadUrlByUid(post_uid);
        String post_username = userService.getUsernameByUid(post_uid);
        Post post = new Post(post_title,post_content,post_uid,post_tid,post_headUrl,post_username);
        publishPost_id = postService.publishPost(post);
        redirect = "toPost.do";
        return "publish";
    }


    @Action("/listPostByTime.do")
    public String listPostByTime(){
        System.out.println("123"+curPage);
        pageBean = postService.listPostByTime(curPage);
        userList = userService.listUserByTime();
        hotUserList = userService.listUserByHot();
        return "index";
    }

    @Action("/toPost.do")
    public String toPost() {
        System.out.println("In toPost.do getAttribute");
        Integer sessionUid = (Integer) session.getAttribute("uid");
        //获取帖子信息
        System.out.println("In toPost.do getPostByPid");
        post = postService.getPostByPid(pid);
        //获取评论信息
        System.out.println("In toPost.do listReply");
        replyList = replyService.listReply(pid);
        //判断用户是否已经点赞
        liked = false;
        if (sessionUid != null) {
            System.out.println("In toPost.do getLikeStatus");
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
