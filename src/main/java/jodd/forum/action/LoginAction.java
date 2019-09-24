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
        return "login";
    }


    @POST
    @Action("/register.do")
    public String register(User user) {
        user.setEmail(FormEmail);
        user.setPassword(password);
        String result = loginService.register(user, repassword);
        if (result.equals("ok")) {
            info = "系统已经向你的邮箱发送了一份邮件，验证后就可以登陆啦~";
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
        user.setEmail(FormEmail);
        user.setPassword(password);
        Map<String, Object> map = loginService.login(user);
        status = map.get("status").toString();
        if (map.get("status").equals("yes")) {
            session.setAttribute("uid", map.get("uid"));
            session.setAttribute("headUrl", map.get("headUrl"));
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
        info = "您的账户已经激活成功，可以去登陆啦~";
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
