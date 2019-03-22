package com.koala.core.log.util.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koala.core.log.model.AccessLogModel;
import com.koala.core.log.util.AccessLogger;
import com.koala.core.log.util.BaseLogger;

public class AccessLoggerImpl extends BaseLoggerImpl implements AccessLogger {

	@Override
	public void info(Class<?> clazz, AccessLogModel logModel) {

		Logger log = LoggerFactory.getLogger(BaseLogger.LoggerType.A + "."
				+ clazz.getName());
		log.info("{}", logModel);
	}

}
