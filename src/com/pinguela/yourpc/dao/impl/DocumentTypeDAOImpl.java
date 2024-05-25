package com.pinguela.yourpc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.DocumentTypeDAO;
import com.pinguela.yourpc.model.DocumentType;
import com.pinguela.yourpc.util.JDBCUtils;

public class DocumentTypeDAOImpl 
implements DocumentTypeDAO {
	
	private static Logger logger = LogManager.getLogger(DocumentTypeDAOImpl.class);
	
	private static final String QUERY =
			" SELECT dt.ID, dt.NAME"
			+ " FROM DOCUMENT_TYPE dt";

	@Override
	public Map<String, DocumentType> findAll(Connection conn) throws DataException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Map<String, DocumentType> results = new HashMap<String, DocumentType>();
		
		try {
			stmt = conn.prepareStatement(QUERY);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				DocumentType dt = loadNext(rs);
				results.put(dt.getId(), dt);
			}
			
			return results;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}
	
	private DocumentType loadNext(ResultSet rs) throws SQLException {
		DocumentType dt = new DocumentType();
		
		int i = 1;
		dt.setId(rs.getString(i++));
		dt.setName(rs.getString(i++));
		
		return dt;
	}

}
