package com.comprehensive.eureka.user;

import com.comprehensive.eureka.user.dto.base.BaseResponseDto;
import com.comprehensive.eureka.user.dto.request.CreateUserPlanRecordRequestDto;
import com.comprehensive.eureka.user.dto.request.CreateUserRequestDto;
import com.comprehensive.eureka.user.dto.response.CreateUserResponseDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserPlanRecordIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("사용자 요금제 등록 및 유효한 계약 혜택 조회")
    void createUserPlanRecord_thenRetrieveActiveBenefit() throws Exception {
        // 사용자 등록
        CreateUserRequestDto userDto = CreateUserRequestDto.builder()
                .email("planuser@example.com")
                .name("요금제유저")
                .password("pass1234")
                .birthday(LocalDate.of(1990, 1, 1))
                .phone("010-0000-0000")
                .build();

        MvcResult result = mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn();

        BaseResponseDto<CreateUserResponseDto> response = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<>() {}
        );

        Long userId = response.getData().getId();

        // 기존 요금제 등록
        CreateUserPlanRecordRequestDto planDto1 = new CreateUserPlanRecordRequestDto();
        planDto1.setUserId(userId);
        planDto1.setPlanBenefitId(100L);

        mockMvc.perform(post("/user/user-plan-record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(planDto1)))
                .andExpect(status().isOk());

        // 새 요금제 등록 (기존 만료처리됨)
        CreateUserPlanRecordRequestDto planDto2 = new CreateUserPlanRecordRequestDto();
        planDto2.setUserId(userId);
        planDto2.setPlanBenefitId(200L);

        mockMvc.perform(post("/user/user-plan-record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(planDto2)))
                .andExpect(status().isOk());

        // 요금제 혜택 조회 → 최신(200L) 혜택만 응답
        mockMvc.perform(post("/user/user-plan-record/valid-contract")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(userId))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].userId").value(userId))
                .andExpect(jsonPath("$.data[0].planBenefitId").value(200L));
    }
}
