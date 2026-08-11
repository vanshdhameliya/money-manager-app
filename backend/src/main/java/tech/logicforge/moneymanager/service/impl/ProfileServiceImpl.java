package tech.logicforge.moneymanager.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.logicforge.moneymanager.util.JwtUtil;
import tech.logicforge.moneymanager.dto.AuthDto;
import tech.logicforge.moneymanager.dto.ProfileDto;
import tech.logicforge.moneymanager.entity.ProfileEntity;
import tech.logicforge.moneymanager.mapper.ProfileMapper;
import tech.logicforge.moneymanager.repository.ProfileRepository;
import tech.logicforge.moneymanager.service.IEmailService;
import tech.logicforge.moneymanager.service.IProfileService;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements IProfileService {

    private final ProfileRepository profileRepository;
    private final IEmailService emailService;
    private final ProfileMapper profileMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public ProfileDto registerProfile(ProfileDto profileDto) {

        ProfileEntity newProfile = profileMapper.toEntity(profileDto);
        newProfile.setPassword(passwordEncoder.encode(profileDto.getPassword()));

        System.out.println("DTO EMAIL    = [" + profileDto.getEmail() + "]");
        System.out.println("ENTITY EMAIL = [" + newProfile.getEmail() + "]");

        newProfile.setActivationToken(UUID.randomUUID().toString());
        newProfile = profileRepository.save(newProfile);

        System.out.println("SAVED EMAIL  = [" + newProfile.getEmail() + "]");

        String activationLink =
                "http://localhost:8080/api/v1.0/activation?token="
                        + newProfile.getActivationToken();

        String subject = "Activate your Money Manager account";

        String body =
                "Click on the following link to activate your account: "
                        + activationLink;

        emailService.sendEmail(
                newProfile.getEmail(),
                subject,
                body
        );

        return profileMapper.toDto(newProfile);
    }


    @Override
    public boolean activationToken(String activationToken) {
        return profileRepository.findByActivationToken(activationToken)
                .map(profile -> {
                    profile.setIsActive(true);
                    profileRepository.save(profile);
                    return true;
                }).orElse(false);
    }

    public boolean isAccountActive(String email) {
        return profileRepository.findByEmail(email)
                .map(ProfileEntity::getIsActive).orElse(false);
    }

    @Override
    public Map<String, Object> authenticateAndGenerateToken(AuthDto authDto) {
        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    authDto.getEmail(),
                                    authDto.getPassword()
                            )
                    );

            // Get authenticated user
            UserDetails userDetails =
                    (UserDetails) authentication.getPrincipal();

            // Generate JWT token
            String token = jwtUtil.generateToken(userDetails);

            return Map.of(
                    "token", token,
                    "user", getPublicProfile(authDto.getEmail())
            );

        } catch (Exception e) {

            throw new RuntimeException("Invalid email or password");
        }
    }

    public ProfileEntity getCurrentProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return profileRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Profile not found with email: "+authentication.getName()));
    }

    public ProfileDto getPublicProfile(String email) {
        ProfileEntity currentUser = null;
        if(email == null) {
            currentUser = getCurrentProfile();
        } else {
            currentUser = profileRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Profile not found with email: "+email));
        }
        return profileMapper.toDto(currentUser);
    }


}
