package jodd.forum.action;

import jodd.forum.model.PageBean;
import jodd.forum.model.Post;
import jodd.forum.model.User;
import jodd.forum.service.PostService;
import jodd.forum.service.UserService;
import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.MadvocAction;
import jodd.madvoc.meta.Out;
import jodd.petite.meta.PetiteInject;

import java.util.List;

@MadvocAction("/")
public class PostAction {

    @PetiteInject
    PostService postService;
    @PetiteInject
    UserService userService;

    @Out
    PageBean<Post> pageBean;
    @Out
    List<User> userList;
    @Out
    List<User> hotUserList;

    @Action("/listPostByTime.do")
    public String listPostByTime(int curPage){
        pageBean = postService.listPostByTime(curPage);
        userList = userService.listUserByTime();
        hotUserList = userService.listUserByHot();
        return "index";
    }
}
