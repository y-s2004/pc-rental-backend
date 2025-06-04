package jp.rose.pc_rental.services;

import jp.rose.pc_rental.elements.MstDevice;
import jp.rose.pc_rental.elements.TrnRental;
import jp.rose.pc_rental.repositories.MstDeviceRepository;
import jp.rose.pc_rental.repositories.TrnRentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TrnRentalService {

    private final TrnRentalRepository trnRentalRepository;
    private final MstDeviceRepository mstDeviceRepository;

    @Autowired
    public TrnRentalService(TrnRentalRepository trnRentalRepository, MstDeviceRepository mstDeviceRepository) {
        this.trnRentalRepository = trnRentalRepository;
        this.mstDeviceRepository = mstDeviceRepository;
    }

    public String rental(TrnRental trnRental) {

        if (trnRentalRepository.existsByAssetNumAndRentalStatus(trnRental.getAssetNum(), true)) {
            return "既に貸出中のPCです";
        }

        if (trnRentalRepository.existsByAssetNumAndDeleteFlag(trnRental.getAssetNum(), true)) {
            return "故障中で貸出できません";
        }

        Optional<MstDevice> deviceOpt = mstDeviceRepository.findByAssetNum(trnRental.getAssetNum());
        if (!deviceOpt.isPresent()) {
            return "そのようなPCは存在しません";
        }

        MstDevice device = deviceOpt.get();

        device.setDeleteFlag(true);
        mstDeviceRepository.save(device);

        trnRental.setRentalsDate(LocalDateTime.now());
        trnRental.setRentalStatus(true);
        trnRental.setDeleteFlag(false);
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
