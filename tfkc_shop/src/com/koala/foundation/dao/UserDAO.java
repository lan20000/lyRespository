package com.koala.foundation.dao;

import org.springframework.stereotype.Repository;

import com.koala.core.base.GenericDAO;
import com.koala.foundation.domain.User;

@Repository("userDAO")
public class UserDAO extends GenericDAO<User> {

}
