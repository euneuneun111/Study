import re
import pandas as pd
from collections import Counter
import matplotlib.pyplot as plt
import seaborn as sns
from nltk import ngrams
from transformers import pipeline

# 한글 글꼴 설정
plt.rcParams['font.family'] = 'Malgun Gothic'

# 채팅 기록 파일 경로
file_paths = ['KakaoTalk_1.txt', 'KakaoTalk_2.txt', 'KakaoTalk_3.txt', 'KakaoTalk_4.txt']

# 이름
my_name = '은광'

# 채팅 데이터 읽기 함수
def read_chat_file(file_path):
    with open(file_path, 'r', encoding='utf-8') as file:
        return file.readlines()

# 채팅 데이터 파싱 함수
def parse_chat_line(line):
    date_pattern = r'^\d{4}년 \d{1,2}월 \d{1,2}일 .+요일$'
    date_match = re.match(date_pattern, line)
    if date_match:
        date_str = line.strip()
        return date_str, None, None

    message_pattern = r'\[(.*?)\] \[(.*?)\] (.+)'
    message_match = re.match(message_pattern, line)
    if message_match:
        user, time, message = message_match.groups()
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
                current_date = parsed_line[0]
            else:
                all_parsed_chat.append(parsed_line)

# 데이터프레임 생성
df = pd.DataFrame(all_parsed_chat, columns=['Timestamp', 'User', 'Message'])

# 'Hour' 열 추가
def extract_hour(timestamp):
    if timestamp is None:
        return None  # None 값이 들어오면 None 반환
    try:

        # '오전' 또는 '오후'를 포함하는 시간 부분 추출
        if '오전' in timestamp:
            period = 'AM'
            time_str = timestamp.split('오전')[-1].strip()  # '10:30' 형식 추출
        elif '오후' in timestamp:
            period = 'PM'
            time_str = timestamp.split('오후')[-1].strip()  # '3:45' 형식 추출
        else:
            return None  # '오전' 또는 '오후'가 없는 경우 처리하지 않음

        # '오전'과 '오후'에 따라 시간 처리
        dt = pd.to_datetime(time_str, format='%I:%M')
        hour = dt.hour
        if period == 'AM' and hour == 12:
            hour = 0  # 오전 12시는 0으로 처리
        elif period == 'PM' and hour != 12:
            hour += 12  # 오후 12시는 그대로 두고, 나머지 시간은 12 더함

        print(f"Timestamp: {timestamp}, Hour: {hour}")
        return hour
    except Exception as e:
        print(f"시간 추출 실패: {timestamp}, 오류: {e}")
        return None

# 'Hour' 열에 대한 디버깅 출력
df['Hour'] = df['Timestamp'].apply(extract_hour)


# 본인의 채팅만 추출
my_chat_df = df[df['User'] == my_name].copy()

# 단어 추출 및 빈도수 계산
def get_most_common_words(messages):
    words = []
    for message in messages:
        cleaned_message = re.sub(r'[^\w\s]', '', message.lower())
        words.extend(cleaned_message.split())
    word_counts = Counter(words)
    return word_counts

word_counts = get_most_common_words(my_chat_df['Message'])
word_df = pd.DataFrame(word_counts.items(), columns=['Word', 'Count'])
word_df = word_df.sort_values(by='Count', ascending=False)

# 엑셀 파일로 저장
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

for index, value in enumerate(word_df.head(20)['Count']):
    plt.text(value, index, f' {value}', va='center', ha='left', color='black', fontweight='bold')

plt.tight_layout()
plt.savefig('my_word_frequency_plot.png')
plt.show()

print("단어 빈도수 그래프 저장 완료: my_word_frequency_plot.png")

