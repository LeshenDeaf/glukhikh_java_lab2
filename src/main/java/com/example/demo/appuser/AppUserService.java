package com.example.demo.appuser;

import com.example.demo.role.Role;
import com.example.demo.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository, RoleRepository roleRepository) {
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
    }

    public List<AppUser> getAppUsers() {
        return appUserRepository.findAll();
    }

    public AppUser getAppUser(Long userId) {
        return appUserRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("User " + userId + " does not exist")
        );
    }

    public void addNewAppUser(AppUserDto userDto) {
        checkEmail(userDto.getEmail());

        AppUser user = new AppUser();
        mapDtoToEntity(userDto, user);
        appUserRepository.save(user);
    }

    public void delete(Long id) {
        boolean userExists = appUserRepository.existsById(id);

        if (!userExists) {
            throw new IllegalStateException("User " + id + " does not exist");
        }

        appUserRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, String name, String email, Set<Long> roles) {
        AppUser user = appUserRepository.findById(id).orElseThrow(() ->
             new IllegalStateException("User " + id + " does not exist")
        );

        if (name != null && name.length() > 0 && !Objects.equals(user.getName(), name)) {
            user.setName(name);
        }

        if (email != null && email.length() > 0 && !Objects.equals(user.getEmail(), email)) {
            checkEmail(email);

            user.setEmail(email);
        }

        if (roles != null && roles.size() > 0) {
            for (Long roleId : roles) {
                Role role = roleRepository.findById(roleId).orElseThrow(
                        () -> new IllegalStateException("Role " + roleId + " does not exist")
                );
                user.addRole(role);
            }
        }
    }

    private void checkEmail(String email) {
        Optional<AppUser> userOptional = appUserRepository.findAppUserByEmail(email);

        if (userOptional.isPresent()) {
            throw new IllegalStateException("Email already taken");
        }
    }

    private void mapDtoToEntity(AppUserDto userDto, AppUser user) {
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setDob(userDto.getDob());
        Set<Role> roles = new HashSet<>();

        userDto.getRoles().forEach(roleId -> {
            Role role = roleRepository.findById(roleId).orElseThrow(
                    () -> new IllegalStateException("Role " + roleId + " does not exist")
            );

            roles.add(role);
        });

        user.setRoles(roles);
    }

}

/*
 new AppUser(
                        1L,
                        "Alex",
                        "lexuar2000@gmail.com",
                        LocalDate.of(2000, 8, 5)
                )
 */
