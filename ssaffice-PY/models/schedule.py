from sqlalchemy import Column, String, Integer, ForeignKey, Boolean, Enum, DateTime
from sqlalchemy.orm import relationship, backref
from .type import *
from .base import Base


class Schedule(Base):
    __tablename__ = "schedule"

    schedule_id = Column(Integer, primary_key=True, autoincrement=True)
    title = Column(String(255))
    memo = Column(String(500))
    start_date_time = Column(DateTime(timezone=True))
    end_date_time = Column(DateTime(timezone=True))    
    schedule_source_type = Column(Enum(ScheduleSourceType), nullable=True)
    schedule_status_type = Column(
        Enum(ScheduleStatusType), default=ScheduleStatusType.TODO, nullable=True
    )
    is_essential = Column(BooleanToYN, default="N")
    is_enroll = Column(BooleanToYN, default="N")
    user_id = Column(Integer, ForeignKey("user.user_id"), nullable=True)
    notice_id = Column(Integer, ForeignKey("notice.notice_id"), nullable=True)

class Remind(Base):
    __tablename__ = "remind"

    remind_id = Column(Integer, primary_key=True, autoincrement=True)
    is_essential = Column(BooleanToYN, default="N")
    time = Column(DateTime(timezone=True))
    schedule_id = Column(Integer, ForeignKey("schedule.schedule_id"), nullable=False)
