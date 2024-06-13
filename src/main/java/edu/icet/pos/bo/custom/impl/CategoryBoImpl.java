package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.CategoryBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.CategoryDao;
import edu.icet.pos.dto.Category;
import edu.icet.pos.entity.CategoryEntity;
import edu.icet.pos.util.DaoType;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import java.util.List;

@Slf4j
public class CategoryBoImpl implements CategoryBo {
    CategoryDao categoryDao = DaoFactory.getInstance().getDao(DaoType.CATEGORY);

    @Override
    public boolean save(Category dto) {
        return categoryDao.save(new ModelMapper().map(dto, CategoryEntity.class));
    }

    @Override
    public List retrieveAll() {
        return categoryDao.retrieveAll();
    }

    @Override
    public List retrieveById(String id) {
        return categoryDao.retrieveById(id);
    }

    @Override
    public List retrieveAllId() {
        return categoryDao.retrieveAllId();
    }

    @Override
    public int update(Category dto) {
        return categoryDao.update(new ModelMapper().map(dto, CategoryEntity.class));
    }

    @Override
    public List retrieveCategoryNames() {
        return categoryDao.retrieveCategoryNames();
    }

    @Override
    public List retrieveCatIdByName(String name) {
        return categoryDao.retrieveCatIdByName(name);
    }
}
