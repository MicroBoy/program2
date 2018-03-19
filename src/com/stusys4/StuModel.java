/**
 * �����Զ����һ��ѧ������ģ�͡������Ե���һ������ʹ�ã�������TableModel������̳�AbstractTableModel��
 *                  �����ݿ�Ĳ���
 * 1.��ѯ
 * 2.����ģ�͸���
 */
package com.stusys4;

import java.sql.*;
import java.util.*;

import javax.swing.table.*;

public class StuModel extends AbstractTableModel{

	//�������е�����
	//���캯��-->��ʼ������ģ��StuModel
	Vector rowData,columnName;  //ʹ��setModel()ȡ����JTable(rowData,columnName)   Vector�������������ࡿ
	SqlHelper sqlHelper=null;
	
	//�����ѧ�������ݿ������ֲ����
	public boolean updStu(String sql,String[] paras)//��ִ���˶����ݿ�Ĳ����ֿɷ��ز���ֵ
	{               //����������������ɾ���� 
		//����һ��SqlHelper������������Ǵ���Ĳ����ԣ����԰�SqlHelperд��static,�������ɵ�̬��
		sqlHelper=new SqlHelper();
		return sqlHelper.updExecute(sql, paras);
	}
	
	//�޲�����ʼ�����캯��,��������Ϊ�˴���������������еķ�����
	public StuModel()
	{
		
	}

	//��ѯ����
	public void queryStu(String sql,String[] paras)
	{
		//�����м��  JTable
		//������
		columnName=new Vector();
		columnName.add("ѧ��");
		columnName.add("����");
		columnName.add("�Ա�");
		columnName.add("����");
		columnName.add("����");
		columnName.add("ϵ��");
						
		//�������ݿ��е�������
		rowData=new Vector();//����
		try {
			sqlHelper=new SqlHelper();
			ResultSet rs=sqlHelper.queryExecute(sql, paras);
			//ѭ��ȡ��
			while(rs.next())
			{
				Vector row=new Vector();
				row.add(rs.getString(1));
				row.add(rs.getString(2));
				row.add(rs.getString(3));
				row.add(rs.getInt(4));
				row.add(rs.getString(5));
				row.add(rs.getString(6));
				rowData.add(row);	
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			//�ر���Դ
			sqlHelper.close();
		}
			
	}	
	
	
	//����ķ������Զ�����
	@Override
	//�õ����ж�����
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this.columnName.size();
	}

	@Override
	//�õ����ж�����
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.rowData.size();
	}

	@Override
	//�õ�ĳ��ĳ�е�����
	public Object getValueAt(int row, int column) {
		// TODO Auto-generated method stub
		return ((Vector)this.rowData.get(row)).get(column);  
		//this.rowData.get(row)�Ƕ���һ��ֵ��Ҫת�ɶ��ж�������
		//����Ҫ��this.rowData.get(row)ת������
	}

	@Override
	//�õ�����
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return (String)this.columnName.get(column);
	}

}
