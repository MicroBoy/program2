/**
 * 添加对话框  super(owner,title,model)调用父类方法
 */
package com.stusys4;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class StuAddDialog extends JDialog implements ActionListener{
	//定义窗体组件
	JLabel jl1,jl2,jl3,jl4,jl5,jl6;
	JTextField jtf1,jtf2,jtf3,jtf4,jtf5,jtf6;
	JPanel jp1,jp2,jp3;
	JButton jb1,jb2;
			
	//构造函数初始化
	//owner>从哪个窗体引申来的；title>对话框名字；//model>指定模式还是非模式及是否锁定窗体 true/false
	public StuAddDialog(Frame owner,String title,boolean model)   
	{                                                         
		//首先调用父类方法，初始化窗体
		super(owner, title, model);
		
		//创建组件
		jl1=new JLabel("学号");
		jl2=new JLabel("姓名");
		jl3=new JLabel("性别");
		jl4=new JLabel("年龄");
		jl5=new JLabel("籍贯");
		jl6=new JLabel("系别");
		
		jtf1=new JTextField();
		jtf2=new JTextField();
		jtf3=new JTextField();
		jtf4=new JTextField();
		jtf5=new JTextField();
		jtf6=new JTextField();
		
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		
		jb1=new JButton("添加");
		jb1.addActionListener(this);
		jb2=new JButton("取消");
		jb2.addActionListener(this);
		
		//总共将面板划分为三个功能块 在默认的边界布局上划分的-->西边、中间、南边
		jp1.setLayout(new GridLayout(6, 1));
		jp2.setLayout(new GridLayout(6, 1));
		
		//添加组件
		//西边
		jp1.add(jl1);
		jp1.add(jl2);
		jp1.add(jl3);
		jp1.add(jl4);
		jp1.add(jl5);
		jp1.add(jl6);
		//中间
		jp2.add(jtf1);
		jp2.add(jtf2);
		jp2.add(jtf3);
		jp2.add(jtf4);
		jp2.add(jtf5);
		jp2.add(jtf6);
		//南边
		jp3.add(jb1);
		jp3.add(jb2);
		
		this.add(jp1,BorderLayout.WEST);
		this.add(jp2,BorderLayout.CENTER);
		this.add(jp3,BorderLayout.SOUTH);
		
		this.setSize(300, 200);
		this.setVisible(true);
//不能再用this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jb1)
		{
			String sql="insert into stu values(?,?,?,?,?,?)";
			String[] paras={this.jtf1.getText(),this.jtf2.getText(),
					this.jtf3.getText(),this.jtf4.getText(),this.jtf5.getText(),this.jtf6.getText()};
			//添加
			StuModel sm=new StuModel();
			if(!sm.updStu(sql, paras))//会返回布尔值
			{
				//提示
				JOptionPane.showMessageDialog(this, "添加失败");
				//return ;
			}
			//关闭此添加对话框
			this.dispose();
			
		}
		else if(e.getSource()==jb2)
		{
			//关闭此添加对话框
			this.dispose();
		}
	}

}
