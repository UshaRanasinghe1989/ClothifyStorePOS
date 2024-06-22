package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.CategoryBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.CategoryDao;
import edu.icet.pos.dto.Category;
import edu.icet.pos.entity.CategoryEntity;
import edu.icet.pos.util.DaoType;
import edu.icet.pos.util.GetModelMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.List;

@Slf4j
public class CategoryBoImpl implements CategoryBo {
    ModelMapper mapper = GetModelMapper.getInstance().getModelMapper();
    private static final CategoryDao categoryDao = DaoFactory.getInstance().getDao(DaoType.CATEGORY);

    @Override
    public boolean save(Category dto) {
        return categoryDao.save(mapper.map(dto, CategoryEntity.class));
    }

    @Override
    public List<Category> retrieveAll() {
        List<CategoryEntity> categoryEntityList =categoryDao.retrieveAll();
        return mapper.map(categoryEntityList, new TypeToken<List<Category>>() {}.getType());
    }

    @Override
    public List<Category> retrieveById(String id) {
        List<CategoryEntity> categoryEntityList =categoryDao.retrieveById(id);
        return mapper.map(categoryEntityList, new TypeToken<List<Category>>() {}.getType());
    }

    @Override
    public List<String> retrieveAllId() {
        return categoryDao.retrieveAllId();
    }

    @Override
    public int update(Category dto) {
        return categoryDao.update(mapper.map(dto, CategoryEntity.class));
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
