/**
 * ��ӶԻ���  super(owner,title,model)���ø��෽��
 */
package com.stusys4;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class StuAddDialog extends JDialog implements ActionListener{
	//���崰�����
	JLabel jl1,jl2,jl3,jl4,jl5,jl6;
	JTextField jtf1,jtf2,jtf3,jtf4,jtf5,jtf6;
	JPanel jp1,jp2,jp3;
	JButton jb1,jb2;
			
	//���캯����ʼ��
	//owner>���ĸ������������ģ�title>�Ի������֣�//model>ָ��ģʽ���Ƿ�ģʽ���Ƿ��������� true/false
	public StuAddDialog(Frame owner,String title,boolean model)   
	{                                                         
		//���ȵ��ø��෽������ʼ������
		super(owner, title, model);
		
		//�������
		jl1=new JLabel("ѧ��");
		jl2=new JLabel("����");
		jl3=new JLabel("�Ա�");
		jl4=new JLabel("����");
		jl5=new JLabel("����");
		jl6=new JLabel("ϵ��");
		
		jtf1=new JTextField();
		jtf2=new JTextField();
		jtf3=new JTextField();
		jtf4=new JTextField();
		jtf5=new JTextField();
		jtf6=new JTextField();
		
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		
		jb1=new JButton("���");
		jb1.addActionListener(this);
		jb2=new JButton("ȡ��");
		jb2.addActionListener(this);
		
		//�ܹ�����廮��Ϊ�������ܿ� ��Ĭ�ϵı߽粼���ϻ��ֵ�-->���ߡ��м䡢�ϱ�
		jp1.setLayout(new GridLayout(6, 1));
		jp2.setLayout(new GridLayout(6, 1));
		
		//������
		//����
		jp1.add(jl1);
		jp1.add(jl2);
		jp1.add(jl3);
		jp1.add(jl4);
		jp1.add(jl5);
		jp1.add(jl6);
		//�м�
		jp2.add(jtf1);
		jp2.add(jtf2);
		jp2.add(jtf3);
		jp2.add(jtf4);
		jp2.add(jtf5);
		jp2.add(jtf6);
		//�ϱ�
		jp3.add(jb1);
		jp3.add(jb2);
		
		this.add(jp1,BorderLayout.WEST);
		this.add(jp2,BorderLayout.CENTER);
		this.add(jp3,BorderLayout.SOUTH);
		
		this.setSize(300, 200);
		this.setVisible(true);
//��������this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jb1)
		{
			String sql="insert into stu values(?,?,?,?,?,?)";
			String[] paras={this.jtf1.getText(),this.jtf2.getText(),
					this.jtf3.getText(),this.jtf4.getText(),this.jtf5.getText(),this.jtf6.getText()};
			//���
			StuModel sm=new StuModel();
			if(!sm.updStu(sql, paras))//�᷵�ز���ֵ
			{
				//��ʾ
				JOptionPane.showMessageDialog(this, "���ʧ��");
				//return ;
			}
			//�رմ���ӶԻ���
			this.dispose();
			
		}
		else if(e.getSource()==jb2)
		{
			//�رմ���ӶԻ���
			this.dispose();
		}
	}

}
