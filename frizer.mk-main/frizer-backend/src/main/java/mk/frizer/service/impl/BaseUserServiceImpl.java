package mk.frizer.service.impl;

import jakarta.transaction.Transactional;
import mk.frizer.domain.BaseUser;
import mk.frizer.domain.Customer;
import mk.frizer.domain.dto.BaseUserAddDTO;
import mk.frizer.domain.dto.BaseUserUpdateDTO;
import mk.frizer.domain.exceptions.UserAlreadyExistsException;
import mk.frizer.domain.exceptions.UserNotFoundException;
import mk.frizer.repository.BaseUserRepository;
import mk.frizer.repository.CustomerRepository;
import mk.frizer.service.BaseUserService;
import mk.frizer.utilities.FormValidator;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class BaseUserServiceImpl implements BaseUserService {
    private final BaseUserRepository baseUserRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public BaseUserServiceImpl(BaseUserRepository baseUserRepository, CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.baseUserRepository = baseUserRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<BaseUser> getBaseUsers() {
        return baseUserRepository.findAll();
    }

    @Override
    public Optional<BaseUser> getBaseUserById(Long id) {
        BaseUser user = baseUserRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return Optional.of(user);
    }

    @Override
    public Optional<BaseUser> getBaseUserByEmail(String email) {
        return baseUserRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public Optional<BaseUser> createBaseUser(BaseUserAddDTO baseUserAddDTO) {
        if (baseUserRepository.existsByEmail(baseUserAddDTO.getEmail())) {
            throw new UserAlreadyExistsException("User with this email already exists.");
        }

        if (baseUserRepository.existsByPhoneNumber(baseUserAddDTO.getPhoneNumber())) {
            throw new UserAlreadyExistsException("User with this phone number already exists.");
        }
        BaseUser user = baseUserRepository.save(new BaseUser(baseUserAddDTO.getEmail(), passwordEncoder.encode(baseUserAddDTO.getPassword()), baseUserAddDTO.getFirstName(), baseUserAddDTO.getLastName(), baseUserAddDTO.getPhoneNumber()));
        Customer customer = customerRepository.save(new Customer(user));
        return Optional.of(user);
    }

    @Override
    @Transactional
    public Optional<BaseUser> updateBaseUser(Long id, BaseUserUpdateDTO baseUserUpdateDTO) {
        BaseUser user = getBaseUserById(id).get();

        if (FormValidator.isNameValid(baseUserUpdateDTO.getFirstName())) {
            user.setFirstName(baseUserUpdateDTO.getFirstName());
        }
        if (FormValidator.isNameValid(baseUserUpdateDTO.getLastName())) {
            user.setLastName(baseUserUpdateDTO.getLastName());
        }
        if (FormValidator.isPhoneNumberValid(baseUserUpdateDTO.getPhoneNumber())) {
            user.setPhoneNumber(baseUserUpdateDTO.getPhoneNumber());
        }

        user.setFirstName(baseUserUpdateDTO.getFirstName());
        user.setLastName(baseUserUpdateDTO.getLastName());
        user.setPhoneNumber(baseUserUpdateDTO.getPhoneNumber());

        return Optional.of(baseUserRepository.save(user));
    }

    @Override
    @Transactional
    public Optional<BaseUser> changeBaseUserPassword(Long id, String password) {
        BaseUser user = getBaseUserById(id).get();
        user.setPassword(passwordEncoder.encode(password));
        return Optional.of(baseUserRepository.save(user));
    }

    @Override
    @Transactional
    public Optional<BaseUser> deleteBaseUserById(Long id) {
        //try catch?
        BaseUser user = getBaseUserById(id).get();
        baseUserRepository.deleteById(id);
        return Optional.of(user);
    }

    @Transactional
    public Optional<BaseUser> getUserFromAuthentication(Authentication authentication){
        if (authentication != null && authentication.getPrincipal() != null)
            return Optional.of((BaseUser) authentication.getPrincipal());
        return Optional.empty();
    }

    @Override
    public List<BaseUser> searchByUsername(String query) {
        return baseUserRepository.findByEmailContaining(query);

    }
}
