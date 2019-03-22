package com.koala.module.chatting.dao;
import org.springframework.stereotype.Repository;

import com.koala.core.base.GenericDAO;
import com.koala.module.chatting.domain.ChattingLog;
@Repository("chattingLogDAO")
public class ChattingLogDAO extends GenericDAO<ChattingLog> {

}