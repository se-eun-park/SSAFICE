from sqlalchemy import Column, Integer, ForeignKey, String
from sqlalchemy.orm import relationship
from .base import Base


class Notice_Channel(Base):
    __tablename__ = "notice_channel"

    notice_channel_id = Column(Integer, primary_key=True, index=True)
    notice_id = Column(Integer, ForeignKey("notice.notice_id"))
    channel_id = Column(String(50), ForeignKey("channel.channel_id"))    

    notice = relationship("Notice")
    channel_by_channel_id = relationship("Channel")
