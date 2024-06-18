package edu.icet.pos.dto;

import edu.icet.pos.util.ProductSizes;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    private String id;
    private Category category;
    private Supplier supplier;
    private String name;
    private String description;
    private ProductSizes size;
    private Date createDateTime;
}
