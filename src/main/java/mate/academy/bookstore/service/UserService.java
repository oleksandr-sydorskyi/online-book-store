package mate.academy.bookstore.service;

import mate.academy.bookstore.dto.UserLoginResponseDto;
import mate.academy.bookstore.dto.UserRegistrationRequestDto;
import mate.academy.bookstore.exception.RegistrationException;

public interface UserService {
    UserLoginResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException;
}
