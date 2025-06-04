package jp.rose.pc_rental.elements;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "mst_user")
public class MstUser {

    @Id
    @Column(unique = true, length = 20)
    @JsonProperty("employee_no")
    private String employeeNo;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(length = 20)
    @JsonProperty("name_kana")
    private String nameKana;
    
    @Column(length = 20)
    private String department;

    @Column(length = 20)
    @JsonProperty("tel_no")
    private String telNo;

    @Column(length = 50)
    @JsonProperty("mail_address")
    private String mailAddress;

    private int age;
    private int gender;

    @Column(length = 20)
    private String position;

    @Column(length = 20)
    @JsonProperty("account_level")
    private String accountLevel;

    @JsonProperty("retire_date")
    private LocalDateTime retireDate;

    @Column(nullable = false)
    @JsonProperty("register_date")
    private LocalDateTime registerDate;

    @JsonProperty("update_date")
    private LocalDateTime updateDate;

    @Column(nullable = false, columnDefinition = "boolean default false")
    @JsonProperty("delete_flag")
    private boolean deleteFlag;

    @Column(nullable = false, length = 10)
    private String password;

}