# 시간대별 메시지 수 시각화 (2시간 단위)
def get_time_range(hour):
    if hour is None:
        return None  # None 값 처리
    start_hour = (hour // 2) * 2
    end_hour = start_hour + 2
    if end_hour >= 24:
        end_hour = 24
    return f"{start_hour:02d}~{end_hour:02d}시"

my_chat_df['Time Range'] = my_chat_df['Hour'].apply(get_time_range)

hourly_message_counts = my_chat_df['Time Range'].value_counts().reindex(
    [f"{i:02d}~{i+2:02d}시" for i in range(0, 24, 2)],
    fill_value=0
).sort_index()

hourly_df = pd.DataFrame(hourly_message_counts).reset_index()
hourly_df.columns = ['Time Range', 'Message Count']

plt.figure(figsize=(12, 8))
sns.barplot(data=hourly_df, x='Time Range', y='Message Count', palette='coolwarm')
plt.title(f'{my_name}의 2시간 단위 메시지 수')
plt.xlabel('Time Range')
plt.ylabel('Message Count')

for index, value in enumerate(hourly_df['Message Count']):
    plt.text(index, value, f' {value}', va='bottom', ha='center', color='black', fontweight='bold')

plt.tight_layout()
plt.savefig('two_hourly_message_count_plot.png')
plt.show()

print("2시간 단위 메시지 수 그래프 저장 완료: two_hourly_message_count_plot.png")

# N-gram 분석 함수
def generate_ngrams(messages, n):
    words = []
    for message in messages:
        cleaned_message = re.sub(r'[^\w\s]', '', message.lower())
        words.extend(cleaned_message.split())
    n_grams = ngrams(words, n)
    ngram_counts = Counter(n_grams)
    return ngram_counts

# 2-gram, 3-gram 분석
n2_counts = generate_ngrams(my_chat_df['Message'], 2)
n3_counts = generate_ngrams(my_chat_df['Message'], 3)

# N-gram 결과를 데이터프레임으로 변환
n2_df = pd.DataFrame(n2_counts.items(), columns=['N-gram', 'Count']).sort_values(by='Count', ascending=False)
n3_df = pd.DataFrame(n3_counts.items(), columns=['N-gram', 'Count']).sort_values(by='Count', ascending=False)

# N-gram 결과를 엑셀 파일에 추가
with pd.ExcelWriter('my_chat_analysis.xlsx', engine='openpyxl', mode='a') as writer:
    n2_df.to_excel(writer, sheet_name='2-gram Analysis', index=False)
    n3_df.to_excel(writer, sheet_name='3-gram Analysis', index=False)

print("N-gram 분석 결과 엑셀 파일로 저장 완료: my_chat_analysis.xlsx")


# BERT 기반 감정 분석 파이프라인 로드
sentiment_analyzer = pipeline('sentiment-analysis')

# 감정 분석 함수 (transformers 사용)
def analyze_sentiment_bert(text):
    result = sentiment_analyzer(text)
    return result[0]['label']  # 감정 레이블 반환 (POSITIVE/NEGATIVE)

# 메시지 데이터프레임에 감정 분석 적용
df['Sentiment_BERT'] = df['Message'].apply(analyze_sentiment_bert)

# 감정 분석 결과 통계
bert_sentiment_counts = df['Sentiment_BERT'].value_counts()

# 감정 분석 결과 시각화
plt.figure(figsize=(8, 6))
sns.barplot(x=bert_sentiment_counts.index, y=bert_sentiment_counts.values, palette='coolwarm')
plt.title('BERT-based Sentiment Analysis of Chat Messages')
plt.xlabel('Sentiment')
plt.ylabel('Message Count')
plt.tight_layout()
plt.savefig('bert_sentiment_analysis_plot.png')
plt.show()

print("BERT 기반 감정 분석 결과 그래프 저장 완료: bert_sentiment_analysis_plot.png")



# 특정 키워드에 대한 반응 분석
def analyze_keyword_reactions(df, keyword):
    keyword_reactions = df[df['Message'].str.contains(keyword, na=False)]
    return keyword_reactions

# 키워드에 대한 반응 분석 실행
keyword = ('날씨')  # 원하는 키워드로 변경
keyword_reactions_df = analyze_keyword_reactions(my_chat_df, keyword)

# 가장 많이 등장하는 5개의 메시지 추출
top_5_messages = keyword_reactions_df['Message'].value_counts().head(5)

# 결과를 시각화
plt.figure(figsize=(12, 8))
sns.barplot(x=top_5_messages.values, y=top_5_messages.index, palette='coolwarm')
plt.title(f"키워드 '{keyword}'에 대한 상위 5개 메시지")
plt.xlabel('Count')
plt.ylabel('Message')

plt.tight_layout()
plt.savefig(f'keyword_reaction_top5_analysis_{keyword}.png')
plt.show()

print(f"키워드 '{keyword}'에 대한 상위 5개 메시지 분석 결과 PNG로 저장 완료: keyword_reaction_top5_analysis_{keyword}.png")


