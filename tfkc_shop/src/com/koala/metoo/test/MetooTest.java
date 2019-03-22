package com.koala.metoo.test;

import javax.persistence.UniqueConstraint;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.koala.foundation.domain.Address;
import com.koala.metoo.manage.buyer.action.AddressMetooBuyerAction;

public class MetooTest {
	@Autowired 
	private AddressMetooBuyerAction add;
	
	@Test
	public void AddressSava(){
		
		Address address = new Address("hukai","asd");
	}
}
