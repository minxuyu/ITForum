package jodd.forum.action;

import jodd.forum.model.Post;
import jodd.forum.model.User;
import jodd.forum.service.PostService;
import jodd.forum.service.UserService;
import jodd.forum.util.MyConstant;
import jodd.madvoc.meta.In;
import jodd.madvoc.meta.MadvocAction;
import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.Out;
import jodd.madvoc.meta.method.POST;
import jodd.petite.meta.PetiteInject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.io.File;

@MadvocAction("/")
public class UserAction {

    @PetiteInject
    UserService userService;
    @PetiteInject
    PostService postService;

    @Out User user;
    @Out List<Post> postList;
    @Out String redirect;
    @Out String passwordError;
    @Out String error3;
    @Out boolean following;


    @In HttpSession session;
    @In HttpServletRequest request;
    @In Integer uid;
    @In String username;
    @In String description;
    @In String position;
    @In String school;
    @In String job;

    @In String password;
    @In String newpassword;
    @In String repassword;


    @Action("/toMyProfile.do")
    public String toMyProfile() {
        int sessionUid = (int) session.getAttribute("uid");
        user = userService.getProfile(sessionUid, sessionUid);
//        postList =  postService.getPostList(sessionUid);
        return "myProfile";
    }

    @Action("/toEditProfile.do")
    public String toEditProfile(){
        int uid = (int) session.getAttribute("uid");
        user = userService.getEditInfo(uid);
        return "editProfile";
    }

    @Action("/editProfile.do")
    public String editProfile(){
        User user = new User(uid,username,description,position,school,job);
        userService.updateUser(user);
        redirect = "myProfile";
        return "editProfile";
    }


    @Action("/updatePassword.do")
    public String updatePassword(){
        int sessionUid = (int) session.getAttribute("uid");
        String status = userService.updatePassword(password,newpassword,repassword,sessionUid);
        if(status.equals("ok")){
            redirect = "logout.do";
        }else {
            passwordError = status;
        }
        return "editProfile";
    }



//    @Action("/forgetPassword.do")
//    public @ResponseBody
//    String forgetPassword(String email){
//        userService.forgetPassword(email);
//        return "";
//    }
//
//
//    @Action("/afterForgetPassword.do")
//    public String afterForgetPassword(){
//        return "prompt/afterForgetPassword";
//    }

//    @POST
//    @Action("/updateHeadUrl.do")
//    public String updateHeadUrl() throws IOException {
//         文件类型限制
//        String[] allowedType = {"image/bmp", "image/gif", "image/jpeg", "image/png"};
//        System.out.println(myFileName.isEmpty());
//        boolean allowed = Arrays.asList(allowedType).contains(myFileName.getContentType());
//        if (!allowed) {
//            error3 = "图片格式仅限bmp，jpg，png，gif ~";
//            return "editProfile";
//        }
//        // 图片大小限制
//        if (myFileName.getSize() > 3 * 1024 * 1024) {
//            error3 = "图片大小限制在3M以下哦~";
//            return "editProfile";
//        }
//        // 包含原始文件名的字符串
//        String fi = myFileName.getOriginalFilename();
//        // 提取文件拓展名
//        String fileNameExtension = fi.substring(fi.indexOf("."), fi.length());
//        // 生成云端的真实文件名
//        String remoteFileName = UUID.randomUUID().toString() + fileNameExtension;
////        qiniuService.upload(myFileName.getBytes(), remoteFileName);
//        //更新数据库中头像URL
//        int uid = (int) session.getAttribute("uid");
//        userService.updateHeadUrl(uid, MyConstant.IT_IMAGE_URL + remoteFileName);
//        redirect = "myProfile";
//        return "editProfile";
//
//    }

//    @Action("/follow.do")
//    public String follow(int uid,HttpSession session){
//        int sessionUid = (int) session.getAttribute("uid");
//        userService.follow(sessionUid,uid);
//        return "forward:toProfile.do";
//    }
//
//    @Action("/unfollow.do")
//    public String unfollow(int uid,HttpSession session){
//        int sessionUid = (int) session.getAttribute("uid");
//        userService.unfollow(sessionUid,uid);
//        return "forward:toProfile.do";
//    }
//
//
//    @Action("/verify.do")
//    public String verify(String code){
//        userService.verifyForgetPassword(code);
//        return "redirect:toLogin.do";
//    }

    @Action("/toProfile.do")
    public String toProfile() {
        System.out.println("In toProfile.do session.getAttribute");
        int sessionUid = (int) session.getAttribute("uid");
        if(sessionUid==uid){
            return "myProfile";
        }
        System.out.println("In toProfile.do getFollowStatus");
        following = userService.getFollowStatus(sessionUid,uid);
        System.out.println("In toProfile.do getProfile");
        user = userService.getProfile(sessionUid, uid);
        System.out.println("In toProfile.do getPostList");
        postList =  postService.getPostList(uid);
        return "profile";
    }
}
