package cn.itcast.gjp.controller;

import cn.itcast.gjp.domain.Sort;
import cn.itcast.gjp.service.SortService;
import cn.itcast.gjp.view.AbstractOperationSortDialog;

import javax.swing.*;

/*
    添加分类界面
 */
public class AddSortcontroller extends AbstractOperationSortDialog {
    public AddSortcontroller(JDialog dialog) {
        super(dialog);
        /*
            不用记属于GUI
            1.设置窗口标题
            2.设置标题标签
         */
        this.setTitle("添加分类对话框");
        this.titleLabel.setText("添加分类");
    }

    @Override
    public void confirm() {
        System.out.println("点击确认按钮");
        //1.获取录入的数据 固定的格式(GUI)
        //父分类信息
        String parent = parentBox.getSelectedItem().toString();
        //分类名称信息
        String sname = snameTxt.getText();
        //获取分类说明信息
        String sdesc = sdescArea.getText();

        //2.对表单数据进行校验
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

        //3.将数据封装到sort对象中
        Sort sort = new Sort();
        sort.setParent(parent);
        sort.setSname(sname);
        sort.setSdesc(sdesc);

        //4.将数据 传递给service
        SortService service = new SortService();
        service.addSort(sort);

        //5.弹出确认框
        JOptionPane.showMessageDialog(this,"添加成功!");
        //关闭添加界面
        this.dispose();
    }
}
