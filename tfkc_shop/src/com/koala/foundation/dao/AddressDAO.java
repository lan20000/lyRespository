package com.koala.foundation.dao;
import org.springframework.stereotype.Repository;
import com.koala.core.base.GenericDAO;
import com.koala.foundation.domain.Address;
@Repository("addressDAO")
public class AddressDAO extends GenericDAO<Address> {

}