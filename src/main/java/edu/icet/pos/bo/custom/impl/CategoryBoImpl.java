package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.CategoryBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.CategoryDao;
import edu.icet.pos.dto.Category;
import edu.icet.pos.entity.CategoryEntity;
import edu.icet.pos.util.DaoType;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CategoryBoImpl implements CategoryBo {
    CategoryDao categoryDao = DaoFactory.getInstance().getDao(DaoType.CATEGORY);

    @Override
    public boolean save(Category dto) {
        return categoryDao.save(new ModelMapper().map(dto, CategoryEntity.class));
    }

    @Override
    public List<Category> retrieveAll() {
        List<CategoryEntity> categoryEntityList =categoryDao.retrieveAll();
        List<Category> categoryList = new ArrayList<>();
        categoryEntityList.forEach(categoryEntity -> categoryList.add(new ModelMapper().map(categoryEntity, Category.class)));
        return categoryList;
    }

    @Override
    public List<Category> retrieveById(String id) {
        List<CategoryEntity> categoryEntityList =categoryDao.retrieveById(id);
        List<Category> categoryList = new ArrayList<>();
        categoryEntityList.forEach(categoryEntity -> categoryList.add(new ModelMapper().map(categoryEntity, Category.class)));
        return categoryList;
    }

    @Override
    public List<String> retrieveAllId() {
        return categoryDao.retrieveAllId();
    }

    @Override
    public int update(Category dto) {
        return categoryDao.update(new ModelMapper().map(dto, CategoryEntity.class));
    }

    @Override
    public List<String> retrieveCategoryNames() {
        return categoryDao.retrieveCategoryNames();
    }

    @Override
    public List<String> retrieveCatIdByName(String name) {
        return categoryDao.retrieveCatIdByName(name);
    }
}
