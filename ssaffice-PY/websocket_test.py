import websocket
import json
from mmapi import login_to_mattermost
from setup import config

mm_websocket_url = config.MM_WEBSOCKETURL

# 웹소켓 이벤트 핸들러 함수
def on_message(ws, message):
    data = json.loads(message)
    # event중에 글이 게시되고 해당 게시글이 올라온 채널의 이름이 공지를 포함할 때만 출력
    if data['event'] == 'posted' and 'test' in data['data']['channel_display_name'] :
        print("서버로부터 메시지 수신:", data)        

def on_error(ws, error):
    print("웹소켓 오류:", error)


def on_close(ws, close_status_code, close_msg):
    print("웹소켓 연결이 닫혔습니다.")


def on_open(ws):
    token = login_to_mattermost()
    print("웹소켓 연결이 열렸습니다.")

    # Mattermost 인증 토큰 전송
    ws.send(json.dumps({
        "seq": 1,
        "action": "authentication_challenge",
        "data": {"token": token}
    }))


# 웹소켓 연결 설정
websocket_url = mm_websocket_url
ws = websocket.WebSocketApp(websocket_url,
                            on_open=on_open,
                            on_message=on_message,
                            on_error=on_error,
                            on_close=on_close)

# 웹소켓 연결 시작
ws.run_forever()
