package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.CrudDao;
import edu.icet.pos.entity.CategoryEntity;

import java.util.List;

public interface CategoryDao extends CrudDao<CategoryEntity> {
    List<String> retrieveCategoryNames();
    List<String> retrieveCatIdByName(String name);
}
