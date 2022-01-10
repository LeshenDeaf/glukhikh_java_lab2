package com.example.demo.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "api/v1/role")
public class RoleController {
    private final RoleService service;

    @Autowired
    public RoleController(RoleService service) {
        this.service = service;
    }

    @GetMapping
    public List<Role> getRoles() {
        return service.getRoles();
    }

    @GetMapping(path="{roleId}")
    public Role getRole(@PathVariable("roleId") Long roleId) {
        return service.getRole(roleId);
    }

    @PostMapping
    public void create(@RequestBody RoleDto roleDto) {
        service.add(roleDto);
    }

    @PutMapping(path = "{roleId}")
    public void update(@PathVariable("roleId") Long id,
                       @RequestParam(required = false) String name,
                       @RequestParam(required = false) Set<Long> users
    ) {
        service.update(id, name, users);
    }

    @DeleteMapping(path = "{roleId}")
    public void delete(@PathVariable("roleId") Long id) {
        service.delete(id);
    }

}
