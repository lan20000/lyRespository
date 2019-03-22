package com.koala.foundation.dao;
import org.springframework.stereotype.Repository;
import com.koala.core.base.GenericDAO;
import com.koala.foundation.domain.Article;
@Repository("articleDAO")
public class ArticleDAO extends GenericDAO<Article> {

}