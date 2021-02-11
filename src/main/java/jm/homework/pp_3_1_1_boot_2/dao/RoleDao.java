package jm.homework.pp_3_1_1_boot_2.dao;

import jm.homework.pp_3_1_1_boot_2.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleDao extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}
