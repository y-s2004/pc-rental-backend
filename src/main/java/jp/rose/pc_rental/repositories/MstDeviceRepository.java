package jp.rose.pc_rental.repositories;

import jp.rose.pc_rental.elements.MstDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MstDeviceRepository extends JpaRepository<MstDevice, String> {

    Optional<MstDevice> findByAssetNum(String assetNum);
    Optional<MstDevice>findByAssetNumAndDeleteFlagFalse(String assetNum);
    List<MstDevice> findByBreakdownFalseAndDeleteFlagFalse();
    List<MstDevice> findByAssetNumContainingOrMakerContainingOrOsContainingAndDeleteFlagFalse(String assetNum, String maker, String os);

}
