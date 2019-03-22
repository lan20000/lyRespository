package com.koala.foundation.dao;
import org.springframework.stereotype.Repository;
import com.koala.core.base.GenericDAO;
import com.koala.foundation.domain.Album;
@Repository("albumDAO")
public class AlbumDAO extends GenericDAO<Album> {

}