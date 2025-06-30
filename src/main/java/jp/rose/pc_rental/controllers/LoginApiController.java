package jp.rose.pc_rental.controllers;

import jakarta.servlet.http.HttpSession;
import jp.rose.pc_rental.entity.MstUser;
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
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData, HttpSession session) {
        System.out.println("ログインリクエスト受信");

        String employeeNo = loginData.get("employee_no");
        String password = loginData.get("password");

        System.out.println("入力された社員番号: " + employeeNo);
        System.out.println("入力されたパスワード: " + password);


        Optional<MstUser> userOptional = mstUserService.findByEmployeeNo(employeeNo);

        if(userOptional.isEmpty()){
            System.out.println("ユーザーが見つかりません");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("ログイン情報が存在しません");
        }

        MstUser user = userOptional.get();
        System.out.println("該当ユーザーあり: " + user.getEmployeeNo());
        System.out.println("DBのパスワード: [" + user.getPassword() + "]");
        System.out.println("送信されたパスワード: [" + password + "]");

        if(user.isDeleteFlag() || user.getRetireDate() != null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("ログイン情報が存在しません");
        }

        if(!user.getPassword().equals(password)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("ログイン情報が存在しません");
        }


        session.setAttribute("LOGIN_USER", user);

        Map<String, Object> response = new java.util.HashMap<>();
        response.put("message", "ログイン完了");
        Map<String, Object> userInfo = new java.util.HashMap<>();
        userInfo.put("authority", user.getAccountLevel());
        userInfo.put("name", user.getName());
        response.put("user", userInfo);

        return ResponseEntity.ok(response);
    }
}
