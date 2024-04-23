package test02.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import test02.dto.UserDto;
import test02.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<User> findByIdForUpdate(Long id);

    Page<UserDto> findAllUsersWithTransferCount(Pageable pageable);
}