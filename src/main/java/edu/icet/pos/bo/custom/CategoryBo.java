package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.dto.Category;

import java.util.List;

public interface CategoryBo extends SuperBo {
    boolean save(Category dto);
    List<Category> retrieveAll();
    List<Category> retrieveById(String id);
    List<String> retrieveAllId();
    int update(Category dto);
    List<String> retrieveCategoryNames();
    List<String> retrieveCatIdByName(String name);
}
