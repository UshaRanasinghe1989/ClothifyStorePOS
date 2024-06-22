package edu.icet.pos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "credit_note")
public class CreditNoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "return_id")
    private ReturnEntity returnEntity;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private boolean isRedeemed;

    @Column(nullable = false)
    private Date createDate;

    public CreditNoteEntity(ReturnEntity returnEntity, double price, boolean isRedeemed, Date createDate){
        this.returnEntity = returnEntity;
        this.price = price;
        this.isRedeemed = isRedeemed;
        this.createDate = createDate;
    }
}
