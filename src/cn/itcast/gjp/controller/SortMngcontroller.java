package cn.itcast.gjp.controller;

import cn.itcast.gjp.domain.Sort;
import cn.itcast.gjp.service.SortService;
import cn.itcast.gjp.view.AbstractSortMngDialog;

import javax.swing.*;
import java.util.List;

public class SortMngcontroller extends AbstractSortMngDialog{
    //带参数的构造方法
    public SortMngcontroller(JFrame frame) {
        //调用父类的构造方法
        super(frame);
        //调用SortService中的方法querySortAll,获取查询的所有数据
        SortService service = new SortService();
        List<Sort> list = service.querySortAll();
        //把数据展示到分类界面的表格中
        setTableModel(list);
    }

    @Override
    public void addSort() {
        System.out.println("添加按钮对应的功能!");
        /*
            弹出添加分类界面,设置可见
         */
        new AddSortcontroller(this).setVisible(true);

        //添加完数据之后,刷新表格,显示新添加的数据==>再一次查询所有的分类数据
        SortService service= new SortService();
        List<Sort> list = service.querySortAll();
        //把新的查询出来的数据,显示到分类界面上的表格中
        setTableModel(list);
    }

    @Override
    public void editSort() {
        System.out.println("编辑按钮对应的功能!");
        //1. 获取用户选择的表格行号
        int row = this.sortDataTable.getSelectedRow();

        //2. 判断行号是否小于0，如果小于0说明用户没有选择
        if(row<0){
            JOptionPane.showMessageDialog(this,"请选择要编辑的分类!");
            return;//结束方法
        }

        //3. 通过表格行号获取表格中该行数据封装到Sort对象中（要实现这一功能需要使用JTable的一些复杂功能，本项目中已经把这一功能封装好了，我们只需要调用方法即可。）
        Sort sort = this.getSortByTableRow(row);

        //4. 判断获取的Sort对象是否为null，如果为null说明用户选择的是空行
        if(sort==null){
            JOptionPane.showMessageDialog(this,"您不能选择空行!");
            return;//结束方法
        }

        //5. 创建EditSortcontroller对话框，并把Sort对象传递给EditSortcontroller的构造器
        new EditSortcontroller(this,sort).setVisible(true);
        //6. 用户编辑结束后，回到这里，我们再查询所有分类设置给JTable，作用是刷新JTable中的分类信息。
        SortService service= new SortService();
        List<Sort> list = service.querySortAll();
        //把新的查询出来的数据,显示到分类界面上的表格中
        setTableModel(list);
    }

    @Override
    public void deleteSort() {
        System.out.println("删除按钮对应的功能!");
        //1.判断用户是否选择要删除的表格记录
        int row = this.sortDataTable.getSelectedRow();
        if(row<0){
            JOptionPane.showMessageDialog(this,"请选择要删除的分类!");
            return;//结束方法
        }
        //2.获取用户选择的表格记录
        Sort sort = this.getSortByTableRow(row);

        //3.判断用户选择的是否为空行
        if(sort==null){
            JOptionPane.showMessageDialog(this,"别拿空格糊弄我!");
            return;//结束方法
        }
        //4.判断用户是否真的要删除记录
        int m = JOptionPane.showConfirmDialog(this,"您是否确定要删除该行数据?");
        System.out.println("是否确定删除:"+m);
        if(m!=0){
            return;//用户选择否|取消,不删除数据,结束删除的方法
        }
        //5.调用SortService#deleteSort()方法删除记录
        SortService service = new SortService();
        service.deleteSort(sort.getSid());

        //6.刷新表格数据
        List<Sort> list = service.querySortAll();
        //把新的查询出来的数据,显示到分类界面上的表格中
        setTableModel(list);
    }
}
