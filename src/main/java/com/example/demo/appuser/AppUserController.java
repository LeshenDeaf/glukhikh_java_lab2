package com.example.demo.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping(path = "api/v1/appuser")
public class AppUserController {

    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping
    public List<AppUser> getAppUsers() {
        return appUserService.getAppUsers();
    }

    @GetMapping(path="{userId}")
    public AppUser getAppUser(@PathVariable("userId") Long userId) {
        return appUserService.getAppUser(userId);
    }

    @PostMapping
    public void registerNewAppUser(@RequestBody AppUserDto user) {
        appUserService.addNewAppUser(user);
    }

    @DeleteMapping(path = "{appUserId}")
    public void deleteAppUser(@PathVariable("appUserId") Long id) {
        appUserService.delete(id);
    }

    @PutMapping(path = "{appUserId}")
    public void updateAppUser(
            @PathVariable("appUserId") Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Set<Long> roles
    ) {
        appUserService.update(id, name, email, roles);
    }
}
