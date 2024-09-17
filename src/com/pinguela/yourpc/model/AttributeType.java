package com.pinguela.yourpc.model;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

public class AttributeType implements UserType<Attribute<?>> {
	
	private static final Class<Attribute<?>> CLASS;
	
	static {
		try {
			CLASS = (Class<Attribute<?>>) Class.forName("com.pinguela.yourpc.model.Attribute");
		} catch (Exception e) {
			throw new AssertionError();
		}
	}

	@Override
	public int getSqlType() {
		return Types.JAVA_OBJECT;
	}

	@Override
	public Class<Attribute<?>> returnedClass() {
		return CLASS;
	}

	@Override
	public boolean equals(Attribute<?> x, Attribute<?> y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode(Attribute<?> x) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Attribute<?> nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Attribute<?> value, int index,
			SharedSessionContractImplementor session) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Attribute<?> deepCopy(Attribute<?> value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isMutable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Serializable disassemble(Attribute<?> value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attribute<?> assemble(Serializable cached, Object owner) {
		// TODO Auto-generated method stub
		return null;
	}

}
