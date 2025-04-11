package mate.academy.bookstore.service;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.UserLoginResponseDto;
import mate.academy.bookstore.dto.UserRegistrationRequestDto;
import mate.academy.bookstore.exception.EntityNotFoundException;
import mate.academy.bookstore.exception.RegistrationException;
import mate.academy.bookstore.mapper.UserMapper;
import mate.academy.bookstore.model.Role;
import mate.academy.bookstore.model.User;
import mate.academy.bookstore.repository.user.RoleRepository;
import mate.academy.bookstore.repository.user.UserRepository;
import mate.academy.bookstore.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ShoppingCartService shoppingCartService;
    private final JwtUtil jwtUtil;

    @Override
    public UserLoginResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException("Can't register user with email: "
                    + requestDto.getEmail());
        }
        User user = userMapper.toUserModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        Role userRole = roleRepository.findByRole(Role.RoleName.ROLE_USER)
                .orElseThrow(() -> new EntityNotFoundException("Role "
                        + Role.RoleName.ROLE_USER + " not found"));
        user.setRoles(Set.of(userRole));
        userRepository.save(user);
        shoppingCartService.createNewShoppingCart(user);
        String token = jwtUtil.generateToken(user.getEmail());
        return new UserLoginResponseDto(token);
    }
}
