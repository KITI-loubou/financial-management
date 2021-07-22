package cn.itcast.gjp.controller;

import cn.itcast.gjp.domain.Sort;
import cn.itcast.gjp.service.SortService;
import cn.itcast.gjp.view.AbstractOperationSortDialog;

import javax.swing.*;

/*
    编辑分类信息界面
 */
public class EditSortcontroller extends AbstractOperationSortDialog{
    //定义一个Sort类型的成员变量
    private Sort sort;

    public EditSortcontroller(JDialog dialog,Sort sort) {
        super(dialog);

        //给成员变量sort赋值用户传递过来的分类信息
        this.sort = sort;

        /*
            不用记属于GUI
            1.设置窗口标题
            2.设置标题标签
         */
        this.setTitle("编辑分类对话框");
        this.titleLabel.setText("编辑分类");

        //需要把选中的这行信息的原始内容,显示到编辑的界面中
        //取出Sort对象中的数据
        String parent = sort.getParent();//父分类
        String sname = sort.getSname();//分类名称
        String sdesc = sort.getSdesc();//分类描述
        System.out.println("父分类:"+parent);
        System.out.println("分类名称:"+sname);
        System.out.println("分类描述:"+sdesc);
        //将获取到的内容显示到分类界面上
        parentBox.setSelectedItem(parent);
        snameTxt.setText(sname);
        sdescArea.setText(sdesc);
    }

    @Override
    public void confirm() {
        System.out.println("编辑界面的确认按钮!");
        //1.获取用户修改后的表单数据
        String parent = parentBox.getSelectedItem().toString();
        String sname = snameTxt.getText();
        String sdesc = sdescArea.getText();

        //2.对数据进行校验
        //判断父分类是否为请选择
        if("=请选择=".equals(parent)){
            //弹出一个提示框
            JOptionPane.showMessageDialog(this,"请选择父分类!");
            return;//结束添加方法
        }
        //判断分类名称是否填写
        if(sname.trim().isEmpty()){
            //弹出一个提示框
            JOptionPane.showMessageDialog(this,"分类名称不能为空!");
            return;//结束添加方法
        }

        //3.把用户修改后的数据更新到Sort对象中
        sort.setParent(parent);
        sort.setSname(sname);
        sort.setSdesc(sdesc);

        //4.调用SortService中的editSort方法
        SortService service = new SortService();
        service.editSort(sort);

        //5.关闭对话框
        this.dispose();

        //6.弹出编辑成功界面
        JOptionPane.showMessageDialog(this,"编辑成功!");
    }
}
