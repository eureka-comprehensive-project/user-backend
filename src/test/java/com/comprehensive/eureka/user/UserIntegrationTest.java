package com.comprehensive.eureka.user;

import com.comprehensive.eureka.user.dto.base.BaseResponseDto;
import com.comprehensive.eureka.user.dto.request.*;
import com.comprehensive.eureka.user.dto.response.CreateUserResponseDto;
import com.comprehensive.eureka.user.entity.enums.Status;
import com.comprehensive.eureka.user.scheduler.UserScheduler;
import com.comprehensive.eureka.user.service.RedisService;
import com.comprehensive.eureka.user.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RedisService redisService;

    @Test
    @DisplayName("사용자 프로필 조회 및 상세 조회")
    void getUserProfile_andDetail_thenSuccess() throws Exception {
        CreateUserRequestDto userDto = CreateUserRequestDto.builder()
                .email("profileuser@example.com")
                .name("프로필유저")
                .password("pass1234")
                .birthday(LocalDate.of(1990, 1, 1))
                .phone("010-1234-5678")
                .build();

        MvcResult result = mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn();

        Long userId = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<BaseResponseDto<CreateUserResponseDto>>() {}).getData().getId();

        GetByIdRequestDto idRequest = new GetByIdRequestDto();
        idRequest.setId(userId);

        mockMvc.perform(post("/user/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(idRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("프로필유저"));

        mockMvc.perform(post("/user/profileDetail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(idRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value("profileuser@example.com"));
    }

    @Test
    @DisplayName("사용자 상태 및 차단 시간 변경")
    void updateUserStatusAndTime_thenSuccess() throws Exception {
        CreateUserRequestDto userDto = CreateUserRequestDto.builder()
                .email("banuser@example.com")
                .name("차단유저")
                .password("pass1234")
                .birthday(LocalDate.of(1995, 1, 1))
                .phone("010-9999-9999")
                .build();

        MvcResult result = mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn();

        Long userId = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<BaseResponseDto<CreateUserResponseDto>>() {}).getData().getId();

        UpdateUserStatusRequestDto updateDto = new UpdateUserStatusRequestDto();
        updateDto.setUserId(userId);
        updateDto.setStatus(Status.INACTIVE);
        updateDto.setUnbanTime(LocalDateTime.now().plusDays(3));

        mockMvc.perform(put("/user/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("사용자 생년월일 조회")
    void getUserBirthday_thenReturnBirthday() throws Exception {
        CreateUserRequestDto userDto = CreateUserRequestDto.builder()
                .email("birthuser@example.com")
                .name("생일유저")
                .password("pass1234")
                .birthday(LocalDate.of(1980, 10, 20))
                .phone("010-7777-8888")
                .build();

        MvcResult result = mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn();

        Long userId = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<BaseResponseDto<CreateUserResponseDto>>() {}).getData().getId();

        mockMvc.perform(get("/user/" + userId + "/birthday"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("1980-10-20"));
    }

    @Test
    @DisplayName("사용자 검색 - 이름 또는 이메일 포함")
    void searchUsers_thenReturnMatchingUsers() throws Exception {
        CreateUserRequestDto userDto = CreateUserRequestDto.builder()
                .email("searchuser@example.com")
                .name("홍길동")
                .password("pass1234")
                .birthday(LocalDate.of(1991, 5, 12))
                .phone("010-5555-6666")
                .build();

        mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk());

        SearchWordRequestDto requestDto = new SearchWordRequestDto();
        requestDto.setSearchWord("홍");

        mockMvc.perform(post("/user/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").value("홍길동"));
    }

    @Test
    @DisplayName("검색어 없이 사용자 검색 시 예외 발생")
    void searchUsers_withoutSearchWord_thenThrowBadRequest() throws Exception {
        SearchWordRequestDto requestDto = new SearchWordRequestDto(); // searchWord = null

        mockMvc.perform(post("/user/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(50005))
                .andExpect(jsonPath("$.data.detailMessage").value("검색어를 입력해 주세요."));
    }


    @Test
    @DisplayName("사용자 상태 변경 요청 성공")
    void updateUserStatus_thenSuccess() throws Exception {
        // 사용자 등록
        CreateUserRequestDto userDto = CreateUserRequestDto.builder()
                .email("statususer@example.com")
                .name("상태유저")
                .password("pass1234")
                .birthday(LocalDate.of(1994, 3, 15))
                .phone("010-1234-1234")
                .build();

        MvcResult result = mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn();

        Long userId = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<BaseResponseDto<CreateUserResponseDto>>() {}).getData().getId();

        // 상태 변경 요청
        mockMvc.perform(put("/user/" + userId + "/status"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("이메일을 통해 OAuth 사용자 조회 성공")
    void getOAuthUserByEmail_thenReturnUserInfo() throws Exception {
        // 1. 사용자 등록
        CreateUserRequestDto userDto = CreateUserRequestDto.builder()
                .email("searchuser@example.com")
                .name("홍길동")
                .password("pass1234")
                .birthday(LocalDate.of(1991, 5, 12))
                .phone("010-5555-6666")
                .build();

        MvcResult result = mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn();

        BaseResponseDto<CreateUserResponseDto> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {}
        );
        Long userId = response.getData().getId();

        // 2. 이메일로 OAuth 사용자 조회 요청
        GetByEmailRequestDto emailDto = new GetByEmailRequestDto();
        emailDto.setEmail("searchuser@example.com");

        mockMvc.perform(post("/user/oauth-email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emailDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value(userId))
                .andExpect(jsonPath("$.data.email").value("searchuser@example.com"))
                .andExpect(jsonPath("$.data.role").value("ROLE_USER"))
                .andExpect(jsonPath("$.data.status").value("INACTIVE"));
    }
}
