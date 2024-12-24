package mk.frizer.web.rest;

import mk.frizer.domain.BaseUser;
import mk.frizer.domain.Employee;
import mk.frizer.domain.Salon;
import mk.frizer.domain.dto.BaseUserAddDTO;
import mk.frizer.domain.dto.BaseUserUpdateDTO;
import mk.frizer.domain.dto.simple.BaseUserSimpleDTO;
import mk.frizer.domain.enums.Role;
import mk.frizer.domain.exceptions.UserNotFoundException;
import mk.frizer.service.BaseUserService;
import mk.frizer.service.SalonService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/api/users", "/api/user"})
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class UserController {
    private final BaseUserService baseUserService;
    private final SalonService salonService;

    public UserController(BaseUserService baseUserService, SalonService salonService) {
        this.baseUserService = baseUserService;
        this.salonService = salonService;
    }

    @GetMapping()
    public List<BaseUserSimpleDTO> getUsers(){
        return baseUserService.getBaseUsers().stream().map(BaseUser::toDto).toList();
    }

    @GetMapping("/me")
    public ResponseEntity<BaseUserSimpleDTO> getUserDetails() {
        Optional<BaseUser> user = baseUserService.getUserFromAuthentication(SecurityContextHolder.getContext().getAuthentication());
        return user.map(baseUser -> ResponseEntity.ok(baseUser.toDto()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseUserSimpleDTO> getUser(@PathVariable Long id){
        return this.baseUserService.getBaseUserById(id)
                .map(user -> ResponseEntity.ok().body(user.toDto()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<BaseUserSimpleDTO> createUser(@RequestBody BaseUserAddDTO baseUserAddDTO) {
        return this.baseUserService.createBaseUser(baseUserAddDTO)
                .map(user -> ResponseEntity.ok().body(user.toDto()))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<BaseUserSimpleDTO> updateUser(@PathVariable Long id, @RequestBody BaseUserUpdateDTO baseUserUpdateDTO) {
        return this.baseUserService.updateBaseUser(id, baseUserUpdateDTO)
                .map(user -> ResponseEntity.ok().body(user.toDto()))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/edit-password/{id}")
    public ResponseEntity<BaseUserSimpleDTO> updatePasswordForUser(@PathVariable Long id, @RequestParam String password){
        return this.baseUserService.changeBaseUserPassword(id, password)
                .map(user -> ResponseEntity.ok().body(user.toDto()))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseUserSimpleDTO> deleteUserById(@PathVariable Long id) {
        Optional<BaseUser> user = this.baseUserService.deleteBaseUserById(id);
        try{
            this.baseUserService.getBaseUserById(id);
            return ResponseEntity.badRequest().build();
        }
        catch(UserNotFoundException exception){
            return ResponseEntity.ok().body(user.get().toDto());
        }
    }
    @GetMapping("/available")
    public List<BaseUserSimpleDTO> getAvailableEmployeesForSalon() {
        return baseUserService.getBaseUsers().stream()
                .filter(user -> !user.getRoles().contains(Role.ROLE_EMPLOYEE) && !user.getRoles().contains(Role.ROLE_OWNER))
                .map(BaseUser::toDto).toList();
         }

    @GetMapping("/search")
    public List<BaseUserSimpleDTO> searchUsers(@RequestParam("query") String query) {
        return baseUserService.searchByUsername(query).stream()
                .map(BaseUser::toDto)
                .toList();
    }

}
