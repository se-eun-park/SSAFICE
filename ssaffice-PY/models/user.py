from sqlalchemy import Column, Integer, String, Boolean
from sqlalchemy.orm import relationship
from .base import Base
from .type import *


class User(Base):
    __tablename__ = "user"

    user_id = Column(Integer, primary_key=True, index=True)
    mm_user_id = Column(String(50), unique=True, index=True)
    email = Column(String(100), index=True)
    password = Column(String(60))
    name = Column(String(50), index=True)
    disabled_yn = Column(BooleanToYN, default="N")
    cohort_num = Column(Integer)
    region_cd = Column(String(10))
    track_cd = Column(String(20))
    class_num = Column(Integer)

    notices = relationship("Notice", back_populates="created_user")
