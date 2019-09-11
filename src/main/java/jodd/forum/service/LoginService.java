package jodd.forum.service;

import jodd.forum.async.MailTask;
import jodd.forum.mapper.UserMapper;
import jodd.forum.model.User;
import jodd.forum.util.MyConstant;
import jodd.forum.util.MyUtil;
import jodd.jtx.meta.ReadWriteTransaction;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@PetiteBean
public class LoginService {

    @PetiteInject
    MailTask mailTask;
    @PetiteInject
    UserMapper userMapper;

    //注册
    @ReadWriteTransaction
    public String register(User user, String repassword) {

        //校验邮箱格式
        Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$");
        Matcher m = p.matcher(user.getEmail());
        if(!m.matches()){
            return "邮箱格式不对呀~";
        }

        //校验密码长度
        p = Pattern.compile("^\\w{6,20}$");
        m = p.matcher(user.getPassword());
        if(!m.matches()){
            return "密码长度要在6-20位之间~";
        }

        //检查密码
        if(!user.getPassword().equals(repassword)){
            return "两次输入的密码不一致~";
        }

        //检查邮箱是否被注册
        int emailCount = userMapper.selectEmailCount(user.getEmail());
        if(emailCount>0){
            return "该邮箱已经被注册了~";
        }

        //构造user，设置未激活
        user.setActived(0);
        String activateCode = MyUtil.createActivateCode();
        user.setActivateCode(activateCode);
        user.setJoinTime(MyUtil.formatDate(new Date()));
        user.setUsername("IT"+new Random().nextInt(10000)+"号");
        user.setHeadUrl(MyConstant.IT_IMAGE_URL +"portrait.jpg");

        //发送邮件
        mailTask.setCode(activateCode);
        mailTask.setEmail(user.getEmail());
        mailTask.setOperation(1);
        mailTask.sendEmail();

        //向数据库插入记录
        System.out.println("准备插入记录");
        userMapper.insertUser(user);

        return "ok";
    }



    //登录
    @ReadWriteTransaction
    public Map<String,Object> login(User user) {
        Map<String,Object> map = new HashMap<>();
        Integer uid = userMapper.selectUidByEmailAndPassword(user);

        if(uid==0){
            map.put("status","no");
            map.put("error","用户名和密码错误~");
            return map;
        }

        int checkActived = userMapper.selectActived(user);
        if(checkActived==0){
            map.put("status","no");
            map.put("error","您还没有激活用户哦，请前往邮箱激活~");
            return map;
        }

        String headUrl = userMapper.selectHeadUrl(uid);

        map.put("status","yes");
        map.put("uid",uid);
        map.put("headUrl",headUrl);
        return map;
    }
    @ReadWriteTransaction
    public void activate(String activateCode) {
        userMapper.updateActived(activateCode);
    }
}

