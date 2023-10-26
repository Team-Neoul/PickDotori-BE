package com.theZ.dotoring.app.menti.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theZ.dotoring.app.desiredField.repository.QueryDesiredFieldRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("local")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class MentiControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private QueryDesiredFieldRepository queryDesiredFieldRepository;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("해당 멘토의 전공은 소프트웨어공학과, 수학교육과이고, 관심 분야는 진로, 개발_언어이다..")
    @WithUserDetails(value = "sonny12385")
    void findMentiById() throws Exception {

        ResultActions resultActions = mockMvc.perform(
                get("/api/menti")
                        .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("findAllMentoBySlice_test : " + responseBody);

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.success").value(true));
        resultActions.andExpect(jsonPath("$.response").exists());
    }

    @Test
    @WithUserDetails(value = "sonny12385")
    void findAllMentiBySlice() throws Exception {

        String mentiId = "1";

        ResultActions resultActions = mockMvc.perform(
                get("/api/menti/" + mentiId)
                        .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("findMentoById_test : " + responseBody);

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.success").value(true));
        resultActions.andExpect(jsonPath("$.response").exists());
    }
}