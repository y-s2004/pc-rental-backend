package jp.rose.pc_rental.repositories;

import jp.rose.pc_rental.elements.TrnRental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TrnRentalRepository extends JpaRepository<TrnRental, String> {

    Optional<TrnRental> findByAssetNum(String assetNum);
    Optional<TrnRental> findByAssetNumAndDeleteFlagFalse(String assetNum);
    boolean existsByAssetNumAndDeleteFlag(String assetNum, boolean deleteFlag);
    boolean existsByAssetNumAndRentalStatus(String assetNum, boolean rentalStatus);
    List<TrnRental> findByRentalStatusTrueAndDeleteFlagFalse();
    List<TrnRental> findByRentalStatusFalseAndDeleteFlagFalse();
    List<TrnRental> findByReturnDateBeforeAndRentalStatus(LocalDateTime returnDate, boolean rentalStatus);
    List<TrnRental> findByAssetNumContainingAndDeleteFlagFalse(String assetNum);


}
