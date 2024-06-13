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

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "empId")
    private EmployeeEntity employeeEntity;

    @Column(nullable = false)
    private String systemName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType type;

    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createDate;

    public UserEntity(String id, String systemName, String password, UserType type) {
        this.id = id;
        this.systemName = systemName;
        this.password = password;
        this.type = type;
    }

    public UserEntity(String id, boolean isActive) {
        this.id = id;
        this.isActive = isActive;
    }
}
