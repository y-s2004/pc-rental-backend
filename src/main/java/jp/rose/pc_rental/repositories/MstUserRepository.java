package jp.rose.pc_rental.repositories;

import jp.rose.pc_rental.elements.MstUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MstUserRepository extends JpaRepository<MstUser, String> {

    Optional<MstUser> findByEmployeeNo(String employeeNo);
    Optional<MstUser> findByEmployeeNoAndDeleteFlagFalse(String employeeNo);
    List<MstUser> findByDeleteFlagFalse();
    List<MstUser> findByEmployeeNoContainingOrNameContainingOrDepartmentContaining(String employeeNo, String name, String department);


}
