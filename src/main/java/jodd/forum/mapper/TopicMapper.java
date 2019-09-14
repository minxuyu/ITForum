package jodd.forum.mapper;

import jodd.db.oom.DbOomQuery;
import jodd.db.oom.sqlgen.DbSqlBuilder;
import jodd.forum.model.Topic;
import jodd.jtx.meta.ReadWriteTransaction;
import jodd.petite.meta.PetiteBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static jodd.db.oom.DbOomQuery.query;
import static jodd.db.oom.sqlgen.DbSqlBuilder.sql;

@PetiteBean
public class TopicMapper {

    
    public List<Topic> listTopic() {
        DbSqlBuilder dbsql =
                sql("select $C{t.tid},$C{t.name},$C{t.content},$C{t.image} from $T{Topic t}");
        DbOomQuery dbquery = query(dbsql);
        ResultSet rs = dbquery.execute();
        List<Topic> list = new ArrayList<>();
        try {
            while (rs.next()) {
                Integer tid = rs.getInt("tid");
                String name = rs.getString("name");
                String content = rs.getString("content");
                String image = rs.getString("image");
                Topic topic = new Topic(tid, name, content, image);
                list.add(topic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    
    public List<String> listImage() {
        DbSqlBuilder dbsql =
                sql("select image_url from image");
        DbOomQuery dbquery = query(dbsql);
        List<String> list = dbquery.list(String.class);
        System.out.println(list);
        return list;
    }
}
