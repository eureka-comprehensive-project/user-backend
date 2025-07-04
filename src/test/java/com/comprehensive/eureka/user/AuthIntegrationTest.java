package com.comprehensive.eureka.user;

import com.comprehensive.eureka.user.dto.base.BaseResponseDto;
import com.comprehensive.eureka.user.dto.request.CreateUserRequestDto;
import com.comprehensive.eureka.user.dto.request.GetByEmailRequestDto;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String EMAIL = "testuser@example.com";

    @Test
    @DisplayName("사용자 등록 후 활성화 → 이메일 조회 성공")
    void createUser_thenActivate_thenFindByEmail() throws Exception {
        // 1. 사용자 등록
        CreateUserRequestDto userDto = CreateUserRequestDto.builder()
                .email(EMAIL)
                .name("테스트유저")
                .password("pass1234")
                .birthday(java.time.LocalDate.of(1990, 1, 1))
                .phone("010-1234-5678")
                .build();

        MvcResult result = mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        BaseResponseDto<CreateUserResponseDto> response = objectMapper.readValue(responseBody, new TypeReference<>() {});
        assertThat(response.getData().getId()).isNotNull();

        // 2. 상태 ACTIVE로 변경
        GetByEmailRequestDto emailDto = new GetByEmailRequestDto();
        emailDto.setEmail(EMAIL);

        mockMvc.perform(put("/user/status-active")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emailDto)))
                .andExpect(status().isOk());

        // 3. 이메일 조회
        mockMvc.perform(post("/user/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emailDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value(EMAIL))
                .andExpect(jsonPath("$.data.role").value("ROLE_USER"));
    }

    @Test
    @DisplayName("중복된 이메일로 사용자 등록 시 예외")
    void createUser_duplicateEmail_throwsConflict() throws Exception {
        CreateUserRequestDto userDto = CreateUserRequestDto.builder()
                .email(EMAIL)
                .name("테스트유저")
                .password("pass1234")
                .birthday(java.time.LocalDate.of(1990, 1, 1))
                .phone("010-1234-5678")
                .build();

        mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk());

        // 다시 등록 시도 (중복)
        mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(50001))
                .andExpect(jsonPath("$.data.detailMessage").value("중복된 email 입니다."));
    }

    @Test
    @DisplayName("활성화되지 않은 사용자 조회 시 예외")
    void findUserByEmail_notActivated_returnsNotFound() throws Exception {
        CreateUserRequestDto userDto = CreateUserRequestDto.builder()
                .email("inactive@example.com")
                .name("비활성")
                .password("pass1234")
                .birthday(java.time.LocalDate.of(1990, 1, 1))
                .phone("010-1234-5678")
                .build();

        mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk());

        GetByEmailRequestDto request = new GetByEmailRequestDto();
        request.setEmail("inactive@example.com");

        mockMvc.perform(post("/user/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(50000))
                .andExpect(jsonPath("$.data.detailMessage").value("해당 사용자를 찾을 수 없습니다."));
    }

    @Test
    @DisplayName("존재하지 않는 사용자 상태 활성화 요청 시 예외")
    void updateUserStatusActive_userNotFound_returnsNotFound() throws Exception {
        GetByEmailRequestDto request = new GetByEmailRequestDto();
        request.setEmail("nonexistent@example.com");

        mockMvc.perform(put("/user/status-active")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(50000));
    }

    @Test
    @DisplayName("이메일 중복 검사 - 존재하면 true 반환")
    void emailExists_returnsTrue() throws Exception {
        CreateUserRequestDto userDto = CreateUserRequestDto.builder()
                .email("dup@example.com")
                .name("중복검사")
                .password("pass1234")
                .birthday(java.time.LocalDate.of(1990, 1, 1))
                .phone("010-1234-5678")
                .build();

        mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk());

        GetByEmailRequestDto request = new GetByEmailRequestDto();
        request.setEmail("dup@example.com");

        mockMvc.perform(post("/user/email-check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(true));
    }

    @Test
    @DisplayName("이메일 중복 검사 - 존재하지 않으면 false 반환")
    void emailExists_returnsFalse() throws Exception {
        GetByEmailRequestDto request = new GetByEmailRequestDto();
        request.setEmail("nope@example.com");

        mockMvc.perform(post("/user/email-check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(false));
    }
}

