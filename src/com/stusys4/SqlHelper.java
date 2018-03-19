/**
 * ��StuModel�ж����ݿ�Ĳ����������
 */
package com.stusys4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class SqlHelper {
	//�������е�����
	//���캯��-->��ʼ������ģ��StuModel
	Vector rowData,columnName;  //ʹ��setModel()ȡ����JTable(rowData,columnName)   Vector�������������ࡿ
	PreparedStatement ps=null;
	Connection ct=null;
	ResultSet rs=null;
	String driver="com.microsoft.jdbc.sqlserver.SQLServerDriver";
	String url="jdbc:microsoft:sqlserver://127.0.0.1:1433;databaseName=spdb1";
	String user="sa";
	String passwd="jinpeng";
	
	//�ر����ݿ���Դ
	public void close()
	{
		try {
			if(rs!=null) 
			{
				rs.close();
			}
			if(ps!=null)
			{
				ps.close();
			}
			if(ct!=null)
			{
				ct.close();
			}
		} catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
		}
	}
	
	//��ɾ�ķ���һ��������
	public boolean updExecute(String sql,String[] paras)
	{
		boolean b=true;
		
		try {
			//��������
			Class.forName(driver);
			//ȡ������
			ct=DriverManager.getConnection(url,user,passwd);
			//����ps->��Ϊ�˷���SQL���
			ps=ct.prepareStatement(sql);
			//��sql�еģ���ֵ
			for(int i=0;i<paras.length;i++)
			{
				ps.setString(i+1, paras[i]);//���͡�ʱ���ͻ��Զ�ת����String
			}
			//ִ�в���
			//int pe=ps.executeUpdate();
			
			if(ps.executeUpdate()!=1)//�����������
			{
				b=false;//δ��ӳɹ�
			}
		} catch (Exception e) {
			// TODO: handle exception
			b=false;//�׳��쳣��δ��ӳɹ�
			e.printStackTrace();
		}finally{
			//�ر���Դ
			this.close();
		}
		return b;
	}
	
	//��ѯ����
	public ResultSet queryExecute(String sql,String []paras)
	{
		try {
			Class.forName(driver);
			ct=DriverManager.getConnection(url,user,passwd);
			ps=ct.prepareStatement(sql);
			//��sql�еģ���ֵ
			for(int i=0;i<paras.length;i++)
			{
				ps.setString(i+1, paras[i]);//���͡�ʱ���ͻ��Զ�ת����String
			}
			rs=ps.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			//���ص��ǽ���������Թر���Դ���ڵ��ô�
		}
		
		return rs;
	}
}
