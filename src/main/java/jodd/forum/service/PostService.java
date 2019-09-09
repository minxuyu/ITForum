package jodd.forum.service;

import jodd.forum.mapper.PostMapper;
import jodd.forum.model.PageBean;
import jodd.forum.model.Post;
import jodd.jtx.meta.ReadWriteTransaction;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@PetiteBean
public class PostService {

    @PetiteInject
    PostMapper postMapper;
    @PetiteInject
    RedisService redisService;

    @ReadWriteTransaction
    public PageBean<Post> listPostByTime(int curPage) {
        //每页记录数，从哪开始
        int limit = 8;
        int offset = (curPage-1) * limit;
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
        List<Post> postList = postMapper.listPostByTime(offset+"",limit+"");
        System.out.println("从Redis连接池建立jedis连接");
        Jedis jedis = redisService.getResource();
        System.out.println("连接成功");
        for(Post post : postList){
            post.setLikeCount((int)(long)jedis.scard(post.getPid()+":like"));
        }

        //构造PageBean
        PageBean<Post> pageBean = new PageBean<>(allPage,curPage);
        pageBean.setList(postList);

        if(jedis!=null){
            System.out.println("returnResource");
            redisService.returnResource(jedis);
        }
        return pageBean;
    }
    //根据uid，获得帖子列表
    @ReadWriteTransaction
    public List<Post> getPostList(int uid) {
        String post_uid=uid+"";
        return postMapper.listPostByUid(post_uid);
    }
}
