package com.theZ.dotoring.app.menti.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FindMyMentiRespDTO {

    private Long mentiId;
    private String profileImage;
    private String nickname;
    private List<String> fields;
    private List<String> majors;
    private String introduction;
    private Long grade;

    @Builder
    public FindMyMentiRespDTO(Long mentiId, String profileImage, String nickname, List<String> fields, List<String> majors, String introduction, Long grade) {
        this.mentiId = mentiId;
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.fields = fields;
        this.majors = majors;
        this.introduction = introduction;
        this.grade = grade;
    }
}