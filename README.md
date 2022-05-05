# api_hospotal

COVID-19_Hospital API

1. API 주소 ( json)

2. json 데이터 javaObject 로 변경하기

3. 스프링 어플리케이션 생성

- gradle
- mustache
- jpa
- h2 ( 메모리 디비 )

4. cron 테스트 1분 주기

5. 공공데이터 가져오기

- 가져와서 DTO 로 변환
- url 인코딩 문제
- 가져온 json 데이터 DTO 맵핑 문제

6. jpa 모델만들기

- 테이블 만들어 지는지 테스트

7. 컬렉션에 다 담아보자

- 주소를 10개씩 1페이지 들고오는거니까
- 페이지 끝가지 가져와야함
- 그냥 1000개를 가져오는 걸로 함

8. 디비에 insert 테스트 -h2
   http://localhost:8765/h2-console/login.do

- 성공

9. 삭제 후 insert하기 매일 batch 때마다 무슨 병원이 늘었는지 확인하는 것보다 다시 새로 디비를 업데이트 하는게 빠르니까 그런 방식으로 함

- yml ddl 변경하고 DB 날리고 , 다시 가져와서 다 추가하고

그런데 h2 DB를 사용하다보니 메모리 DB여서 원래 ddl auto 를 update로 사용해야하는데 어차피 서버꺼졌다 키면 다 사라져서 그냥 create로..

10. 토탈 카운드 - 배치 시간 설정하기.. 배치는 매시간 15분에하는 걸로 설정

11. 화면에 뿌려중 view 를 만들자 뷰 설정 완료 - 부트스트랩 이용

12. view 에서 검색할수 있게하자 위치로 검색하게 하자 ( 시도명, 시군구명 )

13. 검색 조건을 리스트로 보여주자 사용자들이 잘 못찾을 듯 검색할수 있는 리스트를 만들자 ajax로 가져와서 select 박스를 사용하자 select distinct sidoNm from hospital;
    시도명을 선택하면 ajax를 사용해서 구 명을 리스트로 보여줌

최초값도 설정 완료

14. 검색 결과도 ajax로 변경

- 검색해도 검색값은 안바뀜