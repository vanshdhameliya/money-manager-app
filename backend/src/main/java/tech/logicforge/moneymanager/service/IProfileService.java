package tech.logicforge.moneymanager.service;

import tech.logicforge.moneymanager.dto.ProfileDto;

public interface IProfileService {

    ProfileDto registerProfile(ProfileDto profileDto);

    boolean activationToken(String activationToken);



}
