package test02.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import test02.dto.UserDto;
import test02.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> findByIdForUpdate(@Param("id") Long id);

    @Query("SELECT new package.UserDto(u.id, u.name, u.surname, u.balance, COUNT(t)) " +
            "FROM User u LEFT JOIN u.transactions t " +
            "GROUP BY u.id, u.name, u.surname, u.balance")
    Page<UserDto> findAllUsersWithTransferCount(Pageable pageable);
}