package com.koala.foundation.dao;
import org.springframework.stereotype.Repository;
import com.koala.core.base.GenericDAO;
import com.koala.foundation.domain.Document;
@Repository("documentDAO")
public class DocumentDAO extends GenericDAO<Document> {

}