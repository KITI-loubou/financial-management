package cn.itcast.gjp.controller;

import cn.itcast.gjp.domain.Ledger;
import cn.itcast.gjp.service.LedgerService;
import cn.itcast.gjp.service.SortService;
import cn.itcast.gjp.view.AbstractOperationLedgerDialog;

import javax.swing.*;
import java.util.List;

/*
    添加账务界面
 */
public class AddLedgerController extends AbstractOperationLedgerDialog{
    //带参数构造方法
    public AddLedgerController(JDialog dialog) {
        super(dialog);
        /*
            设置添加账务界面的标题
         */
        this.setTitle("添加账务对话框");
        this.titleLabel.setText("添加账务");
    }

    @Override
    public void changeParent() {
        System.out.println("添加账务信息级联!");
        //1.创建SortService对象
        SortService service = new SortService();
        //2.获取用户选择的"收/支"情况
        String parent = this.parentBox.getSelectedItem().toString();
        //3.判断用户选择的是哪一种"收/支"情况
        if("-请选择-".equals(parent)){
            Object[] sortItems = {"-请选择-"};
            //设置分类中的选项
            sortBox.setModel(new DefaultComboBoxModel(sortItems));
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

    @Override
    public void confirm() {
        System.out.println("添加界面中的确认按钮!");
        //1.获取界面上用户填写的所有的数据
        String parent = parentBox.getSelectedItem().toString();
        String sname = sortBox.getSelectedItem().toString();
        String account = accountTxt.getText();
        String money = moneyTxt.getText();
        String createtime = createtimeTxt.getText();
        String ldesc = ldescTxt.getText();
        //2.进行校验
        if("-请选择-".equals(parent)){
            JOptionPane.showMessageDialog(this,"请您选择收入或者支出!");
            return;
        }
        if("-请选择-".equals(sname)){
            JOptionPane.showMessageDialog(this,"请您选择对应的分类!");
            return;
        }
        if(account.equals("") ){
            JOptionPane.showMessageDialog(this,"账务不能为空!");
            return;
        }
        if(money.equals("")){
            JOptionPane.showMessageDialog(this,"金额不能为空!");
            return;
        }
        //3.把数据封装到一个Ledger对象中
        Ledger ledger = new Ledger();
        ledger.setParent(parent);
        ledger.setSname(sname);
        ledger.setAccount(account);
        //Double.parseDouble(money):把字符串的小数,转换为double类型
        ledger.setMoney(Double.parseDouble(money));
        ledger.setCreatetime(createtime);
        ledger.setLdesc(ldesc);
        //4.调用service层addLedger方法,传递Ledger对象
        LedgerService service = new LedgerService();
        service.addLedger(ledger);
        //5.弹出添加成功的提示框
        JOptionPane.showMessageDialog(this,"添加成功!");
        //6.关闭添加界面
        this.dispose();
    }
}
