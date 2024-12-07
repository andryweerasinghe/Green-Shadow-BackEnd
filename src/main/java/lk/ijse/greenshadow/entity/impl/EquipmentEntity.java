package lk.ijse.greenshadow.entity.impl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lk.ijse.greenshadow.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "equipment")
public class EquipmentEntity implements SuperEntity {
    @Id
    private String eq_code;
    private String name;
    private String type;
    private String status;
    @ManyToOne(optional = true)
    @JoinColumn(name = "staff_id",nullable = true)
    private StaffEntity staff;
    @ManyToOne(optional = true)
    @JoinColumn(name = "field_code",nullable = true)
    private FieldEntity field;
    private String field_name;
    private String field_location;
    private String first_name;
    private String role;
    private String phone_no;
}
