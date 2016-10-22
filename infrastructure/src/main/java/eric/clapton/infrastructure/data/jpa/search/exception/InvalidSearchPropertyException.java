/*
 * Copyright (c) 2014-2014 www.roomy.cn All rights reserved
 * Info:roomy-core InvalidSearchPropertyException.java 2014-5-4 15:05:50 wuwei$$
 */
package eric.clapton.infrastructure.data.jpa.search.exception;

// TODO: Auto-generated Javadoc
/**
 * The Class InvalidSearchPropertyException.
 * 
 * @author wuwei 2014-4-29
 */
public final class InvalidSearchPropertyException extends SearchException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7269443994287794296L;

	/**
	 * Instantiates a new invalid search property exception.
	 * 
	 * @param searchProperty
	 *            the search property
	 * @param entityProperty
	 *            the entity property
	 */
	public InvalidSearchPropertyException(String searchProperty, String entityProperty) {
		this(searchProperty, entityProperty, null);
	}

	/**
	 * Instantiates a new invalid search property exception.
	 * 
	 * @param searchProperty
	 *            the search property
	 * @param entityProperty
	 *            the entity property
	 * @param cause
	 *            the cause
	 */
	public InvalidSearchPropertyException(String searchProperty, String entityProperty, Throwable cause) {
		super("Invalid Search Property [" + searchProperty + "] Entity Property [" + entityProperty + "]", cause);
	}

}
