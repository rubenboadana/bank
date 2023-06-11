package com.iobuilders.application;

import com.iobuilders.domain.UserRepository;
import com.iobuilders.domain.dto.User;
import com.iobuilders.domain.dto.UserID;
import com.iobuilders.domain.dto.UserObjectMother;
import com.iobuilders.domain.exceptions.UserAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
final class UserServiceTest {

    private static final String USER_ID = "26929514-237c-11ed-861d-0242ac120002";
    @Mock
    private UserRepository userRepositoryMock;
    @InjectMocks
    private UserServiceImpl sut;

    @Test
    void should_returnUserId_when_createSucceed() {
        //Given
        User user = UserObjectMother.basic();
        UserID expectedId = new UserID(USER_ID);
        doReturn(expectedId).when(userRepositoryMock).create(user);

        //When
        UserID newId = sut.create(user);

        //Then
        assertThat(newId).isEqualTo(expectedId);
    }

    @Test
    void should_throwException_when_UserAlreadyExists() {
        //Given
        User user = UserObjectMother.basic();
        doReturn(Optional.of(user)).when(userRepositoryMock).findByUserName(user.userName());

        //When/Then
        assertThrows(UserAlreadyExistsException.class, () -> sut.create(user));
    }

}
