package jodd.forum.mapper;

import jodd.db.oom.DbOomQuery;
import jodd.db.oom.sqlgen.DbEntitySql;
import jodd.db.oom.sqlgen.DbSqlBuilder;
import jodd.forum.model.Info;
import jodd.forum.model.User;
import jodd.jtx.meta.ReadWriteTransaction;
import jodd.petite.meta.PetiteBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static jodd.db.oom.DbOomQuery.query;
import static jodd.db.oom.sqlgen.DbSqlBuilder.sql;

@PetiteBean
public class UserMapper {

    @ReadWriteTransaction
    public int selectEmailCount(String email) {
        DbSqlBuilder dbsql =
                sql("select $C{u.*} from $T{User u} where email = :email");
        DbOomQuery dbquery = query(dbsql);
        dbquery.setString("email", email);
        List<User> list = dbquery.list(User.class);
        if (!list.isEmpty()) {
            return 1;
        } else {
            return 0;
        }
    }

    @ReadWriteTransaction
    public void insertUser(User user) {
        DbEntitySql des = new DbEntitySql();
        des.insert(user).query().autoClose().executeUpdate();
    }

    @ReadWriteTransaction
    public int selectUidByEmailAndPassword(User user) {
        System.out.println("DbSqlBuilder dbsql");
        DbSqlBuilder dbsql =
                sql("select $C{u.uid} from $T{User u} where email=:email and password=:password ");
        System.out.println("DbOomQuery dbquery = query(dbsql);");
        DbOomQuery dbquery = query(dbsql);
        System.out.println("dbquery.setString");
        dbquery.setString("email", user.getEmail());
        dbquery.setString("password", user.getPassword());
        System.out.println("List<User> list");
        List<User> list = dbquery.list(User.class);
        System.out.println(list.get(0));
        return list.get(0).getUid();
    }

    @ReadWriteTransaction
    public int selectActived(User user) {
        DbSqlBuilder dbsql =
                sql("select $C{u.actived} from $T{User u} where email = :email");
        DbOomQuery dbquery = query(dbsql);
        dbquery.setString("email", user.getEmail());
        List<User> list = dbquery.list(User.class);
        return list.get(0).getActived();
    }

    @ReadWriteTransaction
    public String selectHeadUrl(String uid) {
        DbSqlBuilder dbsql =
                sql("select $C{u.headUrl} from $T{User u} where uid = :uid");
        DbOomQuery dbquery = query(dbsql);
        dbquery.setString("uid", uid);
        List<User> list = dbquery.list(User.class);
        return list.get(0).getHeadUrl();
    }

    @ReadWriteTransaction
    public void updateActived(String activateCode) {
        DbSqlBuilder dbsql =
                sql("update $T{User u} set actived = 1  where activate_code = :activateCode");
        DbOomQuery dbquery = query(dbsql);
        dbquery.setString("activateCode", activateCode);
        dbquery.executeUpdate();
}

    @ReadWriteTransaction
    public void updateScanCount(String uid) {
        DbSqlBuilder dbsql =
                sql("update $T{User u} set scan_count = scan_count + 1  where uid = :uid");
        DbOomQuery dbquery = query(dbsql);
        dbquery.setString("uid", uid);
    }

    @ReadWriteTransaction
    public User selectUserByUid(String uid) {
        DbSqlBuilder dbsql =
                sql("select $C{u.*} from $T{User u} where uid = :uid");
        DbOomQuery dbquery = query(dbsql);
        dbquery.setString("uid", uid);
        List<User> list = dbquery.list(User.class);
        return list.get(0);
    }

    @ReadWriteTransaction
    public void insertInfo(Info info) {
        DbEntitySql des = new DbEntitySql();
        des.insert(info).query().autoClose().executeUpdate();
    }

    @ReadWriteTransaction
    public List<User> listUserByTime() {
        DbSqlBuilder dbsql =
                sql("select $C{u.uid} ,$C{u.username}, $C{u.headUrl}  from $T{User u} order by $u.joinTime desc limit 6");
        DbOomQuery dbquery = query(dbsql);
        List<User> list = dbquery.list(User.class);
        return list;
    }

    @ReadWriteTransaction
    public List<User> listUserByHot() {
        DbSqlBuilder dbsql =
                sql("select $C{u.uid}, $C{u.username}, $C{u.headUrl}  from $T{User u} order by $u.postCount desc limit 6");
        DbOomQuery dbquery = query(dbsql);
        List<User> list = dbquery.list(User.class);
        return list;


    }

    @ReadWriteTransaction
    public User selectEditInfo(String uid) {
        DbSqlBuilder dbsql =
                sql("select $C{u.uid}, $C{u.username}, $C{u.description} ,$C{u.school} ,$C{u.job} from $T{User u}  where uid= :uid");
        DbOomQuery dbquery = query(dbsql);
        dbquery.setString("uid", uid);
        User user = null;
        ResultSet resultSet = dbquery.execute();
        try {
            while (resultSet.next()) {
                Integer user_uid = resultSet.getInt("uid");
                String user_name = resultSet.getString("username");
                String user_description = resultSet.getString("description");
                String school = resultSet.getString("school");
                String job = resultSet.getString("job");
                user = new User(user_uid, user_name, user_description, school, job);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        select uid,username,description,position,school,job from user
//        where uid=#{uid}
        return user;
    }

    @ReadWriteTransaction
    public void updateUser(User user) {
        DbSqlBuilder dbsql =
                sql("update $T{User u} set username= :username , description= :description,position = :position ,school= :school,job= :job where uid = :uid");
        DbOomQuery dbquery = query(dbsql);
        dbquery.setString("username", user.getUsername());
        dbquery.setString("description", user.getDescription());
        dbquery.setString("position", user.getPosition());
        dbquery.setString("school", user.getSchool());
        dbquery.setString("job", user.getJob());
        dbquery.setString("uid", user.getUid() + "");
        dbquery.executeUpdate();
//        update user set username=#{username},description=#{description},position=#{position},school=#{school},job=#{job}
//        where uid=#{uid}
    }

    @ReadWriteTransaction
    public void updatePassword(String newPassword, String uid) {
        DbSqlBuilder dbsql =
                sql("update $T{User u} set password= :password  where uid = :uid");
        DbOomQuery dbquery = query(dbsql);
        dbquery.setString("password", newPassword);
        dbquery.setString("uid", uid);
        dbquery.executeUpdate();
//        update user set password = #{newPassword} where uid=#{uid}
    }

    @ReadWriteTransaction
    public String selectPasswordByUid(String uid) {
        DbSqlBuilder dbsql =
                sql("select $C{u.password} from $T{User u}  where uid= :uid");
        DbOomQuery dbquery = query(dbsql);
        dbquery.setString("uid", uid);
        String password = null;
        ResultSet resultSet = dbquery.execute();
        try {
            while (resultSet.next()) {
                password = resultSet.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return password;
//        select password from user where uid=#{uid}
    }

    @ReadWriteTransaction
    public void updateHeadUrl(String uid,String headUrl){
        DbSqlBuilder dbsql =
                sql("update $T{User u} set head_url= :headUrl  where uid = :uid");
        DbOomQuery dbquery = query(dbsql);
        dbquery.setString("headUrl",headUrl);
        dbquery.setString("uid", uid);
        dbquery.executeUpdate();
//        update user set head_url=#{headUrl} where uid=#{uid}
    }

}
