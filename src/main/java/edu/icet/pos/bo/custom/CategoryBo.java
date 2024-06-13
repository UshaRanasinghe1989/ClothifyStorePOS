package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.dto.Category;

import java.util.List;

public interface CategoryBo extends SuperBo {
    boolean save(Category dto);
    List<Category> retrieveAll();
    List<Category> retrieveById(String id);
    List<Category> retrieveAllId();
    int update(Category dto);
    List<Category> retrieveCategoryNames();
    List<Category> retrieveCatIdByName(String name);
}
