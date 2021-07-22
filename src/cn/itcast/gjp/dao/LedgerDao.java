package cn.itcast.gjp.dao;

import cn.itcast.gjp.domain.Ledger;
import cn.itcast.gjp.tools.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import sun.security.util.Length;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
    和gjp_ledger表对应的dao
    可以对gjp_ledger进行增删改查
 */
public class LedgerDao {
    //创建QueryRunner对象,把QueryRunner提取到成员位置,所有的方法都可以使用
    QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());

    /*
        查询所有账务信息的方法
        参数:无
        返回值:List<Ledger>
     */
    public List<Ledger> queryAllLedger(){
        try {
            //拼接sql语句
            String sql = "SELECT l.lid,l.parent,s.sname,l.money,l.account," +
                    "l.createtime,l.ldesc FROM gjp_ledger l,gjp_sort s WHERE l.sid = s.sid;";
            //使用QueryRunner对象中的方法query执行查询的sql语句,获取查询的结果
            List<Ledger> list = qr.query(sql, new BeanListHandler<Ledger>(Ledger.class));
            //把集合返回到service层
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //没有查询到数据,返回null
        return null;
    }

    /*
        添加账务信息的方法
        参数:Ledger对象
        返回值:无
     */
    public void addLedger(Ledger ledger){
        try {
            //拼接sql数据
            String sql = "INSERT INTO gjp_ledger (parent,money,sid,account,createtime,ldesc)" +
                    "VALUES(?,?,?,?,?,?);";
            //定义Object类型的数组,存储?占位符对应的实际参数
            Object[] params = {ledger.getParent(),ledger.getMoney(),ledger.getSid(),ledger.getAccount(),ledger.getCreatetime(),ledger.getLdesc()};
            //调用QueryRunner对象中的方法update,执行insert语句
            qr.update(sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
        编辑账务的方法
        编辑:update
            UPDATE gjp_ledger SET parent=?,money=?,sid=?,account=?,createtime=?,ldesc=? WHERE lid = ?
        参数: Ledger对象
        返回值:无
     */
    public void editLedger(Ledger ledger){
        try {
            //拼接Sql语句
            String sql = "UPDATE gjp_ledger SET parent=?,money=?,sid=?,account=?,createtime=?,ldesc=? WHERE lid = ?";
            //定义Object类型的数组,存储?占位符对应的实际参数
            Object[] params = {ledger.getParent(),ledger.getMoney(),ledger.getSid(),ledger.getAccount(),ledger.getCreatetime(),ledger.getLdesc(),ledger.getLid()};
            //调用QueryRunner对象中的方法update,执行update语句
            qr.update(sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
        根据lid删除账务信息
        参数: int lid
        返回值: 无
     */
    public void deleteLedgerById(int lid){
        try {
            //拼接Sql语句
            String sql = "DELETE FROM gjp_ledger WHERE lid = ?";
            //定义Object类型的数组,存储?占位符对应的实际参数
            Object[] params = {lid};
            //调用QueryRunner对象中的方法update,执行delete语句
            qr.update(sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
        定义多条件查询的方法
        参数:四个 开始时间,截止时间,父分类,分类名称
        返回值:List<Ledger>
     */
    public List<Ledger> queryLedger(String begintime,String endtime,String parent,String sname){
        try {
            //sql语句是动态的,sql语句的参数也是动态的,会跟着sql语句变化而变化,把参数存储到集合中
            ArrayList<Object> list = new ArrayList<>();
            //拼接sql语句,条件到时间(必须的条件)这里
            String sql = "SELECT l.lid,l.parent,s.sname,l.money,l.account,l.createtime,l.ldesc FROM gjp_ledger l,gjp_sort s WHERE l.sid=s.sid "
                    + "AND l.createtime BETWEEN ? AND ?";
            list.add(begintime);
            list.add(endtime);
            //判断父分类用户是否选择
            if(parent.equals("收入")||parent.equals("支出")){
                //拼接上父分类
                sql+=" and l.parent = ?";
                list.add(parent);
            }
            //判断分类用户是否选择
            if(!sname.equals("-请选择-")){
                //拼接分类
                sql+=" and s.sname = ?";
                list.add(sname);
            }
            //执行sql语句
            Object[] params = list.toArray();
            List<Ledger> ledgers = qr.query(sql, new BeanListHandler<Ledger>(Ledger.class), params);
            //把集合返回到service层
            return ledgers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //没有查到到数据返回null
        return null;
    }
}
