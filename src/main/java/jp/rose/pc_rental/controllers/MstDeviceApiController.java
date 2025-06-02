package jp.rose.pc_rental.controllers;


import jp.rose.pc_rental.elements.MstDevice;
import jp.rose.pc_rental.services.MstDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/device")
public class MstDeviceApiController {

    private final MstDeviceService mstDeviceService;

    @Autowired
    public MstDeviceApiController(MstDeviceService mstDeviceService){
        this.mstDeviceService = mstDeviceService;
    }

    @PostMapping
    public ResponseEntity<MstDevice> createDevice(@RequestBody MstDevice mstDevice){
        MstDevice saved = mstDeviceService.createMstDevice(mstDevice);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{asset_num}")
    public ResponseEntity<Void> deleteDevice(@PathVariable("asset_num") String assetNum){
        mstDeviceService.deleteDevice(assetNum);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{asset_num}")
    public ResponseEntity<MstDevice> updateDevice(@PathVariable("asset_num") String assetNum, @RequestBody MstDevice req){
        MstDevice updated = mstDeviceService.updateDevice(assetNum, req);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<List<MstDevice>> getAllDevice(){
        List<MstDevice> allDevice = mstDeviceService.getAllDevice();
        return  ResponseEntity.ok(allDevice);
    }

    @GetMapping("/{asset_num}")
    public ResponseEntity<MstDevice> getDetail(@PathVariable("asset_num") String assetNum){
        MstDevice result = mstDeviceService.getDeviceDetail(assetNum);
        return  ResponseEntity.ok(result);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MstDevice>> searchDevices(@RequestParam("keyword") String keyword) {
        List<MstDevice> result = mstDeviceService.searchDevices(keyword);
        return ResponseEntity.ok(result);
    }

}
