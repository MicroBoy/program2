/**
 * 这是自定义的一个学生【表模型】，可以当做一个表来使用，它类似TableModel，必须继承AbstractTableModel类
 *                  对数据库的操作
 * 1.查询
 * 2.数据模型更新
 */
package com.stusys4;

import java.sql.*;
import java.util.*;

import javax.swing.table.*;

public class StuModel extends AbstractTableModel{

	//操作表中的数据
	//构造函数-->初始化数据模型StuModel
	Vector rowData,columnName;  //使用setModel()取代了JTable(rowData,columnName)   Vector行向量【集合类】
	SqlHelper sqlHelper=null;
	
	//将添加学生的数据库操作移植到此
	public boolean updStu(String sql,String[] paras)//既执行了对数据库的操作又可返回布尔值
	{               //可以用来进行增、删、改 
		//创建一个SqlHelper，【如果不考虑代码的并发性，可以把SqlHelper写成static,或者做成单态】
		sqlHelper=new SqlHelper();
		return sqlHelper.updExecute(sql, paras);
	}
	
	//无参数初始化构造函数,【仅仅是为了创建对象后引用类中的方法】
	public StuModel()
	{
		
	}

	//查询方法
	public void queryStu(String sql,String[] paras)
	{
		//创建中间的  JTable
		//创建列
		columnName=new Vector();
		columnName.add("学号");
		columnName.add("姓名");
		columnName.add("性别");
		columnName.add("年龄");
		columnName.add("籍贯");
		columnName.add("系别");
						
		//引入数据库中的行数据
		rowData=new Vector();//总行
		try {
			sqlHelper=new SqlHelper();
			ResultSet rs=sqlHelper.queryExecute(sql, paras);
			//循环取出
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
			//关闭资源
			sqlHelper.close();
		}
			
	}	
	
	
	//下面的方法会自动调用
	@Override
	//得到共有多少列
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this.columnName.size();
	}

	@Override
	//得到共有多少行
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.rowData.size();
	}

	@Override
	//得到某行某列的数据
	public Object getValueAt(int row, int column) {
		// TODO Auto-generated method stub
		return ((Vector)this.rowData.get(row)).get(column);  
		//this.rowData.get(row)是多行一列值，要转成多行多列向量
		//就是要将this.rowData.get(row)转成向量
	}

	@Override
	//得到列名
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return (String)this.columnName.get(column);
	}

}
