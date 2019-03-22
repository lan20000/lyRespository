package com.koala.foundation.dao;

import org.springframework.stereotype.Repository;

import com.koala.core.base.GenericDAO;
import com.koala.foundation.domain.Role;

@Repository("roleDAO")
public class RoleDAO extends GenericDAO<Role> {
}
