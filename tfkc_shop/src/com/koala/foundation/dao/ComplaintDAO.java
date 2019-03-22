package com.koala.foundation.dao;
import org.springframework.stereotype.Repository;
import com.koala.core.base.GenericDAO;
import com.koala.foundation.domain.Complaint;
@Repository("complaintDAO")
public class ComplaintDAO extends GenericDAO<Complaint> {

}