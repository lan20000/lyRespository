package com.koala.module.sns.dao;
import org.springframework.stereotype.Repository;

import com.koala.core.base.GenericDAO;
import com.koala.module.sns.domain.UserShare;

@Repository("userShareDAO")
public class UserShareDAO extends GenericDAO<UserShare> {

}