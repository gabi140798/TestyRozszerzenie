package test02.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import test02.dto.UserDto;
import test02.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<UserDto> findAllUsersWithTransferCount(Pageable pageable);
}
