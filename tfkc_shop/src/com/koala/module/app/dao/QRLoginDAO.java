package com.koala.module.app.dao;
import org.springframework.stereotype.Repository;

import com.koala.core.base.GenericDAO;
import com.koala.module.app.domain.QRLogin;

@Repository("qRLoginDAO")
public class QRLoginDAO extends GenericDAO<QRLogin> {

}