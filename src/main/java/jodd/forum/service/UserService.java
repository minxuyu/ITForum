package jodd.forum.service;

import jodd.forum.mapper.UserMapper;
import jodd.forum.model.Info;
import jodd.forum.model.User;
import jodd.jtx.meta.ReadWriteTransaction;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Jedis;

import java.util.List;



@PetiteBean
public class UserService {
    @PetiteInject
    UserMapper userMapper;
   @PetiteInject
   RedisService redisService;

    @ReadWriteTransaction
    public User getProfile(int sessionUid, int uid) {
        //如果是浏览别人的主页，则增加主页浏览数
        String uid_s = Integer.toString(uid);
        if(sessionUid!=uid){
            userMapper.updateScanCount(uid_s);
        }
        //从数据库得到User对象
        User user = userMapper.selectUserByUid(uid_s);
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
        String uid_s = uid + "";
        return userMapper.selectEditInfo(uid_s);
    }

    @ReadWriteTransaction
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @ReadWriteTransaction
    public String updatePassword(String password, String newpassword, String repassword, int sessionUid) {
        String sessionUid_s = sessionUid+"";
        String oldPassword = userMapper.selectPasswordByUid(sessionUid_s);
        if(!oldPassword.equals(password)){
            return "原密码输入错误~";
        }
        if(newpassword.length()<6 ||newpassword.length()>20){
            return "新密码长度要在6~20之间~";
        }

        if(!newpassword.equals(repassword)){
            return "新密码两次输入不一致~";
        }
        userMapper.updatePassword(newpassword,sessionUid_s);
        return "ok";
    }

    @ReadWriteTransaction
    public void updateHeadUrl(int uid, String headUrl) {
        String uid_s = uid + "";
        userMapper.updateHeadUrl(uid_s,headUrl);
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

    @ReadWriteTransaction
    public String getHeadUrlByUid(int uid){
        return userMapper.selectHeadUrl(uid+"");
    }

    @ReadWriteTransaction
    public String getUsernameByUid(int uid){
        return userMapper.selectUsernameByUid(uid+"");
    }
}
