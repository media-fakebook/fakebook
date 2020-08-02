# 페이스북 클론 프로젝트 (작성 중)

사용 기술 : 

Spring boot, JPA, MySQL, H2, Vue.js, Jenkins

# 코딩 컨벤션

- Lombok을 사용한다.
- 생성자의 인자 수가 4개 이상인 경우에 빌더패턴을 사용한다.
- 한 메서드의 길이는 10줄을 넘으면 안된다.
- tab 사용
- 테스트코드는 DisplayName 애노테이션을 사용하지 않는다.

```
    @Test
    public void 테스트_코드_이름() {
        ...
    }
```

# 프로젝트 관리

- Github의 Project 탭
    - TODO
    - IN PROGRESS
        - 동시에 1개만 진행한다.
    - REVIEW
        - 2일내에 리뷰하기
        - 리뷰어가 Approved 하면 PR보낸 사람이 Merge한다.
    - DONE
        - Merge가 된 후에 Done으로 이동

# 브랜치 전략

```
git remote add upstream https://github.com/media-fakebook/fakebook.git
git remote add origin https://github.com/{개인 아이디}/fakebook.git
```

### 브랜치 이름

- 마스터 브랜치 : master
- 개발 브랜치 : develop
- 기능 구현 브랜치 : feature/{issueNumber}/{issueName}
    - ex) feature/75/articlePaging



### 새로운 브랜치 생성방법 
```
git fetch upstream develop
git checkout -b feature/75/articlePageing upstream/develop
```



### Commit 이름

- [#{issueNumber}] {commit-type}: {commit-name}
    - ex) [#115] feat: 게시글 페이징 기능구현