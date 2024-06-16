package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.SupplierBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.SupplierDao;
import edu.icet.pos.dto.Supplier;
import edu.icet.pos.entity.SupplierEntity;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class SupplierBoImpl implements SupplierBo {
    SupplierDao supplierDao = DaoFactory.getInstance().getDao(DaoType.SUPPLIER);
    @Override
    public boolean save(Supplier dto) {
        return supplierDao.save(new ModelMapper().map(dto, SupplierEntity.class));
    }

    @Override
    public List<Supplier> retrieveAll() {
        List<SupplierEntity> supplierEntityList = supplierDao.retrieveAll();
        List<Supplier> supplierList = new ArrayList<>();

        supplierEntityList.forEach(supplierEntity ->
                supplierList.add(
                        new ModelMapper().map(supplierEntity, Supplier.class)
                ));

        return supplierList;
    }

    @Override
    public List<Supplier> retrieveById(String id) {
        List<SupplierEntity> supplierEntityList = supplierDao.retrieveById(id);
        List<Supplier> supplierList = new ArrayList<>();

        supplierEntityList.forEach(supplierEntity ->
                supplierList.add(
                        new ModelMapper().map(supplierEntity, Supplier.class)
                ));

        return supplierList;
    }

    @Override
    public List<String> retrieveAllId() {
        return supplierDao.retrieveAllId();
    }

    @Override
    public int update(Supplier dto) {
        return supplierDao.update(new ModelMapper().map(dto, SupplierEntity.class));
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
