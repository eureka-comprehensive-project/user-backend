# 📌 요기 U+
요금제를 기가막히게 추천해줘 U+

# 🧾 사용자 모듈 소개  
사용자 개인정보, 가입 요금제, 통신 사용량을 관리합니다.

금칙어 정책 위반으로 차단된 사용자는 지정된 해제 시간이 되면 스케줄러에 의해 자동으로 차단이 해제됩니다.

# 🛠 사용 기술  
| 언어 | Java 17 |
| --- | --- |
| 프레임워크 | Spring Boot 3.5.0 |
| ORM | Spring Data JPA (Hibernate) |
| DB | MySQL, Redis |
| 유틸 | Lombok |
| 테스트 | Spring Boot Test, JUnit |

# 🎯 사용자 모듈의 엔티티 구조 
- **User**: 사용자 정보를 저장하는 엔티티
- **UserPlanRecord**: 사용자의 요금제 가입 기록을 저장하는 엔티티
- **UserDataRecord**: 사용자의 통신 사용량 데이터 기록을 저장하는 엔티티

# :clipboard:  API 엔드포인트
|메서드|경로|설명|
|------|---|---|
|POST|/user/|사용자 등록|
|POST|/user/email|사용자 정보 조회(인증 모듈)|
|POST|/user/email-check|회원 가입 시 이메일 중복 체크|
|PUT|/user/status-active|이메일 인증 후 사용자 상태 활성화|
|POST|/user/profile|이름, 전화번호 조회|
|POST|/user/profileDetail|사용자 정보 상세 조회|
|GET|/user/{userId}/birthday|생년월일 반환|
|PUT|/user/{userId}/status|사용자 상태 변경(강제 차단)|
|PUT|/user/status|사용자 상태, 차단 해제 시간 변경|
|POST|/user/search|이름이나 이메일로 사용자 검색|
|POST|/user/user-data-record|사용자의 통신 사용량 등록|
|POST|/user/user-data-record/usage|사용자의 통신 사용량 조회|
|POST|/user/user-plan-record|요금제 가입, 변경|
|POST|/user/user-plan-record/valid-contract|이용 중인 요금제 조회|

