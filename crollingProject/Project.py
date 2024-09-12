import urllib.request
from bs4 import BeautifulSoup

# 헤더 설정 (브라우저에서 접속하는 것처럼 속이기)
headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36',
    'Accept-Language': 'ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7',  # 한국어 페이지로 요청
    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8'
}

url = 'https://www.tripadvisor.co.kr/Restaurants-g297887-Daejeon.html'

# 요청에 헤더 추가
req = urllib.request.Request(url, headers=headers)

try:
    # 응답 받기
    response = urllib.request.urlopen(req)
    html = response.read()

    # BeautifulSoup으로 HTML 파싱
    soup = BeautifulSoup(html, 'html.parser')

    # 식당 이름과 링크 추출
    restaurants = soup.find_all('a', class_='BMQDV _F Gv wSSLS SwZTJ FGwzt ukgoS')

    for restaurant in restaurants:
        name = restaurant.get_text(strip=True)  # 식당 이름
        link = restaurant['href']  # 링크 (상대 경로)
        full_link = 'https://www.tripadvisor.co.kr' + link  # 절대 경로로 변환
        print(f'식당 이름: {name}, 링크: {full_link}')

except urllib.error.HTTPError as e:
    print(f"HTTP error occurred: {e.code} - {e.reason}")
except urllib.error.URLError as e:
    print(f"URL error occurred: {e.reason}")
