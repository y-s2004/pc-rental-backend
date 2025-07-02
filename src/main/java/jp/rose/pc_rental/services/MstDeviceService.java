package jp.rose.pc_rental.services;

import jp.rose.pc_rental.entity.MstDevice;
import jp.rose.pc_rental.repositories.MstDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MstDeviceService {

    private final MstDeviceRepository mstDeviceRepository;

    @Autowired
    public MstDeviceService(MstDeviceRepository mstDeviceRepository){
        this.mstDeviceRepository = mstDeviceRepository;
    }

    public MstDevice createMstDevice(MstDevice mstDevice){
        mstDevice.setRegistrationDate(LocalDateTime.now());
        return mstDeviceRepository.save(mstDevice);
    }

    public void deleteDevice(String assetNum){
        MstDevice target = mstDeviceRepository.findByAssetNum(assetNum)
                .orElseThrow(() -> new IllegalArgumentException("User not found: "+ assetNum));
        target.setDeleteFlag(true);
        mstDeviceRepository.save(target);
    }

    public MstDevice updateDevice(String assetNum, MstDevice mstDevice){
        MstDevice target = mstDeviceRepository.findByAssetNum(assetNum)
                .orElseThrow(() -> new IllegalArgumentException("User not found: "+ assetNum));

        target.setPlace(mstDevice.getPlace());
        target.setOs(mstDevice.getOs());
        target.setMaker(mstDevice.getMaker());
        target.setMemory(mstDevice.getMemory());
        target.setDiscCapacity(mstDevice.getDiscCapacity());
        target.setGraphicBoard(mstDevice.getGraphicBoard());
        target.setRemarks(mstDevice.getRemarks());
        target.setInventoryDate(mstDevice.getInventoryDate());
        target.setUpdateDate(LocalDateTime.now());

        return mstDeviceRepository.save(target);
    }

    public List<MstDevice> getAllDevice(){
        return mstDeviceRepository.findByBreakdownFalseAndDeleteFlagFalse();
    }

    public MstDevice getDeviceDetail(String assetNum){
        return mstDeviceRepository.findByAssetNumAndDeleteFlagFalse(assetNum).orElse(null);
    }

    public List<MstDevice> searchDevices(String keyword) {
        return mstDeviceRepository.findByAssetNumContainingOrMakerContainingOrOsContainingAndDeleteFlagFalse(keyword, keyword, keyword);
    }

}
