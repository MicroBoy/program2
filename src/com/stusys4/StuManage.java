/**
 * 作者：金鹏
 * 功能：1.通过面板对表中的数据 查询---->迷你学生管理系统   MIS(management  information system:信息管理系统)
 *                     (注册监听)
 *                     (表的数据模型)
 *     2.添加
 *     3.修改
 *     4.删除
 * 日期：2017/8/2
 *                 整合代码，使其【高内聚，低耦合】
 */
package com.stusys4;

import java.sql.*;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.*;

public class StuManage extends JFrame implements ActionListener{
	//定义组件
	JPanel jp1,jp2;
	JLabel jl=null;
	JTextField jtf=null;
	JButton jb1,jb2,jb3,jb4;
	JTable jt=null;
	JScrollPane jsp=null;

	//全局定义，可以防止内存泄漏；
	//局部定义，用完一次后虽然不被再用，但建立的引用依然存在，垃圾回收机制无法回收
	StuModel sm=null;
	
	//定义数据库
	Connection ct=null;
	PreparedStatement ps=null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StuManage stu=new StuManage();
	}
	
	//构造函数
	public StuManage()
	{
		//JPanel默认是：边界布局
		//创建北边
		jp1=new JPanel();
		jl=new JLabel("请输入名字");
		jtf=new JTextField(10);
		jb1=new JButton("查询");
		jb1.addActionListener(this);//注册监听
		//边界上默认是流布局
		jp1.add(jl);
		jp1.add(jtf);
		jp1.add(jb1);
		
		//创建南边
		jp2=new JPanel();
		jb2=new JButton("添加");
		jb2.addActionListener(this);
		jb3=new JButton("修改");
		jb3.addActionListener(this);
		jb4=new JButton("删除");
		jb4.addActionListener(this);
		//边界上默认是流布局
		jp2.add(jb2);
		jp2.add(jb3);
		jp2.add(jb4);
		
		//创建中间的  JTable
		//取出表数据，只要new个数据模型就行
		sm=new StuModel();   //StuModel(TableModel dataModel)方法可以返回行、列、数据
		String sql="select * from stu where 1=?";
		String []paras={"1"};
		sm.queryStu(sql, paras);
		
		jt=new JTable(sm);
		jsp=new JScrollPane(jt);

		//这里的this是指StuManage类或者它new出的对象
		this.add(jsp);
		
		//对边界布局指定位置添加组件有两种方法：
		this.add(jp1,BorderLayout.NORTH);
		this.add(jp2,"South");

		this.setTitle("学生管理系统");		
		this.setSize(400, 300);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//判断哪个按钮被按下
		//查询按钮jb1和响应函数actionPerformed在一个类里面可以用getSource()方式
		if(e.getSource()==jb1)
		{
			//查询
			//将表的数据封装到StuModel中后，查询就简单了
			//1.得到输出框中的内容
			String name=this.jtf.getText().trim();   //trim()屏蔽空字符串
			//2.写sql语句  【模糊查询  精确查询√】
			String sql="select * from stu where stuName=?";    //where stuName='name'
			String []paras={name};
			//3.传递SQL语句并更新     模型就要重新写成StuModel(Stirng sql)
			sm=new StuModel();
			//调用查询方法
			sm.queryStu(sql, paras);
			jt.setModel(sm);
		}
		else if(e.getSource()==jb2)
		{
			//添加
			//通过一个新的对话框添加一行数据
			//1.实例一个对话框
			//2.得到用户输入，并加入数据库
			StuAddDialog sad=new StuAddDialog(this, "添加学生", true);//查看JDialog的jdk帮助文档
			
			//3.更新数据模型
			sm=new StuModel();
			String sql="select * from stu where 1=?";
			String []paras={1+""};
			sm.queryStu(sql, paras);
			jt.setModel(sm);
		}
		else if(e.getSource()==jb3)
		{
			//修改
			//通过一个新对话框修改选中行数据
			//1.取得选中行号
			int rowNum=this.jt.getSelectedRow();
			if(rowNum==-1)
			{
				JOptionPane.showMessageDialog(this, "请选中一行");
				return ;
			}
			//2.显示修改对话框
			new StuUpdDialog(this, "修改数据", true, sm, rowNum);
			
			//3.更新数据模型
			sm=new StuModel();
			String sql="select * from stu where 1=?";
			String []paras={1+""};
			sm.queryStu(sql, paras);
			jt.setModel(sm);
		}
		else if(e.getSource()==jb4)
		{
			//删除
			//鼠标点中某一行，我们可以拿到该行的第一列(主键)
			//1.得到点中行号
			   //JTable的方法getSelectedRow()：返回第一个选定行的索引(即选中的行号)；如果没有选定的行，则返回 -1
			int rowNum=this.jt.getSelectedRow();
			if(rowNum==-1)
			{
				//提示选中要删除的行
				JOptionPane.showMessageDialog(this, "请选中一行");
				return ;//返回在哪响应的地方
			}
			//2.得到学生ID
			String id=(String)sm.getValueAt(rowNum, 0);
			//3.操作数据库删除
			String sql="delete from stu where stuId=?";
			String[] paras={id};
			StuModel temp=new StuModel();//【主要是为了得到其中的方法，但它会附带一次无用的查询】？？？？
			temp.updStu(sql, paras);                                 //此时没有无用的查询
			
			//4.更新数据模型
			sm=new StuModel();
			String sql1="select * from stu where 1=?";
			String []paras1={"1"};//注入的数据
			sm.queryStu(sql1, paras1);
			//【每次输入没有注入的sql语句时，都这么麻烦？？？？-->写两个构造方法：StuModel()、StuModel(String sql)】
			jt.setModel(sm);
		}
	}

}











