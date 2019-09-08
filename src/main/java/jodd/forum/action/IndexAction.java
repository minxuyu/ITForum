package jodd.forum.action;

import jodd.forum.model.PageBean;
import jodd.forum.model.Post;
import jodd.forum.model.User;
import jodd.forum.service.PostService;
import jodd.forum.service.UserService;
import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.In;
import jodd.madvoc.meta.MadvocAction;
import jodd.madvoc.meta.Out;
import jodd.petite.meta.PetiteInject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@MadvocAction("/")
public class IndexAction {

    @PetiteInject
    private UserService userService;
    @PetiteInject
    private PostService postService;

    @In HttpServletRequest request;

    @Out PageBean<Post> pageBean;
    @Out List<User> userList;
    @Out List<User> hotUserList;


    @Action("/toIndex.do")
    public String toIndex(){
        System.out.println("打印客户端地址");
        System.out.println(request.getRemoteAddr());
        //记录访问信息
        System.out.println("记录访问信息");
        userService.record(request.getRequestURL(),request.getContextPath(),request.getRemoteAddr());
        //列出帖子
        System.out.println("列出帖子");
        pageBean = postService.listPostByTime(1);
        //列出用户
        System.out.println("列出用户");
        userList = userService.listUserByTime();
        //列出活跃用户
        System.out.println("列出活跃用户");
        hotUserList = userService.listUserByHot();
        return "index";
    }
//
//
//    //上传图片
//    @RequestMapping(value = "/upload.do", method = {RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
//    public
//    @ResponseBody
//    String upload(MultipartFile myFileName) throws IOException {
//
//        // 文件类型限制
//        String[] allowedType = {"image/bmp", "image/gif", "image/jpeg", "image/png"};
//        boolean allowed = Arrays.asList(allowedType).contains(myFileName.getContentType());
//        if (!allowed) {
//            return "error|不支持的类型";
//        }
//        // 图片大小限制
//        if (myFileName.getSize() > 3 * 1024 * 1024) {
//            return "error|图片大小不能超过3M";
//        }
//        // 包含原始文件名的字符串
//        String fi = myFileName.getOriginalFilename();
//        // 提取文件拓展名
//        String fileNameExtension = fi.substring(fi.indexOf("."), fi.length());
//        // 生成云端的真实文件名
//        String remoteFileName = UUID.randomUUID().toString() + fileNameExtension;
//
//        qiniuService.upload(myFileName.getBytes(), remoteFileName);
//
//        // 返回图片的URL地址
//        return MyConstant.QINIU_IMAGE_URL + remoteFileName;
//    }

}
