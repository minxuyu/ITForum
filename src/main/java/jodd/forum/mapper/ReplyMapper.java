package jodd.forum.mapper;

import jodd.db.oom.DbOomQuery;
import jodd.db.oom.sqlgen.DbEntitySql;
import jodd.db.oom.sqlgen.DbSqlBuilder;
import jodd.forum.model.Comment;
import jodd.forum.model.Reply;
import jodd.petite.meta.PetiteBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static jodd.db.oom.DbOomQuery.query;
import static jodd.db.oom.sqlgen.DbSqlBuilder.sql;

@PetiteBean
public class ReplyMapper {

    public void insertReply(Reply reply) {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm;ss");
        String formatdate = dateFormat.format(date);
        System.out.println(formatdate);
        DbEntitySql des = new DbEntitySql();
        reply.setReplyTime(formatdate);
        des.insert(reply).query().autoClose().executeUpdate();
//        insert into reply(content,uid,pid,reply_time) values(#{content},#{user.uid},#{post.pid},date_format(now(),'%Y-%c-%d %H:%i:%s'))
    }

    public List<Reply> listReply(Integer pid) {
        DbSqlBuilder dbsql =
                sql("select $C{r.rid}, $C{r.content} ,$C{r.replyTime},$C{r.username},$C{r.uid},$C{u.uid}, $C{r.headUrl} from $T{Reply r} join $T{User u} on r.uid=u.uid where pid = :pid");
        DbOomQuery dbquery = query(dbsql);
        dbquery.setInteger("pid", pid);
        ResultSet resultSet=dbquery.execute();

        //List<Reply> list = dbquery.list(Reply.class);
        List<Reply> list=new ArrayList<>();
        try{
            while (resultSet.next()){
                Reply reply=new Reply();
                reply.setRid(resultSet.getInt("rid"));
                reply.setContent(resultSet.getString("content"));
                reply.setReplyTime(resultSet.getString("reply_time"));
                reply.setUsername(resultSet.getString("username"));
                reply.setUid(resultSet.getInt("uid"));
                reply.setHeadUrl(resultSet.getString("head_url"));
                reply.setPid(pid);
                list.add(reply);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
//        select r.rid,r.content,u.uid,u.username,u.head_url from reply r join user u on r.uid=u.uid
//        where pid=#{pid}
    }

    public void insertComment(Comment comment) {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm;ss");
        String formatdate = dateFormat.format(date);
        DbEntitySql des = new DbEntitySql();
        comment.setCommentTime(formatdate);
        des.insert(comment).query().autoClose().executeUpdate();
//        insert into comment(content,uid,rid,comment_time) values(#{content},#{user.uid},#{reply.rid},date_format(now(),'%Y-%c-%d %H:%i:%s'))
    }

    public List<Comment> listComment(Integer rid) {
        DbSqlBuilder dbsql =
                sql("select $C{c.cid},$C{c.content},$C{u.uid},$C{u.username},$C{u.headUrl} from $T{Comment c} join $T{User u}  on c.uid=u.uid where rid = :rid");
        DbOomQuery dbquery = query(dbsql);
        dbquery.setInteger("rid", rid);
        List<Comment> list = dbquery.list(Comment.class);
        return list;
//        select c.cid,c.content,u.uid,u.username,u.head_url from comment c join user u on c.uid=u.uid
//        where rid=#{rid}
    }

    public String getContentByRid(Integer rid) {

        DbSqlBuilder dbsql =
                sql("select $C{r.content} from $T{Reply r} where rid = :rid");
        DbOomQuery dbquery = query(dbsql);
        dbquery.setInteger("rid", rid);
        List<Reply> list = dbquery.list(Reply.class);
        return list.get(0).getContent();
//        select content from reply where rid=#{rid}
    }
}
