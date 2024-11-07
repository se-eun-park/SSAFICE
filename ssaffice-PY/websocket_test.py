import websocket
import json
from mmapi import *
from setup import config, get_db
from models.models import Notice
from websocket_service import *
from get_datas import *
from set_datas import *

# 웹소켓은 관리자가 전체 채널에서 메시지를 가져오는 용도로 사용한다.
# 지금의 경우에는 '재현'의 계정이 관리자라고 가정한다.

mm_websocket_url = config.MM_WEBSOCKET_URL
websocket_url = mm_websocket_url
token = make_mattermost_admin_token()


# 웹소켓 이벤트 핸들러 함수
def on_message(ws, message):
    data = json.loads(message)
    # event중에 글이 게시되고 해당 게시글이 올라온 채널의 이름이 공지를 포함할 때만 출력
    if is_notice(data):
        # 일정인지 아닌지 분석하는 함수
        output_message = analyze_message(data)
        for notice in output_message["list"]:
            # 일정인 경우에는 일정 등록
            if notice["isTodo"] == "o":
                notice_entity = make_notice_entity(data, notice)
                notice_db_id = insert_notice(notice_entity)

                print("공지 등록 완료, notice_id :", notice_db_id)

                notice_channel = make_notice_channel_entity(data, notice_db_id)
                notice_channel_db_id = insert_notice_channel(notice_channel)

                print(
                    "notice_channel 등록 완료, notice_channel_id :",
                    notice_channel_db_id,
                )

                file_download_if_file_exist(token, data)
            else:
                print("일정이 아닙니다.")

    # event중에 글이 삭제 되면 해당 게시글의 아이디를 출력
    elif data["event"] == "post_deleted":
        json_data = json.loads(data["data"]["post"])
        print("메시지가 삭제됨 :", json_data["id"])


def on_error(ws, error):
    print("웹소켓 오류:", error)


def on_close(ws, close_status_code, close_msg):
    print("웹소켓 연결이 닫혔습니다.")


def on_open(ws):
    token = make_mattermost_admin_token()
    print("웹소켓 연결이 열렸습니다.")

    # Mattermost 인증 토큰 전송
    ws.send(
        json.dumps(
            {"seq": 1, "action": "authentication_challenge", "data": {"token": token}}
        )
    )


# 웹소켓 연결 설정
def connect_websocket():
    ws = websocket.WebSocketApp(
        websocket_url,
        on_open=on_open,
        on_message=on_message,
        on_error=on_error,
        on_close=on_close,
    )
    ws.run_forever()


connect_websocket()
