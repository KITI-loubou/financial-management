package cn.itcast.gjp.service;

import cn.itcast.gjp.dao.SortDao;
import cn.itcast.gjp.domain.Sort;

import java.util.List;

/*
    业务层,服务层(承上启下)
    主要完成一些业务的处理
    调用dao层的方法获取数据,把数据返回到controller层
 */
public class SortService {
     /*
        定义一个查询所有分类信息的方法
        方法的参数:查询所有数据,不需要参数
        方法的返回值:List<Sort>
     */
     public List<Sort> querySortAll(){
         //创建Dao对象,调用Dao中的querySortAll方法,获取查询的数据
         SortDao dao = new SortDao();
         List<Sort> list = dao.querySortAll();
         //把数据返回到controller层
         return list;
     }

     /*
        定义一个addSort方法
        参数:Sort
        返回值:无
      */
     public void addSort(Sort sort){
         //创建SortDao对象,调用addSort方法
         SortDao dao = new SortDao();
         dao.addSort(sort);
     }

    /*
       修改(更新)分类信息方法
       参数:传递修改的Sort对象
       返回值:无
    */
    public void editSort(Sort sort){
        //创建SortDao对象,调用editSort方法
        SortDao dao = new SortDao();
        dao.editSort(sort);
    }

    /*
        删除分类信息的方法
        参数: int sid
        返回值:无
     */
    public void deleteSort(int sid){
        //创建SortDao对象,调用deleteSort方法
        SortDao dao = new SortDao();
        dao.deleteSort(sid);
    }

    /*
        查询所有分类名称功能
        参数: 无
        返回值:List<Object>
     */
    public List<Object> queryAllSname(){
        //创建SortDao对象
        SortDao dao = new SortDao();
        //调用SortDao对象中的queryAllSname方法,获取所有的分类信息
        List<Object> list = dao.queryAllSname();
        //把List集合返回到controller层
        return list;
    }

    /*
        根据父分类信息,查询分类名称
        参数: String parentName 父分类名称(收入|支出)
        返回值:List<Object>
     */
    public List<Object> querySnameByParentName(String parentName){
        //创建SortDao对象
        SortDao dao = new SortDao();
        //调用SortDao对象中的querySnameByParentName方法,获取所有的分类信息
        List<Object> list = dao.querySnameByParentName(parentName);
        //把List集合返回到controller层
        return list;
    }
}
