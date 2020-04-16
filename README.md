# Google Rss News Viewer :newspaper:

![imge](https://img.shields.io/badge/ProjectType-SingleStudy-green) ![imge](https://img.shields.io/badge/Language-Kotlin-yellow) ![imge](https://img.shields.io/badge/Tools-AndroidStudio-blue)

## 프로그램 소개

- 구글에서 RSS로 제공되는 뉴스목록을 볼 수 있습니다.
- 뉴스 리스트에서 썸네일, 제목, 본문, 주요키워드 3개를 미리 볼 수 있습니다.
- 뉴스 리스트에서 클릭을하면 바로 뉴스 사이트로 연결됩니다.

## 주요 기술 요소

#### MVP 패턴 적용

- Model 과 View의 완전한 분리를 위해 MVP 패턴 사용.

#### Glide 사용

- 이미지 다운로드 라이브러리중 Glide를 사용.

#### Coroutine 사용

- 비동기 통신을 위해 Coroutine을 사용.

#### Jsoup 사용

- http Parsing을 위해 Jsoup을 활용.

## 클래스 구조

![class](https://user-images.githubusercontent.com/37828448/78210167-49569d00-74e4-11ea-91c4-dee09541d3cf.png)

Model 의 역할을 ListRecyclerAdapter에게 주어 처리하였습니다.

## RSS 파싱

- 구글 뉴스 ([https://](https://news.google.com/rss)[news.google.com/rss](https://news.google.com/rss)) 에서 item 별로 파싱하여 link를 들고 온 후, Jsoup을 통해 link에서 Html OpenGraph에 나와있는 썸네일과 본문 내용을 파싱하였습니다.

- 통신은 비동기 통신을 위해 Coroutine을 사용하였습니다.

## 이미지 로드

- 뉴스의 썸네일 이미지를 불러오는 라이브러리로 Glide를 채택하였습니다.

- Glide를 선택한 이유는, 이미지를 불러오는 라이브러리인 Picasso 와 Coil에 비교해 퍼포먼스가 매우 빠르기 때문에 선택했습니다. 

- Coil이  위해 나와 경량이긴 하지만앱 자체가 현재 무겁게 돌아가는 것이 없고빠르게 뉴스이미지를 보여주기 위해 가장 빠른 를 선택하였습니다

# Ver 1.0.0 의 보완해야할 점 

- MVP 패턴 적용시 Presenter와 Fragment에 직접적으로 접근, View에 대한 인터페이스 제공 안함.
- ?. 를 사용하지 않고 null 처리에 !!를 주로 사용함.
- 뉴스 데이터 가져올시 비동기 처리하였지만, 결국엔 다 Load 된 후에 보여짐.
- Linear Layout을 사용함.