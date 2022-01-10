package com.example.demo.role;

import com.example.demo.appuser.AppUser;
import com.example.demo.appuser.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final RoleRepository repository;
    private final AppUserRepository userRepository;

    @Autowired
    public RoleService(RoleRepository repository, AppUserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<Role> getRoles() {
        return repository.findAll();
    }

    public Role getRole(Long roleId) {
        return repository.findById(roleId).orElseThrow(
                () -> new IllegalStateException("Role " + roleId + " does not exist")
        );
    }

    public void add(RoleDto roleDto) {
        Role role = new Role();
        mapDtoToEntity(roleDto, role);

        repository.save(role);
    }

    @Transactional
    public void update(Long roleId, String name, Set<Long> users) {
        Role role = repository.findById(roleId).orElseThrow(() ->
                new IllegalStateException("Role " + roleId + " does not exist")
        );

        if (name != null && name.length() > 0 && !Objects.equals(role.getName(), name)) {
            role.setName(name);
        }

        if (users != null && users.size() > 0) {
            for (Long userid : users) {
                AppUser user = userRepository.findById(userid).orElseThrow(
                        () -> new IllegalStateException("user " + userid + " does not exist")
                );
                role.addUser(user);
            }
        }
    }

    public void delete(Long id) {
        Role role = repository.findById(id).orElseThrow(
                () -> new IllegalStateException("Role " + id + " does not exist")
        );

        role.getUsers().forEach((AppUser user) -> user.removeRole(role));

        repository.deleteById(id);
    }

    private void mapDtoToEntity(RoleDto roleDto, Role role) {
        role.setName(roleDto.getName());
        role.setUsers(new HashSet<AppUser>());

        roleDto.getUsers()
                .forEach(userId ->
                    role.addUser(userRepository.findById(userId).orElseThrow(
                        () -> new IllegalStateException("User " + userId + " does not exist")
                    ))
                );
    }

    private RoleDto mapEntityToDto(Role role) {
        RoleDto roleDto = new RoleDto();

        roleDto.setId(role.getId());
        roleDto.setName(role.getName());

        roleDto.setUsers(
                role.getUsers()
                    .stream()
                    .map(AppUser::getId)
                    .collect(Collectors.toSet())
        );

        return roleDto;
    }
}
