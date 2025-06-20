package jp.rose.pc_rental.controllers;

import jp.rose.pc_rental.entity.TrnRental;
import jp.rose.pc_rental.services.TrnRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rental")
public class TrnRentalApiController {

    private final TrnRentalService trnRentalService;

    @Autowired
    public TrnRentalApiController(TrnRentalService trnRentalService){
        this.trnRentalService = trnRentalService;
    }

    @PostMapping
    public ResponseEntity<String> rental(@RequestBody TrnRental trnRental){
        String result = trnRentalService.rental(trnRental);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{asset_num}")
    public ResponseEntity<TrnRental> updateRental(@PathVariable("asset_num") String assetNum, @RequestBody TrnRental req){
        TrnRental updated = trnRentalService.updateRental(assetNum, req);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{asset_num}/return")
    public ResponseEntity<String> returnRental(@PathVariable("asset_num") String assetNum, @RequestBody Map<String, String> requestBody) {
        String userNo = requestBody.get("user_no");
        if (userNo == null || userNo.isEmpty()) {
            return ResponseEntity.badRequest().body("ユーザ番号が必要です");
        }

        String result = trnRentalService.returnRental(assetNum, userNo);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/rented")
    public ResponseEntity<List<TrnRental>> getAllAvailableDevice(){
        List<TrnRental> availableDevice = trnRentalService.getAllAvailableDevice();
        return ResponseEntity.ok(availableDevice);
    }

    @GetMapping("/{asset_num}")
    public ResponseEntity<TrnRental> getDetail(@PathVariable("asset_num") String assetNum){
        TrnRental rental = trnRentalService.getDetail(assetNum);
        return  ResponseEntity.ok(rental);
    }

    @GetMapping("/over")
    public ResponseEntity<List<TrnRental>> getAllOverDevice(){
        List<TrnRental> overDevice = trnRentalService.getAllOverDevice();
        return ResponseEntity.ok(overDevice);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TrnRental>> searchRental(@RequestParam("keyword") String keyword){
        List<TrnRental> result = trnRentalService.searchRental(keyword);
        return ResponseEntity.ok(result);
    }
}
