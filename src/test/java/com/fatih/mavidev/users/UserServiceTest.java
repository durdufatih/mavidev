package com.fatih.mavidev.users;

import com.fatih.mavidev.domain.users.UserEntity;
import com.fatih.mavidev.domain.users.UserRepository;
import com.fatih.mavidev.domain.users.UserResponse;
import com.fatih.mavidev.domain.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @BeforeEach
    public void setUp() {
        // Initialize mocks created above
        MockitoAnnotations.initMocks(this);
        // Change behaviour of `resource`

    }

    @Test
    public void whenSendValidIDToFindByIdMethodExpectedObject() {

        UserEntity userEntity = UserEntity.builder().age(20).email("test@test.com").name("Hello").surname("World").build();
        when(userRepository.findById(any())).thenReturn(Optional.of(userEntity));

        UserResponse userResponseResult = userService.findById(1);
        assertEquals(userResponseResult.getEmail(), userEntity.getEmail());
    }

    @Test
    public void whenSendNullIDToFindByIdMethodExpectedThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.findById(null);
        });

        assertEquals("Id cant be null", exception.getMessage());
    }

    @Test
    public void whenSendValidIDToFindByIdMethodButNotFoundObjectExpectedThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.findById(30);
        });

        assertEquals("Item not found", exception.getMessage());

    }

    @Test
    public void whenSaveValidEntityToCreateMethodExpectedRealResult() {
        UserEntity userEntity = UserEntity.builder().age(20).email("test@test.com").name("Hello").surname("World").build();
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        UserResponse userResponseResult = userService.create(userEntity);
        assertEquals(userResponseResult.getEmail(), userEntity.getEmail());

    }

    @Test
    public void whenSendNullEntityToCreateMethodExpectedThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.create(null);
        });

        assertEquals("User cant be null", exception.getMessage());

    }

    @Test
    public void whenReturnNullEntityFromApplyMethodExpectedThrowException() {
        UserEntity userEntity = UserEntity.builder().age(20).email("test@test.com").name("Hello").surname("World").build();
        when(userRepository.save(any(UserEntity.class))).thenReturn(null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.create(userEntity);
        });

        assertEquals("User not found", exception.getMessage());

    }

    @Test
    public void whenSendValidIdAndValidObjectToUpdateMethodExpectedResult() {

        UserEntity userEntity = UserEntity.builder().age(20).email("test@test.com").name("Hello").surname("World").build();
        UserEntity userEntityResult = UserEntity.builder().age(20).email("test1@test.com").name("Hello").surname("World").build();
        when(userRepository.findById(any())).thenReturn(Optional.of(userEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntityResult);

        UserResponse userResponseResult = userService.update(1, userEntity);
        assertEquals(userResponseResult.getEmail(), userEntityResult.getEmail());
    }


    @Test
    public void whenSendNullIDAndNullObjectToUpdateMethodExpectedThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.update(null, null);
        });

        assertEquals("Params cant be null", exception.getMessage());
    }

    @Test
    public void whenSendValidIDAndNullObjectToUpdateMethodExpectedThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.update(10, null);
        });

        assertEquals("Params cant be null", exception.getMessage());
    }

    @Test
    public void whenSendNullIDAndValidObjectToUpdateMethodExpectedThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.update(10, null);
        });

        assertEquals("Params cant be null", exception.getMessage());
    }

    @Test
    public void whenSendValidIDAndValidUserToUpdateMethodButNotFoundObjectExpectedThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.update(30, new UserEntity());
        });

        assertEquals("Item not found", exception.getMessage());

    }

    @Test
    public void whenCallListMethodCheckCallCount() {
        when(userRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(new ArrayList<>()));
        userService.list(PageRequest.of(10, 20));
        Mockito.verify(userRepository, Mockito.times(1)).findAll(PageRequest.of(10, 20));
    }

    @Test
    public void whenCallDeleteMethodCheckCallCount() {
        Mockito.doNothing().when(userRepository).deleteById(anyInt());
        userService.delete(1);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(1);
    }

    @Test
    public void whenSendNullIDToDeleteMethodExpectedThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.delete(null);
        });

        assertEquals("Id cant be null", exception.getMessage());
    }


}
