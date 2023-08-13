package com.theZ.dotoring.app.mento.service;

import com.theZ.dotoring.app.desiredField.model.DesiredField;
import com.theZ.dotoring.app.mento.dto.MentoNicknameRequestDTO;
import com.theZ.dotoring.app.memberMajor.model.MemberMajor;
import com.theZ.dotoring.app.mento.dto.MentoSignupRequestDTO;
import com.theZ.dotoring.app.mento.model.Mento;
import com.theZ.dotoring.app.mento.repository.MentoRepository;
import com.theZ.dotoring.app.profile.model.Profile;
import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import com.theZ.dotoring.common.MessageCode;
import com.theZ.dotoring.exception.NicknameDuplicateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MentoService {

    private final MentoRepository mentoRepository;

    public void saveMento(MentoSignupRequestDTO mentoSignupRequestDTO, MemberAccount memberAccount, Profile profile, List<DesiredField> desiredFields, List<MemberMajor> memberMajors){
        Mento mento = Mento.createMento(mentoSignupRequestDTO.getNickname(), mentoSignupRequestDTO.getIntroduction(), mentoSignupRequestDTO.getSchool(), mentoSignupRequestDTO.getGrade(), memberAccount,profile,desiredFields,memberMajors);
        mentoRepository.save(mento);
    }

    public void validateNickname(MentoNicknameRequestDTO mentoNicknameRequestDTO) {
        mentoRepository.findAll().stream().forEach(i ->{
            if(i.getNickname().equals(mentoNicknameRequestDTO.getNickname())){
                throw new NicknameDuplicateException(MessageCode.DUPLICATED_NICKNAME);
            }
        });
    }

}