package edu.icet.pos.dto;


import edu.icet.pos.entity.CategoryEntity;
import edu.icet.pos.entity.SupplierEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    private String id;
    private CategoryEntity categoryEntity;
    private SupplierEntity supplierEntity;
    private String name;
    private String description;
    private String size;
    private Date createDateTime;
}
