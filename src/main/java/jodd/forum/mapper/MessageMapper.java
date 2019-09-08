package jodd.forum.mapper;

import jodd.db.oom.DbOomQuery;
import jodd.db.oom.sqlgen.DbSqlBuilder;
import jodd.forum.model.Message;
import jodd.jtx.meta.ReadWriteTransaction;
import jodd.petite.meta.PetiteBean;

import java.util.List;

import static jodd.db.oom.DbOomQuery.query;
import static jodd.db.oom.sqlgen.DbSqlBuilder.sql;

@PetiteBean
public class MessageMapper {

    void insertMessage(Message message) {

//            <insert id="insertMessage">
//                insert into message(uid,other_id,other_username,operation,post_id,displayed_content)
//        values(#{uid},#{otherId},#{otherUsername},#{operation},#{postId},#{displayedContent})
//    </insert>
    }

    @ReadWriteTransaction
    public List<Message> listMessageByUid(String uid) {
        DbSqlBuilder dbsql =
                sql("select $C{m.*} from $T{Message m}  where uid = :uid");
        DbOomQuery dbquery = query(dbsql);
        dbquery.setString("uid", uid);
        List<Message> list = dbquery.list(Message.class);
        return list;

//            <select id="listMessageByUid" resultType="com.fc.model.Message">
//                select * from message where uid=#{uid}
//    </select>
    }
}
