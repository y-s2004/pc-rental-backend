package jp.rose.pc_rental.services;

import jp.rose.pc_rental.entity.MstUser;
import jp.rose.pc_rental.repositories.MstUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MstUserService {

    private final MstUserRepository mstUserRepository;

    /*  コンストラクトインジェクション
        MstUserService クラスのインスタンスを作るときに、
        Springが MstUserRepository を自動で探してインスタンスを注入し、this.mstUserRepository に渡してくれる。
    */
    @Autowired
    public MstUserService(MstUserRepository mstUserRepository){
        this.mstUserRepository = mstUserRepository;
    }

    //新規ユーザ登録
    public MstUser createMstUser(MstUser mstUser){
        mstUser.setDeleteFlag(false);
        mstUser.setRegisterDate(LocalDateTime.now());
        return mstUserRepository.save(mstUser);
    }
    //ユーザ削除
    public void deleteUser(String employeeNo){
        MstUser target = mstUserRepository.findById(employeeNo)
                .orElseThrow(() -> new IllegalArgumentException("そのユーザーは存在しません"));
        target.setDeleteFlag(true);
        mstUserRepository.save(target);
    }

    //ユーザ情報の更新
    public MstUser updateUser(String employeeNo, MstUser mstUser){
        MstUser target = mstUserRepository.findById(employeeNo)
                .orElseThrow(() -> new IllegalArgumentException("User not found: "+ employeeNo));

        target.setName(mstUser.getName());
        target.setNameKana(mstUser.getNameKana());
        target.setDepartment(mstUser.getDepartment());
        target.setTelNo(mstUser.getTelNo());
        target.setMailAddress(mstUser.getMailAddress());
        target.setAge(mstUser.getAge());
        target.setGender(mstUser.getGender());
        target.setPosition(mstUser.getPosition());
        target.setAccountLevel(mstUser.getAccountLevel());
        target.setRetireDate(mstUser.getRetireDate());
        target.setUpdateDate(LocalDateTime.now());

        return mstUserRepository.save(target);
    }

    //退職したユーザの退職日の設定
    public void retireUser(String employeeNo, LocalDateTime retireDate){
        MstUser target = mstUserRepository.findById(employeeNo)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + employeeNo));
        target.setRetireDate(retireDate);
        mstUserRepository.save(target);
    }

    //全ユーザ情報の取得
    public List<MstUser> getAllUsers(){
        return mstUserRepository.findByDeleteFlagFalse();
    }

    //ユーザの詳細情報の取得
    public MstUser getUserDetail(String EmployeeNo){
        return mstUserRepository.findByEmployeeNoAndDeleteFlagFalse(EmployeeNo).orElse(null);
    }

    //ユーザ検索
    public List<MstUser> searchUsers(String keyword) {
        return mstUserRepository.findByEmployeeNoContainingOrNameContainingOrDepartmentContaining(keyword, keyword, keyword);
    }

    public Optional<MstUser> login(String employeeNo, String password){
        return mstUserRepository.findByEmployeeNoAndDeleteFlagFalse(employeeNo)
                .filter(user -> user.getPassword().equals(password));
    }

    //
    public Optional<MstUser> findByEmployeeNo(String employeeNo) {
        return mstUserRepository.findByEmployeeNoAndDeleteFlagFalse(employeeNo);
    }


}
