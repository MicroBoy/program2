/**
 * ���ߣ�����
 * ���ܣ�1.ͨ�����Ա��е����� ��ѯ---->����ѧ������ϵͳ   MIS(management  information system:��Ϣ����ϵͳ)
 *                     (ע�����)
 *                     (�������ģ��)
 *     2.���
 *     3.�޸�
 *     4.ɾ��
 * ���ڣ�2017/8/2
 *                 ���ϴ��룬ʹ�䡾���ھۣ�����ϡ�
 */
package com.stusys4;

import java.sql.*;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.*;

public class StuManage extends JFrame implements ActionListener{
	//�������
	JPanel jp1,jp2;
	JLabel jl=null;
	JTextField jtf=null;
	JButton jb1,jb2,jb3,jb4;
	JTable jt=null;
	JScrollPane jsp=null;

	//ȫ�ֶ��壬���Է�ֹ�ڴ�й©��
	//�ֲ����壬����һ�κ���Ȼ�������ã���������������Ȼ���ڣ��������ջ����޷�����
	StuModel sm=null;
	
	//�������ݿ�
	Connection ct=null;
	PreparedStatement ps=null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StuManage stu=new StuManage();
	}
	
	//���캯��
	public StuManage()
	{
		//JPanelĬ���ǣ��߽粼��
		//��������
		jp1=new JPanel();
		jl=new JLabel("����������");
		jtf=new JTextField(10);
		jb1=new JButton("��ѯ");
		jb1.addActionListener(this);//ע�����
		//�߽���Ĭ����������
		jp1.add(jl);
		jp1.add(jtf);
		jp1.add(jb1);
		
		//�����ϱ�
		jp2=new JPanel();
		jb2=new JButton("���");
		jb2.addActionListener(this);
		jb3=new JButton("�޸�");
		jb3.addActionListener(this);
		jb4=new JButton("ɾ��");
		jb4.addActionListener(this);
		//�߽���Ĭ����������
		jp2.add(jb2);
		jp2.add(jb3);
		jp2.add(jb4);
		
		//�����м��  JTable
		//ȡ�������ݣ�ֻҪnew������ģ�;���
		sm=new StuModel();   //StuModel(TableModel dataModel)�������Է����С��С�����
		String sql="select * from stu where 1=?";
		String []paras={"1"};
		sm.queryStu(sql, paras);
		
		jt=new JTable(sm);
		jsp=new JScrollPane(jt);

		//�����this��ָStuManage�������new���Ķ���
		this.add(jsp);
		
		//�Ա߽粼��ָ��λ�������������ַ�����
		this.add(jp1,BorderLayout.NORTH);
		this.add(jp2,"South");

		this.setTitle("ѧ������ϵͳ");		
		this.setSize(400, 300);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//�ж��ĸ���ť������
		//��ѯ��ťjb1����Ӧ����actionPerformed��һ�������������getSource()��ʽ
		if(e.getSource()==jb1)
		{
			//��ѯ
			//��������ݷ�װ��StuModel�к󣬲�ѯ�ͼ���
			//1.�õ�������е�����
			String name=this.jtf.getText().trim();   //trim()���ο��ַ���
			//2.дsql���  ��ģ����ѯ  ��ȷ��ѯ�̡�
			String sql="select * from stu where stuName=?";    //where stuName='name'
			String []paras={name};
			//3.����SQL��䲢����     ģ�;�Ҫ����д��StuModel(Stirng sql)
			sm=new StuModel();
			//���ò�ѯ����
			sm.queryStu(sql, paras);
			jt.setModel(sm);
		}
		else if(e.getSource()==jb2)
		{
			//���
			//ͨ��һ���µĶԻ������һ������
			//1.ʵ��һ���Ի���
			//2.�õ��û����룬���������ݿ�
			StuAddDialog sad=new StuAddDialog(this, "���ѧ��", true);//�鿴JDialog��jdk�����ĵ�
			
			//3.��������ģ��
			sm=new StuModel();
			String sql="select * from stu where 1=?";
			String []paras={1+""};
			sm.queryStu(sql, paras);
			jt.setModel(sm);
		}
		else if(e.getSource()==jb3)
		{
			//�޸�
			//ͨ��һ���¶Ի����޸�ѡ��������
			//1.ȡ��ѡ���к�
			int rowNum=this.jt.getSelectedRow();
			if(rowNum==-1)
			{
				JOptionPane.showMessageDialog(this, "��ѡ��һ��");
				return ;
			}
			//2.��ʾ�޸ĶԻ���
			new StuUpdDialog(this, "�޸�����", true, sm, rowNum);
			
			//3.��������ģ��
			sm=new StuModel();
			String sql="select * from stu where 1=?";
			String []paras={1+""};
			sm.queryStu(sql, paras);
			jt.setModel(sm);
		}
		else if(e.getSource()==jb4)
		{
			//ɾ��
			//������ĳһ�У����ǿ����õ����еĵ�һ��(����)
			//1.�õ������к�
			   //JTable�ķ���getSelectedRow()�����ص�һ��ѡ���е�����(��ѡ�е��к�)�����û��ѡ�����У��򷵻� -1
			int rowNum=this.jt.getSelectedRow();
			if(rowNum==-1)
			{
				//��ʾѡ��Ҫɾ������
				JOptionPane.showMessageDialog(this, "��ѡ��һ��");
				return ;//����������Ӧ�ĵط�
			}
			//2.�õ�ѧ��ID
			String id=(String)sm.getValueAt(rowNum, 0);
			//3.�������ݿ�ɾ��
			String sql="delete from stu where stuId=?";
			String[] paras={id};
			StuModel temp=new StuModel();//����Ҫ��Ϊ�˵õ����еķ����������ḽ��һ�����õĲ�ѯ����������
			temp.updStu(sql, paras);                                 //��ʱû�����õĲ�ѯ
			
			//4.��������ģ��
			sm=new StuModel();
			String sql1="select * from stu where 1=?";
			String []paras1={"1"};//ע�������
			sm.queryStu(sql1, paras1);
			//��ÿ������û��ע���sql���ʱ������ô�鷳��������-->д�������췽����StuModel()��StuModel(String sql)��
			jt.setModel(sm);
		}
	}

}











