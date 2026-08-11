package tech.logicforge.moneymanager.service;

import tech.logicforge.moneymanager.dto.AuthDto;
import tech.logicforge.moneymanager.dto.ProfileDto;

import java.util.Map;

public interface IProfileService {

    ProfileDto registerProfile(ProfileDto profileDto);

    boolean activationToken(String activationToken);

    boolean isAccountActive(String email);


    Map<String, Object> authenticateAndGenerateToken(AuthDto authDto);
}
