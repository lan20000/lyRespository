package com.koala.foundation.dao;
import org.springframework.stereotype.Repository;
import com.koala.core.base.GenericDAO;
import com.koala.foundation.domain.VerifyCode;
@Repository("mobileVerifyCodeDAO")
public class VerifyCodeDAO extends GenericDAO<VerifyCode> {

}