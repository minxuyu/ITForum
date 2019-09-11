package jodd.forum.async;

import jodd.forum.util.MyConstant;
import jodd.mail.Email;
import jodd.mail.MailServer;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import jodd.petite.meta.PetiteBean;

@PetiteBean
public class MailTask {

    private String code;
    private String email;
    private Email JoddMailSender = Email.create();
    private int operation;

    public MailTask() {

    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Email getJoddMailSender() {
        return JoddMailSender;
    }

    public void setJoddMailSender(Email joddMailSender) {
        JoddMailSender = joddMailSender;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }


    public void sendEmail() {
        System.out.println(code);
        System.out.println(email);
        System.out.println(getJoddMailSender());
        System.out.println(operation);
        System.out.println("开始发邮件...");
        JoddMailSender.from(MyConstant.MAIL_FROM);
        JoddMailSender.to(email);
        JoddMailSender.subject("一封激活邮件");
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head></head><body>");

        if (operation == 1) {
            sb.append("<a href=" + MyConstant.DOMAIN_NAME + "activate.do?code=");
            sb.append(code);
            sb.append(">点击激活</a></body>");
        } else {
            sb.append("是否将你的密码修改为:");
            sb.append(code.substring(0, 8));
            sb.append("，<a href=" + MyConstant.DOMAIN_NAME + "verify.do?code=" + code + ">");
            sb.append("点击是</a></body>");
        }
        JoddMailSender.htmlMessage(sb.toString());

        System.out.println("结束发邮件...");
        SmtpServer smtpServer = MailServer.create()
                .ssl(true)
                .host("smtp.qq.com")
                .auth("2261996255@qq.com", "uvdgkdguckraebcb")
                .buildSmtpMailServer();

        SendMailSession session = smtpServer.createSession();
        session.open();
        session.sendMail(JoddMailSender);
        session.close();
    }
}
