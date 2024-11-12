from sqlalchemy import Column, Integer, String, DateTime, ForeignKey, Boolean, Enum
from sqlalchemy.orm import relationship
from sqlalchemy.sql import func
from .base import Base
from .type import *

class Notice(Base):
    __tablename__ = "notice"

    notice_id = Column(Integer, primary_key=True, index=True)
    message_id = Column(String(30))
    title = Column(String(255))
    original_message = Column(String(1000))
    content = Column(String(500))
    start_date_time = Column(DateTime)
    end_date_time = Column(DateTime)
    is_essential = Column(BooleanToYN, default="N")
    notice_source_type = Column(Enum(NoticeType), nullable=True)
    created_at = Column(DateTime(timezone=True), server_default=func.now())
    updated_at = Column(DateTime(timezone=True), server_default=func.now())
    created_by = Column(Integer, ForeignKey("user.user_id"))
    channel_id = Column(String(50), ForeignKey("channel.channel_id"))

    created_user = relationship("User", back_populates="notices")
