from models import *
from setup import config, get_db


def get_user_id_by_user_mm_id(user_mm_id):
    with get_db() as db:
        user = db.query(User).filter(User.mm_user_id == user_mm_id).first()
        return user.user_id if user else None

def get_notice_by_notice_id(notice_id):
    with get_db() as db:
        notice = db.query(Notice).filter(Notice.notice_id == notice_id).first()
        return notice