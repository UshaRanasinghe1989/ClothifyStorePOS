package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.SupplierBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.SupplierDao;
import edu.icet.pos.dto.Supplier;
import edu.icet.pos.entity.SupplierEntity;
import edu.icet.pos.util.DaoType;
import edu.icet.pos.util.GetModelMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class SupplierBoImpl implements SupplierBo {
    private final ModelMapper mapper = GetModelMapper.getInstance().getModelMapper();
    SupplierDao supplierDao = DaoFactory.getInstance().getDao(DaoType.SUPPLIER);
    @Override
    public boolean save(Supplier dto) {
        return supplierDao.save(mapper.map(dto, SupplierEntity.class));
    }

    @Override
    public List<Supplier> retrieveAll() {
        List<SupplierEntity> supplierEntityList = supplierDao.retrieveAll();
        return mapper.map(supplierEntityList, new TypeToken<List<Supplier>>() {}.getType());
    }

    @Override
    public List<Supplier> retrieveById(String id) {
        List<SupplierEntity> supplierEntityList = supplierDao.retrieveById(id);
        return mapper.map(supplierEntityList, new TypeToken<List<Supplier>>() {}.getType());
    }

    @Override
    public List<String> retrieveAllId() {
        return supplierDao.retrieveAllId();
    }

    @Override
    public int update(Supplier dto) {
        return supplierDao.update(mapper.map(dto, SupplierEntity.class));
    }

    @Override
    public List<String> retrieveSupplierNames() {
        return supplierDao.retrieveSupplierNames();
    }

    @Override
    public List<String> retrieveSupplierIdByName(String name) {
        return supplierDao.retrieveSupplierIdByName(name);
    }
}
