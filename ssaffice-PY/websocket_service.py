from mmapi import *
from setup import config, get_db
from models.models import Notice
from get_datas import *
from set_datas import *
from datetime import datetime


def is_notice(data):
    if data["event"] == "posted" and "test" in data["data"]["channel_display_name"]:
        return True
    return False


# 메시지를 분석하는 함수의 변수명.
def analyze_message(data):
    json_data = json.loads(data["data"]["post"])
    message_id = json_data["id"]
    message_content = json_data["message"]
    message = {"id": message_id, "message": message_content}

    today = datetime.now().date()
    try:
        return pipeline.invoke({"data": message, "today_date": today})
    except Exception as e:
        print("Error : ", e)


def essential_test(data):
    if "priority" in data["metadata"]:
        if data["metadata"]["priority"]["priority"] == "important":
            return True
    return False


def make_notice_entity(data, notice):
    json_data = json.loads(data["data"]["post"])
    is_essential = essential_test(json_data)
    mm_user_id = json_data["user_id"]
    user_id = get_user_id_by_user_mm_id(mm_user_id)
    original_message = json_data["message"]

    response_notice = Notice(
        message_id=notice["id"],
        title=notice["title"],
        original_message=original_message,
        content=notice["content"],
        start_date_time=notice["schedule_start_time"],
        end_date_time=notice["schedule_end_time"],
        is_essential=is_essential,
        task_type="공지",
        created_by=user_id,
    )

    return response_notice


def make_notice_channel_entity(data, id):
    json_data = json.loads(data["data"]["post"])
    team_id = get_team_id_by_team_code(data["data"]["team_id"])
    channel_id = get_channel_id_by_channel_code(json_data["channel_id"])
    notice_channel = Notice_Channel(
        notice_id=id, channel_id=channel_id, mm_team_id=team_id
    )
    return notice_channel


def file_download_if_file_exist(token, data):
    json_data = json.loads(data["data"]["post"])
    file_ids = (
        [file["id"] for file in json_data["metadata"]["files"]]
        if "files" in json_data["metadata"]
        else None
    )
    file_names = (
        [file["name"] for file in json_data["metadata"]["files"]]
        if "files" in json_data["metadata"]
        else None
    )
    if file_ids is not None:
        for file_id, file_name in zip(file_ids, file_names):
            file_download(token, file_id, file_name)

def file_download(token, file_id, file_name):
    response = get_file_by_file_id(token, file_id)

    content_type = response.headers.get("Content-Type")
    extension = mimetypes.guess_extension(content_type) if content_type else ".bin"

    download_name = f"{file_name}{extension}"
    with open(download_name, "wb") as file:
        file.write(response.content)

    print(f"{download_name} 다운로드 완료")
