--> 서공 프로젝트 개발 노트 <--

# % 0주차
## 웹 프로젝트 기획 및 구성
- 프로젝트 생성

# % 1주차
## 회원(이용자) 관리 시스템 구축
(1주차 작업 커밋)
- HomeController 구현 및 "/home" 경로로 접속시 "Hello Sogong!" 출력
- Member 클래스 구현 및 Member 인스턴스 생성마다 ID가 자동으로 1씩 증가
- 타임리프 적용
- MemberController 구현
- "/member/join" 경로로 접속시 회원가입 폼 출력
- 회원가입 기능 구현
- 스프링 시큐리티 적용
- SecurityConfig 구현
- MemberService 구현 및 UserDetailsService를 상속하여 loadUserByUsername 오버라이딩
- MemberRepository 구현 및 회원을 username으로 찾는 매소드 구현
- "/member/login" 경로로 접속시 로그인 폼 출력
- 스프링 시큐리티가 대신 로그인을 처리
- 로그인시 마이페이지로 리다이렉트
- "/member/myPage" 경로로 접속시 로그인한 회원 정보(비밀번호 포함) 출력

# % 2주차
## 회원(이용자) 관리 시스템 구축
(파일 수정 커밋)
- member, home 파일을 가지고 있는 파일의 이름 변경 (local -> domain)

(entity 수정 커밋)
- JPA 적용
- Member 클래스에서 id가 자동으로 1씩 증가하도록 수정
- Member 클래스에서 name을 username으로 변경한 뒤 겹치지 않도록 수정

(entity 관련 수정 커밋)
- Member 클래스를 빌더를 이용해서 생성

(DB 연결 커밋)
- mariaDB 적용
- Member 클래스에서 id의 자료형 변경 (String -> int)
- application.yaml 파일에 JPA 및 DB 정보 입력

(mvc 분리 및 DB 저장 불러오기 구현 커밋)
- MemberDto 클래스 구현 및 적용
- MemberRepository를 interface로 변경한 뒤 JpaRepository 상속
- MemberRepository에 findByUsername 매소드 구현 및 적용
- MemberService에 회원 정보를 DB에 저장하고 불러오는 createMember 매소드 구현

(로그아웃 구현 커밋)
- "/member/logout" 경로로 접속시 로그아웃 폼 출력
- 스프링 시큐리티가 대신 로그아웃을 처리

(테스트 회원 생성 커밋)
- InitData 클래스 구현
- profile을 이용해 test 모드일 때만 테스트 데이터 생성
- application.yaml 파일을 일반, 개발, 테스트, 운영 모드 네가지로 분리

(테스트 데이터 문제 해결 커밋)
- 테스트 데이터의 비밀번호가 이중 암호화되는 문제 해결
- SecurityConfig 클래스에서 다양한 상황에 대한 대처 능력 강화

(MemberDto 관련 수정 커밋)
- MemberDto 생성자 매소드 추가
- createMember 매소드 수정

(DB 분리 커밋)
- h2 DB를 적용
- dev, test 모드일 때는 h2를 이용하고 prod 모드일 때는 mariaDB 이용
- dev, prod 모드는 데이터 영구 저장, test 모드는 임시 저장

(마이페이지 수정 커밋)
- SecurityService 클래스 생성 및 MemberService에 있던 loadUserByUsername 매소드를 가져옴
- LoginedMember 클래스 생성
- LoginedMember 클래스를 이용해 로그인한 회원의 정보를 저장함
- "/member/myPage" 경로로 접속시 비밀번호가 노출되는 문제 해결

# % 3주차
## 회원(이용자) 관리 시스템 구축
## 게시판 관리 시스템 구축

(기본 페이지 생성 커밋)
- index.html 생성
- 기본 경로를 "/home" 에서  "/"로 변경

(카카오톡 로그인 구현 커밋)
- OAuth2 적용
- application.yaml에 OAuth2 관련 설정
- application-secret.yaml에 REST API 키 설정 (커밋에는 안 올라감)
- SecurityConfig에 OAuth2 기능 구현
- OAuth2UserService 클래스에 loadUser 매소드 구현
- LoginedMember에 OAuth2User를 상속하여 카카오 계정 정보를 로그인 정보에 포함
- OAuth 타입 불일치와 member를 찾을 수 없을 때 사용할 exception 클래스 생성
- 로그인 페이지에 카카오 로그인 링크 추가
- 카카오 로그인시에 처음이면 자동으로 회원가입 후 로그인 기능 구현

