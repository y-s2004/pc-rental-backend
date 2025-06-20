package jp.rose.pc_rental.repositories;

import jp.rose.pc_rental.entity.TrnRental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TrnRentalRepository extends JpaRepository<TrnRental, String> {

    Optional<TrnRental> findByAssetNumAndDeleteFlagFalse(String assetNum);
    boolean existsByAssetNumAndRentalStatus(String assetNum, boolean rentalStatus);
    Optional<TrnRental>findByAssetNumAndUserNo(String assetNum, String UserNo);
    List<TrnRental> findByRentalStatusTrueAndDeleteFlagFalse();
    List<TrnRental> findByReturnDateBeforeAndRentalStatus(LocalDateTime returnDate, boolean rentalStatus);
    List<TrnRental> findByAssetNumContainingAndDeleteFlagFalse(String assetNum);
}
