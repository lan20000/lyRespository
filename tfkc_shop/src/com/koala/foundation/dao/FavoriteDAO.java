package com.koala.foundation.dao;
import org.springframework.stereotype.Repository;
import com.koala.core.base.GenericDAO;
import com.koala.foundation.domain.Favorite;
@Repository("favoriteDAO")
public class FavoriteDAO extends GenericDAO<Favorite> {

}