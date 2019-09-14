package jodd.forum.service;

import jodd.forum.async.MailTask;
import jodd.forum.mapper.UserMapper;
import jodd.forum.model.Info;
import jodd.forum.model.User;
import jodd.jtx.meta.ReadWriteTransaction;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import java.util.List;



@PetiteBean
public class UserService {
    @PetiteInject
    UserMapper userMapper;
    @PetiteInject
     RedisService redisService;
    @PetiteInject
    MailTask mailTask;


    @ReadWriteTransaction
    public User getProfile(int sessionUid, int uid) {
        //如果是浏览别人的主页，则增加主页浏览数
        String uid_s = Integer.toString(uid);
        if(sessionUid!=uid){
            userMapper.updateScanCount(uid_s);
        }
        //从数据库得到User对象
        User user = userMapper.selectUserByUid(uid);
        //设置获赞数，关注数，粉丝数
//        Jedis jedis = jedisPool.getResource();
//        user.setFollowCount((int)(long)jedis.scard(uid+":follow"));
//        user.setFollowerCount((int)(long)jedis.scard(uid+":fans"));
//        String likeCount = jedis.hget("vote",uid+"");
//        if(likeCount==null){
//            user.setLikeCount(0);
//        }else {
//            user.setLikeCount(Integer.valueOf(likeCount));
//        }
//
//        if(jedis!=null){
//            jedisPool.returnResource(jedis);
//        }
        return user;
    }

    @ReadWriteTransaction
    public void record(StringBuffer requestURL, String contextPath, String remoteAddr) {
        Info info = new Info();
        info.setRequestUrl(requestURL.toString());
        info.setContextPath(contextPath);
        info.setRemoteAddr(remoteAddr);
        userMapper.insertInfo(info);
    }

    @ReadWriteTransaction
    public List<User> listUserByTime() {
        return userMapper.listUserByTime();
    }

    @ReadWriteTransaction
    public List<User> listUserByHot() {
        return userMapper.listUserByHot();
    }

    @ReadWriteTransaction
    public User getEditInfo(int uid) {

        return userMapper.selectEditInfo(uid);
    }

    @ReadWriteTransaction
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @ReadWriteTransaction
    public String updatePassword(String password, String newpassword, String repassword, int sessionUid) {

        String oldPassword = userMapper.selectPasswordByUid(sessionUid);
        if(!oldPassword.equals(password)){
            return "原密码输入错误~";
        }
        if(newpassword.length()<6 ||newpassword.length()>20){
            return "新密码长度要在6~20之间~";
        }

        if(!newpassword.equals(repassword)){
            return "新密码两次输入不一致~";
        }
        userMapper.updatePassword(newpassword,sessionUid);
        return "ok";
    }

    @ReadWriteTransaction
    public void updateHeadUrl(int uid, String headUrl) {

        userMapper.updateHeadUrl(uid,headUrl);
        System.out.println("Success");
    }

    @ReadWriteTransaction
    public boolean getFollowStatus(int sessionUid, int uid) {
        Jedis jedis = redisService.getResource();
        boolean following = jedis.sismember(sessionUid+":follow", String.valueOf(uid));
        if(jedis!=null){
            redisService.returnResource(jedis);
        }
        return following;
    }
    //取消关注
    @ReadWriteTransaction
    public void unfollow(int sessionUid, int uid) {

        userMapper.subFollowCount(sessionUid);
        userMapper.subFollowerCount(uid);
        Jedis jedis = redisService.getResource();
        Transaction tx = jedis.multi();
        tx.srem(sessionUid+":follow", String.valueOf(uid));
        tx.srem(uid+":fans", String.valueOf(sessionUid));
        tx.exec();

        if(jedis!=null){
            redisService.returnResource(jedis);
        }
    }
    //关注用户
    @ReadWriteTransaction
    public void follow(int sessionUid, int uid) {
        System.out.println(sessionUid+"follow"+uid);
        userMapper.addFollowCount(sessionUid);
        userMapper.addFollowerCount(uid);


        Jedis jedis = redisService.getResource();
        Transaction tx = jedis.multi();
        tx.sadd(sessionUid+":follow", String.valueOf(uid));
        tx.sadd(uid+":fans", String.valueOf(sessionUid));
        tx.exec();
        if(jedis!=null){
            redisService.returnResource(jedis);
        }
    }

    //发送忘记密码确认邮件
    @ReadWriteTransaction
    public void forgetPassword(String email) {
        String verifyCode = userMapper.selectVerifyCode(email);
        System.out.println("verifyCode:"+verifyCode);
        //发送邮件
        mailTask.setCode(verifyCode);
        mailTask.setEmail(email);
        mailTask.sendEmail();
    }
    @ReadWriteTransaction
    public void verifyForgetPassword(String code) {
        System.out.println("更新前："+code);
        userMapper.updatePasswordByActivateCode(code);
        System.out.println("更新后："+code);
    }

    @ReadWriteTransaction
    public String getHeadUrlByUid(int uid){
        return userMapper.selectHeadUrl(uid);
    }

    @ReadWriteTransaction
    public String getUsernameByUid(int uid){
        return userMapper.selectUsernameByUid(uid);
    }
}
