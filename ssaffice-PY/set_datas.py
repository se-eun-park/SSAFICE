from setup import get_db
from models import *
from sqlalchemy.exc import IntegrityError


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
        return schedule.schedule_id


def insert_remind(remind):
    with get_db() as db:
        db.add(remind)
        db.commit()
        return remind.remind_id

def insert_file(file):
    with get_db() as db:
        db.add(file)
        db.commit()
        return file.file_id


def insert_mm_team(mm_team):
    with get_db() as db:
        try:
            db.add(mm_team)
            db.commit()
            return mm_team.mm_team_id
        except IntegrityError:
            db.rollback()
            print(
                f"Primary Key {mm_team.mm_team_id} already exists. Skipping insertion."
            )
