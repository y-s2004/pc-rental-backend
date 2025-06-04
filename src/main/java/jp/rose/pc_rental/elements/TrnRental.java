package jp.rose.pc_rental.elements;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "trn_rental")
public class TrnRental {

    @Id
    @Column(unique = true, length = 20)
    @JsonProperty("asset_num")
    private String assetNum;

    @Column(length = 20)
    private String maker;

    @Column(length = 20)
    private String os;

    @Column(nullable = false, length = 20)
    private String place;

    @Column(nullable = false, columnDefinition = "boolean default true")
    @JsonProperty("rental_status")
    private boolean rentalStatus;

    @Column(nullable = false, length = 20)
    @JsonProperty("user_no")
    private String userNo;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    @JsonProperty("rental_date")
    private LocalDateTime rentalsDate;

    @Column(nullable = false)
    @JsonProperty("return_date")
    private LocalDateTime returnDate;
    
    @JsonProperty("inventory_date")
    private LocalDateTime inventoryDate;

    @Column(length = 40)
    private String remarks;

    @Column(nullable = false, columnDefinition = "boolean default false")
    @JsonProperty("delete_flag")
    private Boolean deleteFlag = false;


}
