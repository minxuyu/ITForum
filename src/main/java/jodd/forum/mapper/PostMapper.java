package jodd.forum.mapper;

import jodd.db.oom.DbOomQuery;
import jodd.db.oom.sqlgen.DbEntitySql;
import jodd.db.oom.sqlgen.DbSqlBuilder;
import jodd.forum.model.Post;
import jodd.forum.model.User;
import jodd.jtx.meta.ReadWriteTransaction;
import jodd.petite.meta.PetiteBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static jodd.db.oom.DbOomQuery.query;
import static jodd.db.oom.sqlgen.DbSqlBuilder.sql;

@PetiteBean
public class PostMapper {

    public List<Post> listPostByUid(String uid) {

//            <select id="listPostByUid" resultType="com.fc.model.Post">
//                select uid,pid,title,publish_time from post where uid=#{uid}
//    </select>

        DbSqlBuilder dbsql =
                sql("select $C{p.uid} $C{p.pid} $C{p.title} $C{p.publishTime} from $T{Post p} where uid=:uid  ");

        System.out.println("DbOomQuery dbquery = query(dbsql);");
        DbOomQuery dbquery = query(dbsql);
        System.out.println("dbquery.setString");
        dbquery.setString("uid", uid);


        List<Post> list = dbquery.list(Post.class);
        return list;
    }

    public void insertPost(Post post) {
        DbEntitySql des = new DbEntitySql();
        des.insert(post).query().autoClose().executeUpdate();

//            <insert id="insertPost">
//        <selectKey resultType="int" order="AFTER" keyProperty="pid">
//                select last_insert_id() as pid
//        </selectKey>
//                insert into post(title,content,publish_time,reply_time,reply_count,like_count,scan_count,uid,tid)
//        values(#{title},#{content},#{publishTime},#{replyTime},#{replyCount},#{likeCount},#{scanCount},#{user.uid},#{topic.tid})
//    </insert>
    }

    @ReadWriteTransaction
    public List<Post> listPostByTime(int offset, int limit) {
        DbSqlBuilder dbsql =
                sql("select $C{u.uid} ,$C{u.username} ,$C{u.headUrl}, $C{p.pid}, $C{p.title}," +
                        "$C{p.publishTime} ,$C{p.replyTime} ,$C{p.replyCount}, $C{p.likeCount} ," +
                        "$C{p.scanCount} from $T{Post p} join $T{User u} on p.uid = u.uid  order by $C{p.replyTime} desc");
        DbOomQuery dbquery = query(dbsql);
//        dbquery.setInteger("offset",offset);
//        dbquery.setInteger("limit",limit);
        System.out.println("查询数据库获取推送列表");
        ResultSet rs = dbquery.execute();
        List<Post> list = new ArrayList<>();
        try {
            while (rs.next()) {
                Integer pid = rs.getInt("pid");
                String title = rs.getString("title");
                String publishTime = rs.getString("publish_time");
                String replyTime = rs.getString("reply_time");
                Integer replyCount = rs.getInt("reply_count");
                Integer likeCount = rs.getInt("like_count");
                Integer scanCount = rs.getInt("scan_count");
                Post post = new Post(pid, title, publishTime, replyTime, replyCount, likeCount, scanCount);
                list.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("查询完毕");
        return list;

//            <select id="listPostByTime" resultMap="postMap">
//                select u.uid,u.username,u.head_url,p.pid,p.title,p.publish_time,p.reply_time,p.reply_count,p.like_count,p.scan_count from post p
//        join user u on p.uid = u.uid
//        order by p.reply_time desc limit #{offset},#{limit}
//    </select>

    }

    @ReadWriteTransaction
    public int selectPostCount() {
        DbSqlBuilder dbsql =
                sql("select * from $T{Post p} ");
        DbOomQuery dbquery = query(dbsql);
        System.out.println("DbOomQuery dbquery = query(dbsql);");
        System.out.println("dbquery.setString");
        System.out.println("查询推送信息总页数");
        List<Post> list = dbquery.list(Post.class);
        System.out.println("查询完毕");
        int a = list.size();
        return a;
//           <select id="selectPostCount" resultType="int">
//                select count(*) from post
//    </select>
    }

    @ReadWriteTransaction
    public Post getPostByPid(int pid) {

        DbSqlBuilder dbsql =
                sql("select $C{u.uid},$C{u.username},$C{u.headUrl},$C{p.pid},$C{p.title},$C{p.content}," +
                        "$C{p.publishTime},p$C{.replyTime},$C{p.replyCount},$C{p.likeCount}" +
                        ",$C{p.scanCount} from $T{post p}" +
                        "     join $T{user u} on $p.uid = $u.uid where $p.pid=:pid ");


        DbOomQuery dbquery = query(dbsql);
        dbquery.setInteger("pid", pid);
        ResultSet rs = dbquery.execute();
        Post post = new Post();
        User user = new User();
        try {
            user.setUid(rs.getInt("uid"));
            user.setUsername(rs.getString("username"));
            user.setHeadUrl(rs.getString("headUrl"));
            post.setPid(rs.getInt("pid"));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            post.setPublishTime(rs.getString("publishTime"));
            post.setReplyTime(rs.getString("replyTime"));
            post.setReplyCount(rs.getInt("replyCount"));
            post.setUser(user);
            post.setLikeCount(rs.getInt("likeCount"));
            post.setScanCount(rs.getInt("scanCount"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
//           <select id="getPostByPid" resultMap="post2Map">
//                select u.uid,u.username,u.head_url,p.pid,p.title,p.content,p.publish_time,p.reply_time,p.reply_count,p.like_count,p.scan_count from post p
//        join user u on p.uid = u.uid
//        where p.pid=#{pid}
//    </select>
    }

    public void updateReplyCount(int pid) {
//            <update id="updateReplyCount">
//                update post set reply_count = reply_count + 1 where pid = #{pid}
//    </update>
    }

    public void updateScanCount(int pid) {
//    <update id="updateScanCount">
//                update post set scan_count = scan_count + 1 where pid = #{pid}
//    </update>
    }

    public void updateReplyTime(int pid) {
//   <update id="updateReplyTime">
//                update post set reply_time = date_format(now(),'%Y-%c-%d %H:%i:%s')
//        where pid=#{pid}
//    </update>

    }

    public int getUidByPid(int pid) {
        return 0;
//           <select id="getUidByPid" resultType="int">
//                select uid from post where pid=#{pid}
//    </select>
    }

    public String getTitleByPid(int pid) {
        return null;
//           <select id="getTitleByPid" resultType="String">
//                select title from post where pid=#{pid}
//    </select>
    }
}
