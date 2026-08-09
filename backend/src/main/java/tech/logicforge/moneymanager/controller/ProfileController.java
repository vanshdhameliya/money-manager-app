package tech.logicforge.moneymanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.logicforge.moneymanager.dto.ProfileDto;
import tech.logicforge.moneymanager.service.IProfileService;
import tech.logicforge.moneymanager.service.ProfileServiceImpl;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final IProfileService profileService;

    @PostMapping("/register")
    public ResponseEntity<ProfileDto> registerProfile(@RequestBody ProfileDto profileDto) {
        ProfileDto responseProfile = profileService.registerProfile(profileDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseProfile);
    }
}
