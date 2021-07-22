package cn.itcast.gjp.service;

import cn.itcast.gjp.dao.LedgerDao;
import cn.itcast.gjp.dao.SortDao;
import cn.itcast.gjp.domain.Ledger;

import java.util.List;

/*
    作用:
        调用dao层的方法,获取结果,把结果返回到controller层
 */
public class LedgerService {
    //在成员位置创建LedgerDao对象,供所有的方法
    LedgerDao dao = new LedgerDao();

    /*
        查询所有账务信息的方法
        参数:无
        返回值:List<Ledger>
     */
    public List<Ledger> queryAllLedger(){
        //调用dao中queryAllLedger方法,获取查询结果,把结果返回到controller层
        List<Ledger> list = dao.queryAllLedger();
        return list;
    }

    /*
        添加账务信息的方法
        参数:Ledger对象
        返回值:无
     */
    public void addLedger(Ledger ledger){
        //通过sname查询sid
        SortDao sortDao = new SortDao();
        int sid = sortDao.getSidBySname(ledger.getSname());
        //把sid添加到Ledger对象中
        ledger.setSid(sid);
        //调用LedgerDao类中的方法addLedger添加账务信息
        dao.addLedger(ledger);
    }

    /*
       编辑账务的方法
       参数: Ledger对象
       返回值:无
    */
    public void editLedger(Ledger ledger){
        //通过sname查询sid
        SortDao sortDao = new SortDao();
        int sid = sortDao.getSidBySname(ledger.getSname());
        //把sid添加到Ledger对象中
        ledger.setSid(sid);
        //调用LedgerDao类中的方法editLedger方法更新数据
        dao.editLedger(ledger);
    }

    /*
        根据lid删除账务信息
        参数: int lid
        返回值: 无
     */
    public void deleteLedgerById(int lid){
        dao.deleteLedgerById(lid);
    }
    /*
        定义多条件查询的方法
        参数:四个 开始时间,截止时间,父分类,分类名称
        返回值:List<Ledger>
     */
    public List<Ledger> queryLedger(String begintime,String endtime,String parent,String sname){
        List<Ledger> list = dao.queryLedger(begintime, endtime, parent, sname);
        return list;
    }
}
