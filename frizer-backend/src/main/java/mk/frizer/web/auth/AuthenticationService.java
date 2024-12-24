package mk.frizer.web.auth;

import lombok.RequiredArgsConstructor;
import mk.frizer.config.JwtService;
import mk.frizer.domain.BaseUser;
import mk.frizer.domain.dto.BaseUserAddDTO;
import mk.frizer.domain.exceptions.UserCannotBeCreatedException;
import mk.frizer.domain.exceptions.UserNotFoundException;
import mk.frizer.repository.BaseUserRepository;
import mk.frizer.service.BaseUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final BaseUserService baseUserService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final static String SUCCESS_REGISTER = "Successfully registered.";

    public RegisterResponse register(RegisterRequest request) {
        BaseUser user = baseUserService.createBaseUser(
                new BaseUserAddDTO(
                        request.getEmail(),
                        request.getPassword(),
                        request.getFirstName(),
                        request.getLastName(),
                        request.getPhoneNumber()
                )
        ).orElseThrow(UserCannotBeCreatedException::new);

        return RegisterResponse
                .builder()
                .message(SUCCESS_REGISTER)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        BaseUser user = baseUserService.getBaseUserByEmail(request.getEmail())
                .orElseThrow(UserNotFoundException::new);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }
}
