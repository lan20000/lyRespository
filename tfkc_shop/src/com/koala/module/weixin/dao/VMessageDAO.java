package com.koala.module.weixin.dao;
import org.springframework.stereotype.Repository;
import com.koala.core.base.GenericDAO;
import com.koala.module.weixin.domain.VMessage;
@Repository("vMessageDAO")
public class VMessageDAO extends GenericDAO<VMessage> {

}