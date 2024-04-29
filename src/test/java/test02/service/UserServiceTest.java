package test02.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import test02.dto.UserDto;
import test02.exceptions.UserNotFoundException;
import test02.model.User;
import test02.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    public void testFindUserByIdFound() {
        Long userId = 1L;
        User user = new User("Kamil", "Nowak",1000);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User foundUser = userService.findUserById(userId);
        assertNotNull(foundUser);
        assertEquals("Kamil", foundUser.getName());
        verify(userRepository).findById(userId);
    }

    @Test
    public void testFindUserByIdNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.findUserById(userId);
        });

        assertEquals("No user found with ID: " + userId, exception.getMessage());
    }

    @Test
    public void testGetUsersWithTransferCount() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        UserDto userDto = new UserDto(1L,"Kamil","Nowak",1000,null);
        Page<UserDto> expectedPage = new PageImpl<>(Collections.singletonList(userDto));

        when(userRepository.findAllUsersWithTransferCount(pageRequest)).thenReturn(expectedPage);

        Page<UserDto> resultPage = userService.getUsersWithTransferCount(pageRequest);
        assertNotNull(resultPage);
        assertEquals(1, resultPage.getTotalElements());
        assertEquals("Kamil", resultPage.getContent().get(0).name());
    }
}