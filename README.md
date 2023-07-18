# site
for kb

# 장소 검색 서비스 
1. 사용언어 및 버전 : Java 11
2. 프레임워크 : Spring Boot 2.7.14(SNAPSHOT)
3. 빌드 관리 도구 : Gradle
4. 데이터베이스 : AWS Aurora(MySQL)
5. 외부 라이브러리 및 오픈소스
- spring-boot-starter-data-jpa
- mysql-connector-java
- 사용 이유 : jpa를 통한 mysql 데이터베이스 연동을 위해 사용. 가급적 Redis를 사용하여 간단하게 만들려고 하였으나, 테스트 환경을 전혀 알 수 없기에 로컬 기준으로 가정하여 테스트가 원활히 진행될 수 있도록 MySQL을 활용함.
6. 구현한 API 테스트 방법
- 장소 검색 API 검색어 : 카카오뱅크
  - curl --location 'localhost:8080/v1/place?query=%EC%B9%B4%EC%B9%B4%EC%98%A4%EB%B1%85%ED%81%AC'
- 검색 키워드 목록 : 
  - curl --location 'localhost:8080/v1/keyword'
7. 고려사항
- 검색시 별도의 policy 메소드를 통해 유지보수가 용이하도록 설계
- interface 를 최대한 활용하여 기능 추가/변경시 유연하게 대처할 수 있도록 설계
- 조회한 kakao,naver에서 장애가 발생할 경우, 데이터가 오염되거나 신뢰할 수 없다고 판단하여 빈값으로 return 하도록 구현.(최소한 오류가 발생하지는 않도록 구현)
- ORM(jpa) 을 활용한 생산성 향상, 유지보수 용이, 가독성
- Dto와 Entity를 분리하여 유지보수 용이
- Mapper Class를 활용
- 개발환경에 따른 properties 추가(비 설정시 application.properties 설정대로 동작)


### *참고 : Postman을 통한 테스트 시 아래 코드를 import하면 편하게 테스트가 가능합니다.
```
{
	"info": {
		"_postman_id": "ac4312f6-2d37-45b7-8e5f-866bbb1f3112",
		"name": "New Collection",
		"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json",
		"_exporter_id": "28451484",
		"_collection_link": "https://galactic-water-504037.postman.co/workspace/My-Workspace~075960e0-c73e-46e6-8a2b-583dc4829cc3/collection/28451484-ac4312f6-2d37-45b7-8e5f-866bbb1f3112?action=share&creator=28451484&source=collection_link"
	},
	"item": [
		{
			"name": "getPlace",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "localhost:8080/v1/place?query=카카오뱅크",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"v1",
						"place"
					],
					"query": [
						{
							"key": "query",
							"value": "카카오뱅크"
						}
					]
				}
			},
			"response": []
		},
		{
			"name": "카카오",
			"request": {
				"auth": {
					"type": "apikey",
					"apikey": [
						{
							"key": "value",
							"value": "KakaoAK 867a6d2fa95457060b418b78c844cb2a",
							"type": "string"
						},
						{
							"key": "key",
							"value": "Authorization",
							"type": "string"
						}
					]
				},
				"method": "GET",
				"header": [],
				"url": {
					"raw": "https://dapi.kakao.com/v2/local/search/keyword.json?query=bank&page=1&size=15",
					"protocol": "https",
					"host": [
						"dapi",
						"kakao",
						"com"
					],
					"path": [
						"v2",
						"local",
						"search",
						"keyword.json"
					],
					"query": [
						{
							"key": "query",
							"value": "bank"
						},
						{
							"key": "page",
							"value": "1"
						},
						{
							"key": "size",
							"value": "15"
						}
					]
				}
			},
			"response": []
		},
		{
			"name": "네이버",
			"request": {
				"method": "GET",
				"header": [
					{
						"key": "X-Naver-Client-Id",
						"value": "wrr1prHSayaBpg5iJhmM",
						"type": "text"
					},
					{
						"key": "X-Naver-Client-Secret",
						"value": "SrRI2s9YsC",
						"type": "text"
					}
				],
				"url": {
					"raw": "https://openapi.naver.com/v1/search/local.json?query=bank&display=5",
					"protocol": "https",
					"host": [
						"openapi",
						"naver",
						"com"
					],
					"path": [
						"v1",
						"search",
						"local.json"
					],
					"query": [
						{
							"key": "query",
							"value": "bank"
						},
						{
							"key": "display",
							"value": "5"
						}
					]
				}
			},
			"response": []
		},
		{
			"name": "keyword",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "localhost:8080/v1/keyword",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"v1",
						"keyword"
					]
				}
			},
			"response": []
		}
	]
}
```
