package jodd.forum.action;

import jodd.forum.model.User;
import jodd.forum.service.LoginService;
import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.In;
import jodd.madvoc.meta.MadvocAction;
import jodd.madvoc.meta.Out;
import jodd.madvoc.meta.method.GET;
import jodd.madvoc.meta.method.POST;
import jodd.petite.meta.PetiteInject;

import javax.servlet.http.HttpSession;
import java.util.Map;

@MadvocAction("/")
public class LoginAction {

    @PetiteInject
    LoginService loginService;

    @Out
    String info;
    @Out
    String register;
    @Out
    String email;
    @Out
    String error;
    @Out
    String status;

    @In
    String FormEmail;
    @In
    String password;
    @In
    String repassword;
    @In
    HttpSession session;
    @In
    String code;

    @Action("/toLogin.do")
    public String toLogin() {
        System.out.println(session);
        return "login";
    }


    @POST
    @Action("/register.do")
    public String register(User user) {
        user.setEmail(FormEmail);
        user.setPassword(repassword);
        System.out.println(user + " " + repassword);
        String result = loginService.register(user, repassword);
        if (result.equals("ok")) {
            info = "系统已经向你的邮箱发送了一封邮件哦，验证后就可以登录啦~";
            return "prompt/promptInfo";
        } else {
            register = "yes";
            email = user.getEmail();
            error = result;
            return "login";
        }
    }

    @POST
    @Action("/login.do")
    public String login(User user) {
        System.out.println("开始执行login前台");
        user.setEmail(FormEmail);
        user.setPassword(password);
        System.out.println("开始执行login后台");
        Map<String, Object> map = loginService.login(user);
        System.out.println("查询结果状态：" + map.get("status"));
        status = map.get("status").toString();
        if (map.get("status").equals("yes")) {
            session.setAttribute("uid", map.get("uid"));
            session.setAttribute("headUrl", map.get("headUrl"));
            System.out.println("开始跳转到个人页");
            return "login";
        } else {
            email = user.getEmail();
            error = (String) map.get("error");
            return "login";
        }
    }

    @Action("/activate.do")
    public String activate() {
        loginService.activate(code);
        System.out.println("用户码：" + code);
        info = "您的账户已经激活成功，可以去登录啦~";
        return "prompt/promptInfo";
    }

    @GET
    @Action("/logout.do")
    public String logout() {
        session.removeAttribute("uid");
        status = "toIndex.do";
        return "login";
    }
}
