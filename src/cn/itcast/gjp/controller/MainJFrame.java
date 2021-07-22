package cn.itcast.gjp.controller;

import cn.itcast.gjp.view.AbstractMainFrame;

public class MainJFrame extends AbstractMainFrame{
    @Override
    public void ledgerMng() {
        System.out.println("财务管理");
        //点击财务管理按钮,弹出财务管理界面
        new LedgerMngController(this).setVisible(true);
    }

    @Override
    public void sortMng() {
        System.out.println("分类管理");
        //点击分类管理按钮,弹出分类管理界面
        //创建SortMngcontroller对象,并设置界面可见
        //对象只使用一次,可以使用匿名对象
        new SortMngcontroller(this).setVisible(true);
    }
}
