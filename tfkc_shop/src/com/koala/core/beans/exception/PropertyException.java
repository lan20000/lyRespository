/*
 * Copyright 2002-2005 the original author or authors.
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
 * Exception thrown when referring to an invalid bean property.
 * Carries the offending bean class and property name.
 *
 * @author Juergen Hoeller
 * @since 1.0.2
 */
public class PropertyException extends BeansException {	

	protected String propertyName;
	public PropertyException(String msg)
	{
		super(msg);
	}
	public PropertyException(String msg, Throwable ex)
	{
		super(msg,ex);
	}
	
	/**
	 * Create a new InvalidPropertyException.
	 * @param beanClass the offending bean class
	 * @param propertyName the offending property
	 * @param msg the detail message
	 */
	public PropertyException(Class beanClass, String propertyName, String msg) {
		this(beanClass, propertyName, msg, null);
	}

	/**
	 * Create a new InvalidPropertyException.
	 * @param beanClass the offending bean class
	 * @param propertyName the offending property
	 * @param msg the detail message
	 * @param ex the root cause
	 */
	public PropertyException(Class beanClass, String propertyName, String msg, Throwable ex) {
		super("Invalid property '" + propertyName + "' of bean class [" + beanClass.getName() + "]: " + msg, ex);
		this.beanClass = beanClass;
		this.propertyName = propertyName;
	}

	/**
	 * Return the name of the offending property.
	 */
	public String getPropertyName() {
		return propertyName;
	}

}
