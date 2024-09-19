import re
import pandas as pd
from collections import Counter
import matplotlib.pyplot as plt
import seaborn as sns

# 한글 글꼴 설정
plt.rcParams['font.family'] = 'Malgun Gothic'

# 채팅 기록 파일 경로
file_paths = ['KakaoTalk_1.txt', 'KakaoTalk_2.txt', 'KakaoTalk_3.txt', 'KakaoTalk_4.txt']

# 본인의 이름 (채팅에서 본인 이름)
my_name = '은광'  # 본인의 채팅 이름으로 변경하세요

# 채팅 데이터 읽기 함수
def read_chat_file(file_path):
    with open(file_path, 'r', encoding='utf-8') as file:
        return file.readlines()

# 채팅 데이터 파싱 함수
def parse_chat_line(line):
    # 날짜 구분라인 확인
    date_pattern = r'^\d{4}년 \d{1,2}월 \d{1,2}일 .+요일$'
    date_match = re.match(date_pattern, line)
    if date_match:
        # 날짜 부분을 반환하고 날짜와 시간 변수를 초기화
        date_str = line.strip()
        return date_str, None, None

    # 메시지 구분라인 확인
    message_pattern = r'\[(.*?)\] \[(.*?)\] (.+)'
    message_match = re.match(message_pattern, line)
    if message_match:
        user, time, message = message_match.groups()
        # 날짜와 시간을 포함한 전체 타임스탬프 생성
        timestamp = f"{current_date} {time}"
        return timestamp, user, message

    return None

# 모든 채팅 파일에서 데이터 읽기 및 파싱
all_parsed_chat = []
current_date = None

for file_path in file_paths:
    chat_data = read_chat_file(file_path)
    for line in chat_data:
        parsed_line = parse_chat_line(line)
        if parsed_line:
            if parsed_line[1] is None:
                # 날짜 구분라인 처리
                current_date = parsed_line[0]
            else:
                # 메시지 데이터 처리
                all_parsed_chat.append(parsed_line)

# 데이터프레임 생성
df = pd.DataFrame(all_parsed_chat, columns=['Timestamp', 'User', 'Message'])

# 'Hour' 열 추가
def extract_hour(timestamp):
    try:
        # 시간대 추출을 위해 '오전', '오후'와 12시간제 시간을 처리
        time_part = timestamp.split(' ')[-1]  # "오전 12:29" 같은 부분 추출
        period = 'AM' if '오전' in time_part else 'PM'
        time_str = time_part.replace('오전', '').replace('오후', '').strip()
        dt = pd.to_datetime(time_str, format='%I:%M')
        hour = dt.hour
        if period == 'PM' and hour != 12:
            hour += 12
        if period == 'AM' and hour == 12:
            hour = 0
        return hour
    except:
        return None

df['Hour'] = df['Timestamp'].apply(extract_hour)

# 본인의 채팅만 추출
my_chat_df = df[df['User'] == my_name].copy()
my_chat_df['Hour'] = my_chat_df['Timestamp'].apply(extract_hour)  # 본인의 데이터프레임에도 'Hour' 열 추가

# 단어 추출 및 빈도수 계산
def get_most_common_words(messages):
    words = []
    for message in messages:
        # 소문자 변환 및 특수문자 제거
        cleaned_message = re.sub(r'[^\w\s]', '', message.lower())
        words.extend(cleaned_message.split())

    # 단어 빈도수 계산
    word_counts = Counter(words)
    return word_counts

# 본인의 메시지에서 단어 추출 및 빈도수 계산
word_counts = get_most_common_words(my_chat_df['Message'])

# 가장 많이 사용된 단어를 데이터프레임으로 변환
word_df = pd.DataFrame(word_counts.items(), columns=['Word', 'Count'])
word_df = word_df.sort_values(by='Count', ascending=False)

# 엑셀 파일로 저장 (본인 채팅 데이터 + 단어 빈도수)
with pd.ExcelWriter('my_chat_analysis.xlsx', engine='openpyxl') as writer:
    my_chat_df.to_excel(writer, sheet_name='My Chat Data', index=False)
    word_df.to_excel(writer, sheet_name='Word Frequency', index=False)

print("엑셀 파일로 저장 완료: my_chat_analysis.xlsx")

# 단어 빈도수 바 차트 시각화
plt.figure(figsize=(12, 8))
sns.barplot(data=word_df.head(20), x='Count', y='Word', palette='viridis')
plt.title(f'{my_name}의 Top 20 Most Common Words')
plt.xlabel('Count')
plt.ylabel('Word')

# 막대 위에 빈도 수와 단어 표시
for index, value in enumerate(word_df.head(20)['Count']):
    plt.text(value, index, f' {value}', va='center', ha='left', color='black', fontweight='bold')

plt.tight_layout()
plt.savefig('my_word_frequency_plot.png')
plt.show()

print("단어 빈도수 그래프 저장 완료: my_word_frequency_plot.png")

# 시간대별 메시지 수 시각화 (2시간 단위)
def get_time_range(hour):
    # 2시간 단위로 범위 반환
    start_hour = (hour // 2) * 2
    end_hour = start_hour + 2
    if end_hour == 24:
        end_hour = 24
    return f"{start_hour:02d}~{end_hour:02d}시"

# 2시간 단위로 그룹화
my_chat_df['Time Range'] = my_chat_df['Hour'].apply(get_time_range)
hourly_message_counts = my_chat_df['Time Range'].value_counts().reindex(sorted(my_chat_df['Time Range'].unique()), fill_value=0).sort_index()

# 시간대별 메시지 수를 데이터프레임으로 변환
hourly_df = pd.DataFrame(hourly_message_counts).reset_index()
hourly_df.columns = ['Time Range', 'Message Count']

# 시간대별 메시지 수 시각화
plt.figure(figsize=(12, 8))
sns.barplot(data=hourly_df, x='Time Range', y='Message Count', palette='coolwarm')
plt.title(f'{my_name}의 2시간 단위 메시지 수')
plt.xlabel('Time Range')
plt.ylabel('Message Count')

# 막대 위에 메시지 수 표시
for index, value in enumerate(hourly_df['Message Count']):
    plt.text(index, value, f' {value}', va='bottom', ha='center', color='black', fontweight='bold')

plt.tight_layout()
plt.savefig('two_hourly_message_count_plot.png')
plt.show()

print("2시간 단위 메시지 수 그래프 저장 완료: two_hourly_message_count_plot.png")
