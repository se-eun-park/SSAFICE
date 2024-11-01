from sqlalchemy import Column, Integer, String, ForeignKey, DateTime, Date, Boolean, create_engine
from sqlalchemy.orm import relationship
from sqlalchemy.orm import declarative_base
from sqlalchemy.sql import func
import sys
import os

# Project3의 경로를 root_dir로 설정하기 위함
current_dir = os.path.dirname(os.path.abspath(__file__))
root_dir = os.path.join(current_dir, '..')
sys.path.append(root_dir)

# root 디렉토리로 설정해서 setup을 import할 수 있음
from setup import config

Base = declarative_base()
engine = create_engine('mysql+pymysql://'+config.DB_USERNAME+':'+config.DB_PASSWORD+'@'+config.DB_HOST+':'+config.DB_PORT+'/'+config.DB_NAME)

# User Table
class User(Base):
    __tablename__ = "user"
    
    user_id = Column(Integer, primary_key=True, index=True)        
    email = Column(String(100), unique=True, index=True)
    password = Column(String(60))
    name = Column(String(50), index=True)    
    disabled_yn = Column(Boolean)
    cohort_num = Column(Integer)
    region_cd = Column(String(10))
    track_cd = Column(String(10))
    class_num = Column(Integer)
    curriculum_cd = Column(String(10))
    
    notices = relationship("Notice", back_populates="user")

# Notice Table
class Notice(Base):
    __tablename__ = "notice"
    
    notice_id = Column(Integer, primary_key=True, index=True)        
    message_id = Column(String(30))
    title = Column(String(255))
    content = Column(String(500))
    remind = Column(String(10))
    start_date = Column(Date)
    end_date = Column(Date)
    is_essential = Column(Boolean)
    category = Column(String(10))
    type = Column(String(10))
    created_at = Column(DateTime(timezone=True), server_default=func.now())
    updated_at = Column(DateTime(timezone=True))
    writerId = Column(Integer, ForeignKey('user.user_id'))

    writer = relationship("User", back_populates="notices")
    
class Test(Base):
    __tablename__ = "cm_test"
        
    id = Column(Integer, primary_key=True, index=True)        
    email = Column(String(100), unique=True, index=True)
    nickname = Column(String(50), index=True)
    password = Column(String(50)) 

# 테이블 생성(해당 테이블이 없는 경우에만 생성)
Base.metadata.create_all(bind=engine)