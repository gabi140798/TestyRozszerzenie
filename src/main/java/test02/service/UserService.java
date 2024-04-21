package test02.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import test02.dto.UserDto;
import test02.exceptions.UserNotFoundException;
import test02.model.User;
import test02.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public User findUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No user found with ID: " + id));
    }

    public Page<UserDto> getUsersWithTransferCount(Pageable pageable) {
        return userRepository.findAllUsersWithTransferCount(pageable);
    }

}
