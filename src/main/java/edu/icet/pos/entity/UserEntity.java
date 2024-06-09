package edu.icet.pos.entity;

import edu.icet.pos.util.UserType;
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
@Table(name = "user")
public class UserEntity {
    @Id
    private String id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "empId")
    private String empId;
    private String systemName;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserType type;
    private boolean isActive;
    @Temporal(TemporalType.DATE)
    private Date createDate;
}
