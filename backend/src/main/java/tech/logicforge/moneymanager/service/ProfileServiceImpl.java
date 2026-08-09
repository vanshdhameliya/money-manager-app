package tech.logicforge.moneymanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.logicforge.moneymanager.dto.ProfileDto;
import tech.logicforge.moneymanager.entity.ProfileEntity;
import tech.logicforge.moneymanager.mapper.ProfileMapper;
import tech.logicforge.moneymanager.repository.ProfileRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements IProfileService {

    private final ProfileRepository profileRepository;
    private final IEmailService emailService;
    private final ProfileMapper profileMapper;

    public ProfileDto registerProfile(ProfileDto profileDto) {

        ProfileEntity newProfile = profileMapper.toEntity(profileDto);

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
}
