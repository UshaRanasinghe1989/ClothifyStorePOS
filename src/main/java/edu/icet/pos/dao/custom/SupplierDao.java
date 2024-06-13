package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.CrudDao;
import edu.icet.pos.entity.SupplierEntity;

import java.util.List;

public interface SupplierDao extends CrudDao<SupplierEntity> {
    List<String> retrieveSupplierNames();
    List<String> retrieveSupplierIdByName(String name);
}
