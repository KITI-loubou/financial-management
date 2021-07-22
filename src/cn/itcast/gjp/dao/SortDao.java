package cn.itcast.gjp.dao;

import cn.itcast.gjp.domain.Sort;
import cn.itcast.gjp.tools.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import javax.management.Query;
import java.sql.SQLException;
import java.util.List;

/*
    数据访问层:主要对数据库表进行增删改查
    一般来说,操作的是那张表,就叫表名Dao
    操作数据库的gjp_sort表,叫SortDao
 */
public class SortDao {
    /*
        定义一个查询所有分类信息的方法
        SELECT * FROM gjp_sort;
        方法的参数:查询所有数据,不需要参数
        方法的返回值:List<Sort>
        BeanListHandler：将结果集中每一条记录封装到指定的javaBean中，将这些javaBean在封装到List集合中
     */
    public List<Sort> querySortAll(){
        //try...catch快捷键:选中代码==>ctrl+alt+t==>选择try...catch
        try {
            //1.创建QueryRunner对象
            QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
            //2.使用QueryRunner对象中的方法query,执行查询的sql语句,获取查询结果
            String sql = "SELECT * FROM gjp_sort;";
            List<Sort> list = qr.query(sql, new BeanListHandler<Sort>(Sort.class));
            //3.把查询的结果返回到service层
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //返回null,没有查询到数据
        return null;
    }

    /*
        添加一条新的分类数据到数据库的gjp_sort表中
        添加数据:insert语句
        INSERT INTO gjp_sort(sid,sname,parent,sdesc) VALUES (1,'服装支出','支出','买衣服');
        方法参数:Sort对象(封装了用户填写的分类数据)
        方法返回值:不需要
     */
    public void addSort(Sort sort){
        try {
            //1.创建QueryRunner对象
            QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
            //拼接sql语句
            String sql = "INSERT INTO gjp_sort(sname,parent,sdesc) VALUES (?,?,?);";
            //定义数组,存储?占位符的实际数据
            Object[] params = {sort.getSname(),sort.getParent(),sort.getSdesc()};
            //2.调用QueryRunner对象中的方法update,执行添加数据的sql语句
            qr.update(sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
        修改(更新)分类信息方法
        修改:update
        UPDATE gjp_sort SET parent=?,sname=?,sdesc=? WHERE sid=?
        参数:传递修改的Sort对象
        返回值:无
     */
    public void editSort(Sort sort){
        try {
            //1.创建QueryRunner对象
            QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
            //拼接sql语句
            String sql = "UPDATE gjp_sort SET parent=?,sname=?,sdesc=? WHERE sid=?";
            //定义数组,存储?占位符的实际数据
            Object[] params = {sort.getParent(),sort.getSname(),sort.getSdesc(),sort.getSid()};
            //2.调用QueryRunner对象中的方法update,执行添加数据的sql语句
            qr.update(sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
        删除分类信息的方法
        删除:delete
            DELETE FROM gjp_sort WHERE sid = ?
        参数: int sid
        返回值:无
     */
    public void deleteSort(int sid){
        try {
            //1.创建QueryRunner对象
            QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
            //拼接sql语句
            String sql = "DELETE FROM gjp_sort WHERE sid = ?";
            //定义数组,存储?占位符的实际数据
            Object[] params = {sid};
            //2.调用QueryRunner对象中的方法update,执行添加数据的sql语句
            qr.update(sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
        查询所有分类名称功能
        查询:select
            SELECT sname FROM gjp_sort;
        参数: 无
        返回值:List<Object>
     */
    public List<Object> queryAllSname(){
        try {
            //1.创建QueryRunner对象
            QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
            //2.调用QueryRunner对象中的方法query执行查询的sql语句,获取查询的结果
            String sql = "SELECT sname FROM gjp_sort;";
            List<Object> list = qr.query(sql, new ColumnListHandler());
            //3.把结果返回到service层
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //没有查询到数据
        return null;
    }

    /*
        根据父分类信息,查询分类名称
        参数: String parentName 父分类名称(收入|支出)
        返回值:List<Object>
     */
    public List<Object> querySnameByParentName(String parentName){
        try {
            //1.创建QueryRunner对象
            QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
            //2.调用QueryRunner对象中的方法query执行查询的sql语句,获取查询的结果
            String sql = "SELECT sname FROM gjp_sort WHERE parent = ?;";
            List<Object> list = qr.query(sql, new ColumnListHandler(),parentName);
            //3.把结果返回到service层
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //没有查询到数据
        return null;
    }

    /*
        通过sname找到对应的sid
        参数 String sname
        返回值 int
     */
    public int getSidBySname(String sname){
        try {
            //1.创建QueryRunner对象
            QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
            //2.调用QueryRunner对象中的方法query执行查询的sql语句,获取查询的结果
            String sql = "SELECT sid FROM gjp_sort WHERE sname =?";
            Object obj = qr.query(sql, new ScalarHandler(), sname);
            //向下转型把Object类型的数据转换为Integer
            Integer sid = (Integer)obj;
            //3.把结果返回到service层
            return sid;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //没有查询到数据
        return 0;
    }
}
