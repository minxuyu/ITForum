package jodd.forum.mapper;

import jodd.db.oom.DbOomQuery;
import jodd.db.oom.sqlgen.DbEntitySql;
import jodd.db.oom.sqlgen.DbSqlBuilder;
import jodd.forum.model.Message;
import jodd.jtx.meta.ReadWriteTransaction;
import jodd.petite.meta.PetiteBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static jodd.db.oom.DbOomQuery.query;
import static jodd.db.oom.sqlgen.DbSqlBuilder.sql;

@PetiteBean
public class MessageMapper {

    public void insertMessage(Message message) {

        DbEntitySql des = new DbEntitySql();
        des.insert(message).query().autoClose().executeUpdate();


//            <insert id="insertMessage">
//                insert into message(uid,other_id,other_username,operation,post_id,displayed_content)
//        values(#{uid},#{otherId},#{otherUsername},#{operation},#{postId},#{displayedContent})
//    </insert>
    }

    public List<Message> listMessageByUid(int uid) {
        DbSqlBuilder dbsql =
                sql("select $C{m.*} from $T{Message m}  where uid = :uid");
        DbOomQuery dbquery = query(dbsql);
        dbquery.setInteger("uid", uid);
        List<Message> list=new ArrayList<>();
        ResultSet resultSet=dbquery.execute();
        try{
            while (resultSet.next()){
                Message message=new Message();
                message.setMsgTime(resultSet.getDate("msg_time"));
                message.setMid(resultSet.getInt("mid"));
                message.setUid(resultSet.getInt("uid"));
                message.setOtherId(resultSet.getInt("other_id"));
                message.setOtherUsername(resultSet.getString("other_username"));
                message.setOperation(resultSet.getString("operation"));
                message.setDisplayedContent(resultSet.getString("displayed_content"));
                list.add(message);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return list;
    }

}
