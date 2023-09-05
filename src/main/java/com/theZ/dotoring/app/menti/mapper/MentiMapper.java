package com.theZ.dotoring.app.menti.mapper;

import com.theZ.dotoring.app.menti.dto.FindAllMentiRespDTO;
import com.theZ.dotoring.app.menti.dto.FindMentiByIdRespDTO;
import com.theZ.dotoring.app.menti.model.Menti;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MentiMapper {

    public static FindAllMentiRespDTO fromCard(Menti menti){

        return FindAllMentiRespDTO.builder()
                .id(menti.getMentiId())
                .nickname(menti.getNickname())
                .preferredMentoringSystem(menti.getPreferredMentoring())
                .introduction(menti.getIntroduction())
                .profileImage(menti.getProfile().getSavedProfileName())
                .majors(menti.getMemberMajors().stream().map(m -> m.getMajor().getMajorName()).collect(Collectors.toList()))
                .fields(menti.getDesiredFields().stream().map(desiredField -> desiredField.getField().getFieldName()).collect(Collectors.toList()))
                .build();
    }

    public static FindMentiByIdRespDTO fromDetail(Menti menti){
        FindMentiByIdRespDTO findMentiByIdRespDTO = FindMentiByIdRespDTO.builder()
                .mentiId(menti.getMentiId())
                .nickname(menti.getNickname())
                .preferredMentoring(menti.getPreferredMentoring())
                .introduction(menti.getIntroduction())
                .profileImage(menti.getProfile().getSavedProfileName())
                .majors(menti.getMemberMajors().stream().map(m -> m.getMajor().getMajorName()).collect(Collectors.toList()))
                .fields(menti.getDesiredFields().stream().map(desiredField -> desiredField.getField().getFieldName()).collect(Collectors.toList()))
                .build();

        return findMentiByIdRespDTO;
    }
    public static List<FindAllMentiRespDTO> from(List<Menti> mentis){

        List<FindAllMentiRespDTO> findAllMentiRespDTOS = new ArrayList<>();

        IntStream.range(0, mentis.size())
                .forEach(i -> findAllMentiRespDTOS.add(
                        FindAllMentiRespDTO.builder()
                                .id(mentis.get(i).getMentiId())
                                .nickname(mentis.get(i).getNickname())
                                .preferredMentoringSystem(mentis.get(i).getPreferredMentoring())
                                .introduction(mentis.get(i).getIntroduction())
                                .profileImage(mentis.get(i).getProfile().getSavedProfileName())
                                .majors(mentis.get(i).getMemberMajors().stream().map(m -> m.getMajor().getMajorName()).collect(Collectors.toList()))
                                .fields(mentis.get(i).getDesiredFields().stream().map(desiredField -> desiredField.getField().getFieldName()).collect(Collectors.toList()))
                                .build())
                );
        return findAllMentiRespDTOS;
    }

}