from setup import get_db
from models.models import Notice, Notice_Channel


def insert_notice(notice):
    with get_db() as db:
        db.add(notice)
        db.commit()
        return notice.notice_id


def insert_notice_channel(notice_channel):
    with get_db() as db:
        db.add(notice_channel)
        db.commit()
        return notice_channel.notice_channel_id


def insert_notice_channel_from_notice_id(notice_id, channel_id, mm_team_id):
    with get_db() as db:
        notice_channel = Notice_Channel(
            notice_id=notice_id, channel_id=channel_id, mm_team_id=mm_team_id
        )
        db.add(notice_channel)
        db.commit()
