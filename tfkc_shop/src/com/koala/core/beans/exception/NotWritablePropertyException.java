/*
 * Copyright 2002-2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.koala.core.beans.exception;
/**
 * Exception thrown on an attempt to set the value of a property
 * that isn't writable, because there's no setter method. In some
 * situations alternatives are presented.
 *
 * @author Rod Johnson
 * @author Alef Arendsen
 * @author Arjen Poutsma
 */
public class NotWritablePropertyException extends PropertyException {

	private String[] possibleMatches = null;


	/**
	 * Create a new NotWritablePropertyException.
	 * @param beanClass the offending bean class
	 * @param propertyName the offending property name
	 */
	public NotWritablePropertyException(Class beanClass, String propertyName) {
		super(beanClass, propertyName,
				"Bean property '" + propertyName + "' is not writable or has an invalid setter method: " +
				"Does the return type of the getter match the parameter type of the setter?");
	}

	/**
	 * Create a new NotWritablePropertyException.
	 * @param beanClass the offending bean class
	 * @param propertyName the offending property name
	 * @param msg the detail message
	 */
	public NotWritablePropertyException(Class beanClass, String propertyName, String msg) {
		super(beanClass, propertyName, msg);
	}

	/**
	 * Create a new NotWritablePropertyException.
	 * @param beanClass the offending bean class
	 * @param propertyName the offending property name
	 * @param msg the detail message
	 * @param ex the root cause
	 */
	public NotWritablePropertyException(Class beanClass, String propertyName, String msg, Throwable ex) {
		super(beanClass, propertyName, msg, ex);
	}

	/**
	 * Create a new NotWritablePropertyException.
	 * @param beanClass the offending bean class
	 * @param propertyName the offending property name
	 * @param msg the detail message
	 * @param possibleMatches suggestions for actual bean property names
	 * that closely match the invalid property name
	 */
	public NotWritablePropertyException(Class beanClass, String propertyName, String msg, String[] possibleMatches) {
		super(beanClass, propertyName, msg);
		this.possibleMatches = possibleMatches;
	}


	/**
	 * Return suggestions for actual bean property names that closely match
	 * the invalid property name, if any.
	 */
	public String[] getPossibleMatches() {
		return possibleMatches;
	}

}
