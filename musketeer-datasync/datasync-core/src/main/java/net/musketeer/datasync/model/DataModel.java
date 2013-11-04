/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.musketeer.datasync.model;

import java.io.Serializable;

/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 4, 2013
 */
public class DataModel implements Serializable {

	private static final long serialVersionUID = 2837853966303259780L;
	
	private String name;
	
	private Object value;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName( String name ) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue( Object value ) {
		this.value = value;
	}

}
