package cn.itcast.gjp.controller;

import cn.itcast.gjp.domain.Ledger;
import cn.itcast.gjp.domain.Sort;
import cn.itcast.gjp.service.LedgerService;
import cn.itcast.gjp.service.SortService;
import cn.itcast.gjp.view.AbstractLedgerMngDialog;

import javax.swing.*;
import java.util.List;

public class LedgerMngController extends AbstractLedgerMngDialog{
    //在成员位置创建LedgerService对象,供所有的方法使用
    LedgerService service = new LedgerService();

    //带参数的构造方法
    public LedgerMngController(JFrame frame) {
        super(frame);
        //调用LedgerService对象中的方法queryAllLedger,获取查询的结果
        List<Ledger> list = service.queryAllLedger();
        //将所有的账务信息设置到账务的表格中
        setTableModel(list);

        //计算所有账务信息的总收入和总支出
        //遍历集合,获取每一个Ledger对象中的收入|支出的钱,进行累加求和
        //定义两个变量,一个记录总收入,一个记录总支出
        double inMoney = 0;
        double payMoney = 0;
        for (int i = 0; i < list.size(); i++) {
            Ledger ledger = list.get(i);
            //获取该条账务的父分类
            String parent = ledger.getParent();
            //获取该条账务的金额
            double money = ledger.getMoney();
            //判断父分类是收入还是支出
            if("收入".equals(parent)){
                //总收入累加
                inMoney+=money;
            }else if("支出".equals(parent)){
                //总支出累加
                payMoney+=money;
            }
        }
        //把总收入和总支出打印到控制台
        System.out.println("总收入:"+inMoney);
        System.out.println("总支出:"+payMoney);
        //把总收入和总支出设置到账务页面中
        inMoneyTotalLabel.setText("总收入: "+inMoney);
        payMoneyTotalLabel.setText("总支出: "+payMoney);
    }

    @Override
    public void addLedger() {
        System.out.println("添加账务!");
        //创建AddLedgerController对象,设置可见
        new AddLedgerController(this).setVisible(true);
    }

    @Override
    public void editLedger() {
        System.out.println("编辑账务!");
        System.out.println("编辑账务 信息");

        // 当点击编辑之后 要去检查 有没有选中 需要编辑的
        // 选中了才弹 没有选中 给提示 弹框提示
        // 获取被选中的行
        int row = ledgerDataTable.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(this, "请选择要编辑的数据");
            return;
        }
        // 根据行号 可以获取对象
        // 父类中有个方法 getLedagerByTableRow
        Ledger ledger = getLedgerByTableRow(row);

        //4. 判断获取的Sort对象是否为null，如果为null说明用户选择的是空行
        if(ledger==null){
            JOptionPane.showMessageDialog(this,"您不能选择空行!");
            return;//结束方法
        }
        //弹出编辑账务界面==>创建EditLedgerConroller对象,设置可见
        new EditLedgerConroller(this,ledger).setVisible(true);

        //如果编辑账务成功了,从新在刷新一次数据
        List<Ledger> list = service.queryAllLedger();
        //将所有的账务信息设置到表格中
        setTableModel(list);
    }

    @Override
    public void deleteLedger() {
        System.out.println("删除账务!");
        // 获取被选中的行
        int row = ledgerDataTable.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(this, "请选择要删除的数据");
            return;
        }
        // 根据行号 可以获取对象
        // 父类中有个方法 getLedagerByTableRow
        Ledger ledger = getLedgerByTableRow(row);

        //4. 判断获取的Sort对象是否为null，如果为null说明用户选择的是空行
        if(ledger==null){
            JOptionPane.showMessageDialog(this,"您不能选择空行!");
            return;//结束方法
        }

        //4.判断用户是否真的要删除记录
        int m = JOptionPane.showConfirmDialog(this,"您是否确定要删除该行数据?");
        System.out.println("是否确定删除:"+m);
        if(m!=0){
            return;//用户选择否|取消,不删除数据,结束删除的方法
        }
        //调用SortService#deleteLedgerById()方法删除记录
        service.deleteLedgerById(ledger.getLid());
       //删除成功,刷新表格数据
        List<Ledger> list = service.queryAllLedger();
        //将所有的账务信息设置到表格中
        setTableModel(list);
    }

    @Override
    public void queryLedger() {
        System.out.println("查询账务!");
        //获取界面上四个查询的参数
        String begintime = beginDateTxt.getText();
        String endtime = endDateTxt.getText();
        String parent = parentBox.getSelectedItem().toString();
        String sname = sortBox.getSelectedItem().toString();
        //调用service多条件查询的方法,获取查询的结果
        List<Ledger> list = service.queryLedger(begintime, endtime, parent, sname);
        //设置到表格中显示
        setTableModel(list);
    }

    @Override
    public void parentChange() {
        System.out.println("级联账务!");
        //1.创建SortService对象
        SortService service = new SortService();
        //2.获取用户选择的"收/支"情况
        String parent = this.parentBox.getSelectedItem().toString();
        //3.判断用户选择的是哪一种"收/支"情况
        if("-请选择-".equals(parent)){
            Object[] sortItems = {"-请选择-"};
            //设置分类中的选项
            sortBox.setModel(new DefaultComboBoxModel(sortItems));
        }else if("收入/支出".equals(parent)){
            //调用SortService对象中的方法queryAllSname获取所有的分类信息
            List<Object> list = service.queryAllSname();
            //在第一个位置添加一个"-请选择-"
            list.add(0,"-请选择-");
            //设置分类中的选项
            sortBox.setModel(new DefaultComboBoxModel(list.toArray()));
        }else{
            //选择"收入"或者选择"支出"
            //调用SortService对象中的方法querySnameByParentName获取对应的分类信息
            List<Object> list = service.querySnameByParentName(parent);
            //在第一个位置添加一个"-请选择-"
            list.add(0,"-请选择-");
            //设置分类中的选项
            sortBox.setModel(new DefaultComboBoxModel(list.toArray()));
        }
    }
}
