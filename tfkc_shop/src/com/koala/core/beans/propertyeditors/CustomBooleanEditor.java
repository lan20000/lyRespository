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

package com.koala.core.beans.propertyeditors;

import java.beans.PropertyEditorSupport;

import com.koala.core.tools.StringUtils;


public class CustomBooleanEditor extends PropertyEditorSupport {

	public static final String VALUE_TRUE = "true";
	public static final String VALUE_FALSE = "false";

	public static final String VALUE_ON = "on";
	public static final String VALUE_OFF = "off";

	public static final String VALUE_YES = "yes";
	public static final String VALUE_NO = "no";

	public static final String VALUE_1 = "1";
	public static final String VALUE_0 = "0";


	private final String trueString;

	private final String falseString;

	private final boolean allowEmpty;


	/**
	 * Create a new CustomBooleanEditor instance, with "true"/"on"/"yes"
	 * and "false"/"off"/"no" as recognized String values.
	 * <p>The "allowEmpty" parameter states if an empty String should
	 * be allowed for parsing, i.e. get interpreted as null value.
	 * Else, an IllegalArgumentException gets thrown in that case.
	 * @param allowEmpty if empty strings should be allowed
	 */
	public CustomBooleanEditor(boolean allowEmpty) {
		this(null, null, allowEmpty);
	}

	/**
	 * Create a new CustomBooleanEditor instance,
	 * with configurable String values for true and false.
	 * <p>The "allowEmpty" parameter states if an empty String should
	 * be allowed for parsing, i.e. get interpreted as null value.
	 * Else, an IllegalArgumentException gets thrown in that case.
	 * @param trueString the String value that represents true:
	 * for example, "true" (VALUE_TRUE), "on" (VALUE_ON),
	 * "yes" (VALUE_YES) or some custom value
	 * @param falseString the String value that represents false:
	 * for example, "false" (VALUE_FALSE), "off" (VALUE_OFF),
	 * "no" (VALUE_NO) or some custom value
	 * @param allowEmpty if empty strings should be allowed
	 * @see #VALUE_TRUE
	 * @see #VALUE_FALSE
	 * @see #VALUE_ON
	 * @see #VALUE_OFF
	 * @see #VALUE_YES
	 * @see #VALUE_NO
	 */
	public CustomBooleanEditor(String trueString, String falseString, boolean allowEmpty) {
		this.trueString = trueString;
		this.falseString = falseString;
		this.allowEmpty = allowEmpty;
	}

	/**
	 * 重载setValue方法，使得支持用Number进行赋值
	 */
	public void setValue(Object value) {
		if(value instanceof Number)
		{
			if(((Number)value).intValue()>0)super.setValue(Boolean.TRUE);
			else super.setValue(Boolean.FALSE);
			
		}else
		super.setValue(value);
	}

	public void setAsText(String text) throws IllegalArgumentException {
		if (this.allowEmpty && !StringUtils.hasText(text)) {
			// Treat empty String as null value.
			setValue(null);
		}
		else if (this.trueString != null && text.equalsIgnoreCase(this.trueString)) {
			setValue(Boolean.TRUE);
		}
		else if (this.falseString != null && text.equalsIgnoreCase(this.falseString)) {
			setValue(Boolean.FALSE);
		}
		else if (this.trueString == null &&
				(text.equalsIgnoreCase(VALUE_TRUE) || text.equalsIgnoreCase(VALUE_ON) ||
				text.equalsIgnoreCase(VALUE_YES) || text.equals(VALUE_1))) {
			setValue(Boolean.TRUE);
		}
		else if (this.falseString == null &&
				(text.equalsIgnoreCase(VALUE_FALSE) || text.equalsIgnoreCase(VALUE_OFF) ||
				text.equalsIgnoreCase(VALUE_NO) || text.equals(VALUE_0))) {
			setValue(Boolean.FALSE);
		}
		else {
			throw new IllegalArgumentException("Invalid boolean value [" + text + "]");
		}
	}

	public String getAsText() {
		if (Boolean.TRUE.equals(getValue())) {
			return (this.trueString != null ? this.trueString : VALUE_TRUE);
		}
		else if (Boolean.FALSE.equals(getValue())) {
			return (this.falseString != null ? this.falseString : VALUE_FALSE);
		}
		else {
			return "";
		}
	}

}
