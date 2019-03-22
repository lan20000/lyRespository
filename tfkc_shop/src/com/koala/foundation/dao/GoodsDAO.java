package com.koala.foundation.dao;
import org.springframework.stereotype.Repository;
import com.koala.core.base.GenericDAO;
import com.koala.foundation.domain.Goods;
@Repository("goodsDAO")
public class GoodsDAO extends GenericDAO<Goods> {

}