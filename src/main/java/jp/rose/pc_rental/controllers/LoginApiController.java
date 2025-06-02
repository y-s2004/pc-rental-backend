package jp.rose.pc_rental.controllers;

import jakarta.servlet.http.HttpSession;
import jp.rose.pc_rental.elements.MstUser;
import jp.rose.pc_rental.services.MstUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
public class LoginApiController {

    @Autowired
    private MstUserService mstUserService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginData, HttpSession session) {
        String employeeNo = loginData.get("employee_no");
        String password = loginData.get("password");

        Optional<MstUser> userOptional = mstUserService.findByEmployeeNo(employeeNo);

        if(userOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("ログイン情報が存在しません");
        }

        MstUser user = userOptional.get();

        if(user.getDeleteFlag() || user.getRetireDate() != null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("ログイン情報が存在しません");
        }

        if(!user.getPassword().equals(password)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("ログイン情報が存在しません");
        }

        session.setAttribute("LOGIN_USER", user);
        return ResponseEntity.ok("ログイン完了");
    }
}
