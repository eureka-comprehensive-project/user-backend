package com.comprehensive.eureka.user;

import com.comprehensive.eureka.user.dto.base.BaseResponseDto;
import com.comprehensive.eureka.user.dto.request.CreateUserDataRecordRequestDto;
import com.comprehensive.eureka.user.dto.request.CreateUserRequestDto;
import com.comprehensive.eureka.user.dto.request.UserDataRecordRequestDto;
import com.comprehensive.eureka.user.dto.response.CreateUserResponseDto;
import com.comprehensive.eureka.user.dto.response.UserDataRecordResponseDto;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserDataRecordIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("사용자 데이터 기록 등록 및 사용량 조회")
    void createUserDataRecord_thenRetrieveUsage() throws Exception {
        // 사용자 등록 및 활성화
        CreateUserRequestDto userDto = CreateUserRequestDto.builder()
                .email("record@example.com")
                .name("기록유저")
                .password("pass1234")
                .birthday(java.time.LocalDate.of(1990, 1, 1))
                .phone("010-1234-5678")
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

        // 데이터 기록 등록
        CreateUserDataRecordRequestDto recordDto = new CreateUserDataRecordRequestDto();
        recordDto.setUserId(userId);
        recordDto.setDataUsage(200);
        recordDto.setDataUsageUnit("MB");
        recordDto.setMessageUsage(50);
        recordDto.setCallUsage(30);
        recordDto.setYearMonth("2025-06");

        mockMvc.perform(post("/user/user-data-record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recordDto)))
                .andExpect(status().isOk());

        // 사용자 사용량 조회
        UserDataRecordRequestDto usageRequest = new UserDataRecordRequestDto();
        usageRequest.setUserId(userId);
        usageRequest.setMonthCount(1);

        MvcResult usageResult = mockMvc.perform(post("/user/user-data-record/usage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usageRequest)))
                .andExpect(status().isOk())
                .andReturn();

        BaseResponseDto<List<UserDataRecordResponseDto>> usageResponse = objectMapper.readValue(
                usageResult.getResponse().getContentAsString(), new TypeReference<>() {}
        );

        assertThat(usageResponse.getData()).hasSize(1);
        assertThat(usageResponse.getData().get(0).getDataUsage()).isEqualTo(200);
    }

    @Test
    @DisplayName("중복된 사용자 데이터 기록 등록 시 예외 발생")
    void createUserDataRecord_duplicateYearMonth_throwsConflict() throws Exception {
        // 사용자 등록
        CreateUserRequestDto userDto = CreateUserRequestDto.builder()
                .email("duprecord@example.com")
                .name("기록중복유저")
                .password("pass1234")
                .birthday(java.time.LocalDate.of(1990, 1, 1))
                .phone("010-1234-5678")
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

        // 동일한 연월로 두 번 등록
        CreateUserDataRecordRequestDto recordDto = new CreateUserDataRecordRequestDto();
        recordDto.setUserId(userId);
        recordDto.setDataUsage(100);
        recordDto.setDataUsageUnit("MB");
        recordDto.setMessageUsage(20);
        recordDto.setCallUsage(10);
        recordDto.setYearMonth("2025-06");

        mockMvc.perform(post("/user/user-data-record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recordDto)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/user/user-data-record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recordDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(50004))
                .andExpect(jsonPath("$.data.detailMessage").value("이미 해당 월에 사용자 기록이 존재합니다."));
    }
}
