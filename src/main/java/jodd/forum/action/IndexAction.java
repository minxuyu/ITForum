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

    @In
    HttpServletRequest request;

    @Out
    PageBean<Post> pageBean;
    @Out
    List<User> userList;
    @Out
    List<User> hotUserList;


    @Action("/toIndex.do")
    public String toIndex() {
        //��¼������Ϣ
        System.out.println("Routing at index");
        System.out.println("Record visit information");
        userService.record(request.getRequestURL(), request.getContextPath(), request.getRemoteAddr());
        //�г�����
        System.out.println("List post");
        pageBean = postService.listPostByTime(1);
        //�г��û�
        System.out.println("List user");
        userList = userService.listUserByTime();
        //�г���Ծ�û�
        System.out.println("List active user");
        hotUserList = userService.listUserByHot();
        return "index";
    }
//
//
//    //�ϴ�ͼƬ
//    @RequestMapping(value = "/upload.do", method = {RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
//    public
//    @ResponseBody
//    String upload(MultipartFile myFileName) throws IOException {
//
//        // �ļ���������
//        String[] allowedType = {"image/bmp", "image/gif", "image/jpeg", "image/png"};
//        boolean allowed = Arrays.asList(allowedType).contains(myFileName.getContentType());
//        if (!allowed) {
//            return "error|��֧�ֵ�����";
//        }
//        // ͼƬ��С����
//        if (myFileName.getSize() > 3 * 1024 * 1024) {
//            return "error|ͼƬ��С���ܳ���3M";
//        }
//        // ����ԭʼ�ļ������ַ���
//        String fi = myFileName.getOriginalFilename();
//        // ��ȡ�ļ���չ��
//        String fileNameExtension = fi.substring(fi.indexOf("."), fi.length());
//        // �����ƶ˵���ʵ�ļ���
//        String remoteFileName = UUID.randomUUID().toString() + fileNameExtension;
//
//        qiniuService.upload(myFileName.getBytes(), remoteFileName);
//
//        // ����ͼƬ��URL��ַ
//        return MyConstant.QINIU_IMAGE_URL + remoteFileName;
//    }

}
