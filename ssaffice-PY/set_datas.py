from setup import get_db
from models import *


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

def insert_schedule(schedule):
    with get_db() as db:
        db.add(schedule)
        db.commit()
        return schedule.user_id