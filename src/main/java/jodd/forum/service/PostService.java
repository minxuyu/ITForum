package jodd.forum.service;

import jodd.forum.async.MessageTask;
import jodd.forum.mapper.MessageMapper;
import jodd.forum.mapper.PostMapper;
import jodd.forum.mapper.ReplyMapper;
import jodd.forum.mapper.UserMapper;
import jodd.forum.model.PageBean;
import jodd.forum.model.Post;
import jodd.forum.util.MyConstant;
import jodd.forum.util.MyUtil;
import jodd.jtx.meta.ReadWriteTransaction;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.List;

@PetiteBean
public class PostService {

    @PetiteInject
    PostMapper postMapper;
    @PetiteInject
    RedisService redisService;
    @PetiteInject
    ReplyMapper replyMapper;
    @PetiteInject
    UserMapper userMapper;
    @PetiteInject
    MessageMapper messageMapper;
    @PetiteInject
    MessageTask messageTask;

    @ReadWriteTransaction
    public PageBean<Post> listPostByTime(int curPage) {
        //每页记录数，从哪开始
        int limit = 8;
        int offset = (curPage - 1) * limit;
        //获得总记录数，总页数
        int allCount = postMapper.selectPostCount();
        int allPage = 0;
        if (allCount <= limit) {
            allPage = 1;
        } else if (allCount / limit == 0) {
            allPage = allCount / limit;
        } else {
            allPage = allCount / limit + 1;
        }
        //获得总记录数，总页数System.out.println("分页得到数据列表");
        List<Post> postList = postMapper.listPostByTime(offset, limit );
        Jedis jedis = redisService.getResource();
        for (Post post : postList) {
            post.setLikeCount((int) (long) jedis.scard(post.getPid() + ":like"));
        }

        //构造PageBean
        PageBean<Post> pageBean = new PageBean<>(allPage, curPage);
        pageBean.setList(postList);

        if (jedis != null) {
            System.out.println("returnResource");
            redisService.returnResource(jedis);
        }
        return pageBean;
    }

    @ReadWriteTransaction
    public List<Post> getPostList(int uid) {
        String post_uid = uid + "";
        return postMapper.listPostByUid(post_uid);
    }

    @ReadWriteTransaction
    public Post getPostByPid(int pid) {
        //更新浏览数
        postMapper.updateScanCount(pid);
        Post post = postMapper.getPostByPid(pid);
        System.out.println(post);
        //设置点赞数
        Jedis jedis = redisService.getResource();
        long likeCount = jedis.scard(pid + ":like");
        post.setLikeCount((int) likeCount);

        if (jedis != null) {
            redisService.returnResource(jedis);
        }
        return post;
    }

    @ReadWriteTransaction
    public boolean getLikeStatus(int pid, int sessionUid) {
        Jedis jedis = redisService.getResource();
        boolean result = jedis.sismember(pid + ":like", String.valueOf(sessionUid));

        if (jedis != null) {
            redisService.returnResource(jedis);
        }
        return result;
    }

    @ReadWriteTransaction
    public String clickLike(int pid, int sessionUid) {
        Jedis jedis = redisService.getResource();
        //pid被sessionUid点赞
        jedis.sadd(pid + ":like", String.valueOf(sessionUid));
        //增加用户获赞数
        jedis.hincrBy("vote", sessionUid + "", 1);
        //插入一条点赞消息
        messageTask.setOperation(MyConstant.OPERATION_CLICK_LIKE);
        messageTask.setPid(pid);
        messageTask.setRid(0);
        messageTask.setSessionUid(sessionUid);
        //改进
        Thread thread=new Thread(messageTask);
        thread.start();
        //messageTask.run();//这种启动多线程的方法不好
        String result = String.valueOf(jedis.scard(pid + ":like"));

        if (jedis != null) {
            redisService.returnResource(jedis);
        }
        return result;
    }

    @ReadWriteTransaction
    public void publishPost(Post post) {
        //构造帖子
        post.setPublishTime(MyUtil.formatDate(new Date()));
        post.setReplyTime(MyUtil.formatDate(new Date()));
        post.setReplyCount(0);
        post.setLikeCount(0);
        post.setScanCount(0);
        //插入一条帖子记录
        postMapper.insertPost(post);

        //更新用户发帖量
        userMapper.updatePostCount(post.getUid() );
    }


}
