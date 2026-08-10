package tech.logicforge.moneymanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.logicforge.moneymanager.dto.AuthDto;
import tech.logicforge.moneymanager.dto.ProfileDto;
import tech.logicforge.moneymanager.service.IProfileService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final IProfileService profileService;

    @PostMapping("/register")
    public ResponseEntity<ProfileDto> registerProfile(@RequestBody ProfileDto profileDto) {
        ProfileDto responseProfile = profileService.registerProfile(profileDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseProfile);
    }

    @GetMapping("/activation")
    public ResponseEntity<String> activateProfile(@RequestParam String token) {
        boolean isActivated = profileService.activationToken(token);
        if(isActivated) {
            return ResponseEntity.ok("Profile activated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Activation token not found or user already used!");
        }
    }
}
