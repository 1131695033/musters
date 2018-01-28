package com.jeeplus.modules.sys.web;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;


import com.bstek.ureport.definition.datasource.BuildinDatasource;


public class BuiltDataSourceController implements BuildinDatasource {
	
	public DataSource mySqldataSource;
	@Override
	public Connection getConnection() {
		// TODO Auto-generated method stub
		try {
			return mySqldataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "oracle数据源";
	}

	public void setMySqldataSource(DataSource mySqldataSource) {
		this.mySqldataSource = mySqldataSource;
	}

	
}
