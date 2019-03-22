package com.koala.foundation.dao;
import org.springframework.stereotype.Repository;
import com.koala.core.base.GenericDAO;
import com.koala.foundation.domain.Message;
@Repository("messageDAO")
public class MessageDAO extends GenericDAO<Message> {

}