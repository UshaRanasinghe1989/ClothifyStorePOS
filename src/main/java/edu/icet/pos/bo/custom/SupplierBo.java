package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.dto.Supplier;

import java.util.List;

public interface SupplierBo extends SuperBo {
    boolean save(Supplier dto);
    List<Supplier> retrieveAll();
    List<Supplier> retrieveById(String id);
    List<Supplier> retrieveAllId();
    int update(Supplier dto);
    List<Supplier> retrieveSupplierNames();
    List<Supplier> retrieveSupplierIdByName(String name);
}
