/**
 * 将StuModel中对数据库的操作抽象出来
 */
package com.stusys4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class SqlHelper {
	//操作表中的数据
	//构造函数-->初始化数据模型StuModel
	Vector rowData,columnName;  //使用setModel()取代了JTable(rowData,columnName)   Vector行向量【集合类】
	PreparedStatement ps=null;
	Connection ct=null;
	ResultSet rs=null;
	String driver="com.microsoft.jdbc.sqlserver.SQLServerDriver";
	String url="jdbc:microsoft:sqlserver://127.0.0.1:1433;databaseName=spdb1";
	String user="sa";
	String passwd="jinpeng";
	
	//关闭数据库资源
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
	
	//增删改放在一个函数中
	public boolean updExecute(String sql,String[] paras)
	{
		boolean b=true;
		
		try {
			//加载驱动
			Class.forName(driver);
			//取得连接
			ct=DriverManager.getConnection(url,user,passwd);
			//创建ps->是为了发送SQL语句
			ps=ct.prepareStatement(sql);
			//给sql中的？赋值
			for(int i=0;i<paras.length;i++)
			{
				ps.setString(i+1, paras[i]);//整型、时间型会自动转换成String
			}
			//执行操作
			//int pe=ps.executeUpdate();
			
			if(ps.executeUpdate()!=1)//无添加行数据
			{
				b=false;//未添加成功
			}
		} catch (Exception e) {
			// TODO: handle exception
			b=false;//抛出异常，未添加成功
			e.printStackTrace();
		}finally{
			//关闭资源
			this.close();
		}
		return b;
	}
	
	//查询操作
	public ResultSet queryExecute(String sql,String []paras)
	{
		try {
			Class.forName(driver);
			ct=DriverManager.getConnection(url,user,passwd);
			ps=ct.prepareStatement(sql);
			//给sql中的？赋值
			for(int i=0;i<paras.length;i++)
			{
				ps.setString(i+1, paras[i]);//整型、时间型会自动转换成String
			}
			rs=ps.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			//返回的是结果集，所以关闭资源放在调用处
		}
		
		return rs;
	}
}
