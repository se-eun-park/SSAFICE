from sqlalchemy import (
    Column,
    Integer,
    String,
    ForeignKey,
    DateTime,
    Date,
    Boolean,
    create_engine,
)
from sqlalchemy.orm import relationship
from sqlalchemy.orm import declarative_base
from sqlalchemy.sql import func
import sys
import os

# Project3의 경로를 root_dir로 설정하기 위함
current_dir = os.path.dirname(os.path.abspath(__file__))
root_dir = os.path.join(current_dir, "..")
sys.path.append(root_dir)

# root 디렉토리로 설정해서 setup을 import할 수 있음
from setup import config

Base = declarative_base()
engine = create_engine(
    "mysql+pymysql://"
    + config.DB_USERNAME
    + ":"
    + config.DB_PASSWORD
    + "@"
    + config.DB_HOST
    + ":"
    + config.DB_PORT
    + "/"
    + config.DB_NAME
)


# User Table
class User(Base):
    __tablename__ = "user"

    user_id = Column(Integer, primary_key=True, index=True)
    mm_user_id = Column(String(50), unique=True, index=True)
    email = Column(String(100), index=True)
    password = Column(String(60))
    name = Column(String(50), index=True)
    disabled_yn = Column(Boolean)
    cohort_num = Column(Integer)
    region_cd = Column(String(10))
    track_cd = Column(String(10))
    class_num = Column(Integer)
    curriculum_cd = Column(String(10))

    notices = relationship("Notice", back_populates="created_user")


# Notice Table
class Notice(Base):
    __tablename__ = "notice"

    notice_id = Column(Integer, primary_key=True, index=True)
    message_id = Column(String(30))
    title = Column(String(255))
    original_message = Column(String(1000))
    content = Column(String(500))
    start_date_time = Column(DateTime)
    end_date_time = Column(DateTime)
    is_essential = Column(Boolean)
    task_type = Column(String(10))
    created_at = Column(DateTime(timezone=True), server_default=func.now())
    updated_at = Column(DateTime(timezone=True), server_default=func.now())
    created_by = Column(Integer, ForeignKey("user.user_id"))

    created_user = relationship("User", back_populates="notices")


class Schedule(Base):
    __tablename__ = "schedule"

    scheduled_id = Column(Integer, primary_key=True, index=True)
    title = Column(String(255))
    memo = Column(String(500))
    start_date = Column(Date)
    end_date = Column(DateTime)
    password = Column(String(50))


class MM_Team(Base):
    __tablename__ = "mm_team"

    mm_team_id = Column(Integer, primary_key=True, index=True)
    name = Column(String(50))
    team_code = Column(String(50))


class Channel(Base):
    __tablename__ = "channel"

    channel_id = Column(Integer, primary_key=True, index=True)
    mm_team_id = Column(Integer, ForeignKey("mm_team.mm_team_id"))
    name = Column(String(50))
    code = Column(String(50))

    team = relationship("MM_Team")


class Notice_Channel(Base):
    __tablename__ = "notice_channel"

    notice_channel_id = Column(Integer, primary_key=True, index=True)
    notice_id = Column(Integer, ForeignKey("notice.notice_id"))
    channel_id = Column(Integer, ForeignKey("channel.channel_id"))
    mm_team_id = Column(Integer, ForeignKey("channel.mm_team_id"))

    notice = relationship("Notice")
    channel_by_channel_id = relationship("Channel", foreign_keys=[channel_id])


# 테이블 생성(해당 테이블이 없는 경우에만 생성)
Base.metadata.create_all(bind=engine)
