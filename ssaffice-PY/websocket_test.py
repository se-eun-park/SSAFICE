import websocket
import json
from mmapi import *
from setup import config, get_db
from models.models import Notice
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
    if data["event"] == "posted" and "test" in data["data"]["channel_display_name"]:
        json_data = json.loads(data["data"]["post"])
        print(json_data)

        message_id = json_data["id"]
        message_content = json_data["message"]
        message = {"id": message_id, "message": message_content}
        try:
            output_message = pipeline.invoke({"data": message})
        except Exception as e:
            print("Error : ", e)
        print(output_message)

        # print(output_message)
        for notice in output_message["list"]:
            is_essential = False
            # 일정인 경우에는 일정 등록
            if notice["isTodo"] == "o":
                if "priority" in json_data["metadata"]:
                    if json_data["metadata"]["priority"]["priority"] == "important":
                        is_essential = True

                # Notice Entity 생성과정
                mm_user_id = json_data["user_id"]
                user_id = get_user_id_by_user_mm_id(mm_user_id)

                notice = Notice(
                    message_id=notice["id"],
                    title=notice["title"],
                    content=notice["content"],
                    start_date_time=notice["schedule_start_time"],
                    end_date_time=notice["schedule_end_time"],
                    is_essential=is_essential,
                    task_type="공지",
                    created_by=user_id,
                )
                id = insert_notice(notice)
                print("공지 등록 완료, notice_id :", id)

                # notice_channel entity생성
                team_id = get_team_id_by_team_code(data["data"]["team_id"])
                channel_id = get_channel_id_by_channel_code(json_data["channel_id"])

                notice_channel = Notice_Channel(
                    notice_id=id, channel_id=channel_id, mm_team_id=team_id
                )
                notice_channel_id = insert_notice_channel(notice_channel)
                print(
                    "notice_channel 등록 완료, notice_channel_id :", notice_channel_id
                )
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
