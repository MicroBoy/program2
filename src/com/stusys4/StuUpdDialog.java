/**
 * �޸ĶԻ��������ֱ�Ӵ���ӶԻ��򿽱������ģ���Щ�ط�Ҫ��
 */
package com.stusys4;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class StuUpdDialog extends JDialog implements ActionListener{
	//���崰�����
	JLabel jl1,jl2,jl3,jl4,jl5,jl6;
	JTextField jtf1,jtf2,jtf3,jtf4,jtf5,jtf6;
	JPanel jp1,jp2,jp3;
	JButton jb1,jb2;
			
	//���캯����ʼ��
	//owner>���ĸ������������ģ�title>�Ի������֣�//model>ָ��ģʽ���Ƿ�ģʽ���Ƿ��������� true/false
	public StuUpdDialog(Frame owner,String title,boolean model,StuModel sm,int rowNum)   
	{                                                             //�������ⲿ����������ϵ
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
		//��ʼ���ı���
		jtf1.setText((String)sm.getValueAt(rowNum, 0));
		jtf1.setEditable(false);//�������޸�
		jtf2=new JTextField();
		jtf2.setText((String)sm.getValueAt(rowNum, 1));
		jtf3=new JTextField();
		jtf3.setText((String)sm.getValueAt(rowNum, 2));
		jtf4=new JTextField();
		jtf4.setText(sm.getValueAt(rowNum, 3)+"");//���β���ǿ��ת����String *.toString()
		jtf5=new JTextField();
		jtf5.setText((String)sm.getValueAt(rowNum, 4));
		jtf6=new JTextField();
		jtf6.setText((String)sm.getValueAt(rowNum, 5));
		
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		
		jb1=new JButton("�޸�");
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
		//3.����SQL���
		String sql="update stu set stuName=?,stuSex=?,stuAge=?,stuJg=?,stuDept=? where stuId=?";
		String[] paras={jtf2.getText(),jtf3.getText(),jtf4.getText(),jtf5.getText(),jtf6.getText(),jtf1.getText()};
				
		//����
		StuModel temp=new StuModel();
		temp.updStu(sql, paras);
		
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