(전체적인 페이지 구성 커밋)
- logout.html 페이지 삭제
- myPage.html 페이지 생성
- "/member/myPage" 경로로 접속시 myPage.html 페이지로 전송
- 공통 css 파일 생성
- 타임리프 레이아웃 적용
- layout.html 생성
- 레이아웃을 헤더, 메인, 푸터로 분리
- 헤더에 홈, 회원가입, 로그인, 마이페이지, 로그아웃 바로가기 기능 추가
- 페이지들이 레이아웃이 적용되도록 수정
- 로그인시에만 로그아웃 버튼이 나오도록 수정

(오류 수정 커밋)
- 깃허브에 올라간 application-secret.yaml 파일 삭제
- .gitignore 수정

(BoardClass 생성 커밋)
- BoardClass 엔티티 생성
- BoardClassService 클래스 생성
- BoardClassRepository 클래스 생성
- InitBoardClass 클래스를 생성하여 프로그램 시작시 자동으로 데이터 생성 및 저장

(MemberDto 제거 커밋)
- MemberDto 클래스 제거
- MemberDto 자리에 Member로 변환

(보드 클래스 종류 추가 커밋)
- InitBoardClass에서 공지사항 게시판의 종류를 표현할 보드 클래스를 추가
  
(Board 생성 커밋)
- Board 엔티티 생성
- BoardService 클래스 생성
- BoardRepository 클래스 생성
- BoardController 클래스 생성
- 게시판 생성 페이지 생성
- "/board/create" 경로로 접속시 게시판 생성 페이지로 전송
- 게시판 생성 기능 구현
- 게시판 상세보기 페이지 생성
- 게시판 생성시 상세보기 페이지로 전송

(게시판 바로가기 수정 커밋)
- 로그인을 했을 때만 게시판 생성 링크가 생기게 수정

(Board 수정 커밋)
- Board 엔티티에서 boardClassId 속성 대신 boardClass 속성으로 변경
- boardClass 속성에 ManyToOne 태그를 붙여 DB에는 boardClassId가 외래키로써 저장
- BoardForm 클래스 생성
- BoardClassService에 BoardClass를 찾는 기능 추가
- BoardService 수정
- BoardController 수정
- 게시판 상세보기 페이지 수정

(Article 엔티티 생성 커밋)
- Article 엔티티 생성
- ArticleController 생성

(boardClass 패키지 분리 커밋)
- boardClass 패키지를 board 패키지로부터 분리

(페이지 수정 커밋)
- 마이페이지에서 비밀번호 자체가 표시되지 않게 함
- 로그인 폼에서 전송 버튼의 글자를 제출에서 로그인으로 변경
- 버튼 디자인 변경
- 로그인을 하지 않을 때는 회원가입, 로그인 버튼 생성, 로그인을 했을 때는 로그아웃, 마이페이지, 게시판_생성 버튼 생성

(회원가입 유효성 검사 커밋)
- valid 적용
- MemberForm 클래스 생성
- 회원가입 폼에서 (이름 -> 아이디) 변경
- valid를 이용하여 백엔드에서 아이디나 비밀번호 둘 중 하나라도 입력을 안 하고 제출할 시 다시 회원가입 폼으로 돌려보내기
- JS를 이용하여 프론트엔드에서 아이디나 비밀번호에 공백이 있을 시 공백 제거 후 값이 입력됐는지 검사 후 폼 제출

(회원가입 유효성 검사2 커밋)
- 아이디(username) 중복 검사 추가

(회원가입 폼 수정 커밋)
- 비밀번호 확인 추가
- MemberForm 수정
- 디자인 추가

(게시판 생성 폼 수정 커밋)
- JS를 이용하여 프론트엔드에서 게시판 이름이나 종류에 공백이 있을 시 공백 제거 후 값이 입력됐는지 검사 후 폼 제출
- 디자인 추가