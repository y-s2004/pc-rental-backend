package jp.rose.pc_rental.services;

import jp.rose.pc_rental.elements.TrnRental;
import jp.rose.pc_rental.repositories.TrnRentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TrnRentalService {

    private final TrnRentalRepository trnRentalRepository;

    @Autowired
    public TrnRentalService(TrnRentalRepository trnRentalRepository) {
        this.trnRentalRepository = trnRentalRepository;
    }

    public String rental(TrnRental trnRental) {
        String assetNum = trnRental.getAssetNum();

        if (trnRentalRepository.existsByAssetNumAndRentalStatus(assetNum, true)) {
            return "既に貸出中のPCです";
        }

        if (trnRentalRepository.existsByAssetNumAndDeleteFlag(assetNum, true)) {
            return "故障中で貸出できません";
        }

        boolean exists = trnRentalRepository.findByAssetNum(assetNum).isPresent();
        if (!exists) {
            return "そのようなPCは存在しません";
        }

        trnRental.setRentalsDate(LocalDateTime.now());
        trnRental.setRentalStatus(true);
        trnRentalRepository.save(trnRental);

        return "貸出が完了しました";

    }

    public String returnRental(String assetNum) {
        Optional<TrnRental> optionalRental = trnRentalRepository.findByAssetNum(assetNum);

        if (!optionalRental.isPresent()) {
            return "そのようなPCは存在しません";
        }

        TrnRental trnRental = optionalRental.get();

        if (!trnRental.isRentalStatus()) {
            return "既に返却されています";
        }

        if (trnRentalRepository.existsByAssetNumAndDeleteFlag(assetNum, true)) {
            return "故障中です";
        }

        trnRental.setRentalStatus(false);
        trnRental.setReturnDate(LocalDateTime.now());

        trnRentalRepository.save(trnRental);

        return "返却が完了しました";
    }


    public TrnRental updateRental(String assetNum, TrnRental trnRental) {
        TrnRental target = trnRentalRepository.findById(assetNum)
                .orElseThrow(() -> new IllegalArgumentException("機器が見つかりません: " + assetNum));

        target.setPlace(trnRental.getPlace());
        target.setName(trnRental.getName());
        target.setReturnDate(LocalDateTime.now());
        target.setInventoryDate(LocalDateTime.now());
        target.setRemarks(trnRental.getRemarks());

        return trnRentalRepository.save(target);
    }

    public List<TrnRental> getAllAvailableDevice(){
        return trnRentalRepository.findByRentalStatusTrueAndDeleteFlagFalse();
    }

    public List<TrnRental> getAllRentalDevice(){
        return trnRentalRepository.findByRentalStatusFalseAndDeleteFlagFalse();
    }

    public TrnRental getDetail(String assetNum){
        return trnRentalRepository.findByAssetNumAndDeleteFlagFalse(assetNum).orElse(null);
    }

    public List<TrnRental> getAllOverDevice(){
        LocalDateTime today = LocalDateTime.now();
        return trnRentalRepository.findByReturnDateBeforeAndRentalStatus(today, false);
    }

    public List<TrnRental> searchRental(String assetNum){
        return trnRentalRepository.findByAssetNumContainingAndDeleteFlagFalse(assetNum);
    }
}
