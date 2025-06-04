package jp.rose.pc_rental.elements;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "MST_DEVICE")
public class MstDevice {

    @Id
    @Column(unique = true, length = 20)
    @JsonProperty("asset_num")
    private String assetNum;

    @Column(length = 20)
    private String maker;

    @Column(length = 20)
    private String os;

    @Column(length = 20)
    private String memory;

    @Column(length = 5)
    @JsonProperty("disc_capacity")
    private String discCapacity;

    @Column(nullable = false, length = 20)
    private String place;

    @Column(length = 20)
    private String remarks;

    @Column(columnDefinition = "boolean default false")
    @JsonProperty("graphic_board")
    private Boolean graphicBoard;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean breakdown;

    @Column(nullable = false, columnDefinition = "boolean default false")
    @JsonProperty("delete_flag")
    private Boolean deleteFlag = false;

    @Column(nullable = false)
    @JsonProperty("rental_start")
    private LocalDateTime rentalStart;

    @JsonProperty("rental_deadline")
    private LocalDateTime rentalDeadline;

    @Column(nullable = false)
    @JsonProperty("registration_date")
    private LocalDateTime registrationDate;

    @Column(nullable = false)
    @JsonProperty("update_date")
    private LocalDateTime updateDate;


}
