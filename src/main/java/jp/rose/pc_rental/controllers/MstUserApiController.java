package jp.rose.pc_rental.controllers;

import jp.rose.pc_rental.entity.MstUser;
import jp.rose.pc_rental.services.MstUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class MstUserApiController {

    private final MstUserService mstUserService;

    @Autowired
    public MstUserApiController(MstUserService mstUserService){
        this.mstUserService = mstUserService;
    }

    @PostMapping
    public ResponseEntity<MstUser> createUser(@RequestBody MstUser mstUser){
        MstUser saved = mstUserService.createMstUser(mstUser);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{employee_no}")
    public ResponseEntity<Void> deleteUser(@PathVariable("employee_no") String employeeNo){
        mstUserService.deleteUser(employeeNo);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{employee_no}")
    public ResponseEntity<MstUser> updateUser(@PathVariable("employee_no") String employeeNo, @RequestBody MstUser req){
        System.out.println("受信2: " + employeeNo);
        MstUser updated = mstUserService.updateUser(employeeNo, req);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<List<MstUser>> getAllUsers(){
        List<MstUser> allUsers = mstUserService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/{employee_no}")
    public ResponseEntity<MstUser> getUserDetail(@PathVariable("employee_no") String employee_no){
        MstUser result = mstUserService.getUserDetail(employee_no);
        return  ResponseEntity.ok(result);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MstUser>> searchUsers(@RequestParam("keyword") String keyword) {
        List<MstUser> result = mstUserService.searchUsers(keyword);
        return ResponseEntity.ok(result);
    }

}
