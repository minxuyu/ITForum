package jodd.forum.mapper;

import jodd.db.oom.DbOomQuery;
import jodd.db.oom.sqlgen.DbEntitySql;
import jodd.db.oom.sqlgen.DbSqlBuilder;
import jodd.forum.model.Post;
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
        Integer uid_i = Integer.parseInt(uid);
        DbSqlBuilder dbsql =
                sql("select $C{p.uid}, $C{p.pid} ,$C{p.title} ,$C{p.publishTime} from $T{Post p} where uid=:uid_i  ");

        System.out.println("DbOomQuery dbquery = query(dbsql);");
        DbOomQuery dbquery = query(dbsql);
        System.out.println("dbquery.setString");
        dbquery.setInteger("uid_i", uid_i);


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

    public List<Post> listPostByTime(String offset, String limit) {
        int off_set = Integer.parseInt(offset);
        int lim_it = Integer.parseInt(limit);
        DbSqlBuilder dbsql =
                sql("select $C{u.uid} ,$C{u.username} ,$C{u.headUrl}, $C{p.pid}, $C{p.title}," +
                        "$C{p.publishTime} ,$C{p.replyTime} ,$C{p.replyCount}, $C{p.likeCount} ," +
                        "$C{p.scanCount} from $T{Post p} join $T{User u} on p.uid = u.uid  order by $C{p.replyTime} desc limit :off_set,:lim_it");
        DbOomQuery dbquery = query(dbsql);
        dbquery.setInteger("off_set", off_set);
        dbquery.setInteger("lim_it", lim_it);
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
                String username = rs.getString("username");
                String headUrl = rs.getString("head_url");
                Integer uid = rs.getInt("uid");
                Post post = new Post(uid, pid, title, publishTime, replyTime, replyCount, likeCount, scanCount, username, headUrl);
                list.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("查询完毕");
        return list;
    }


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
    }


    public Post getPostByPid(int pid) {
        DbSqlBuilder dbsql =
                sql("select $C{u.uid}, $C{u.username}, $C{u.headUrl}, $C{p.pid}, $C{p.title}, $C{p.content}, $C{p.publishTime}, $C{p.replyTime}, $C{p.replyCount}, $C{p.likeCount}, $C{p.scanCount} from $T{Post p} join $T{User u} on p.uid = u.uid where p.pid=:pid ");
        DbOomQuery dbquery = query(dbsql);
        dbquery.setInteger("pid", pid);
        ResultSet rs = dbquery.execute();
        Post post = new Post();
        try {
            while (rs.next()) {
                post.setPid(rs.getInt("pid"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setPublishTime(rs.getString("publish_time"));
                post.setReplyTime(rs.getString("reply_time"));
                post.setReplyCount(rs.getInt("reply_count"));
                post.setUid(getUidByPid(pid));
                post.setLikeCount(rs.getInt("like_count"));
                post.setScanCount(rs.getInt("scan_count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    public void updateReplyCount(int pid) {
        DbSqlBuilder dbsql =
                sql("update $T{Post p} set reply_count=reply_count+1  where pid = :pid");
        DbOomQuery dbquery = query(dbsql);
        dbquery.setInteger("pid", pid);
        dbquery.executeUpdate();
//            <update id="updateReplyCount">
//                update post set reply_count = reply_count + 1 where pid = #{pid}
//    </update>
    }

    public void updateScanCount(int pid) {
        DbSqlBuilder dbsql =
                sql("update $T{Post p} set scan_count=scan_count+1  where pid = :pid");
        DbOomQuery dbquery = query(dbsql);
        dbquery.setInteger("pid", pid);
        dbquery.executeUpdate();
//    <update id="updateScanCount">
//                update post set scan_count = scan_count + 1 where pid = #{pid}
//    </update>
    }

    public void updateReplyTime(int pid) {
        DbSqlBuilder dbsql =
                sql("update $T{Post p} set reply_time=date_format(now(),'%Y-%c-%d %H:%i:%s')  where pid = :pid");
        DbOomQuery dbquery = query(dbsql);
        dbquery.setInteger("pid", pid);
        dbquery.executeUpdate();
//   <update id="updateReplyTime">
//                update post set reply_time = date_format(now(),'%Y-%c-%d %H:%i:%s')
//        where pid=#{pid}
//    </update>

    }

    public int getUidByPid(int pid) {
        DbSqlBuilder dbsql =
                sql("select uid from $T{Post p} where pid = :pid");
        DbOomQuery dbquery = query(dbsql);
        dbquery.setInteger("pid", pid);
        List<Post> list = dbquery.list(Post.class);
        return list.get(0).getUid();
//           <select id="getUidByPid" resultType="int">
//                select uid from post where pid=#{pid}
//    </select>
    }

    public String getTitleByPid(int pid) {
        DbSqlBuilder dbsql =
                sql("select p.title from $T{Post p} where pid = :pid");
        DbOomQuery dbquery = query(dbsql);
        dbquery.setInteger("pid", pid);
        List<Post> list = dbquery.list(Post.class);
        return list.get(0).getTitle();

//           <select id="getTitleByPid" resultType="String">
//                select title from post where pid=#{pid}
//    </select>
    }
}
