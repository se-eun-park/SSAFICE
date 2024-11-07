from sqlalchemy import Column, Integer, String, ForeignKey
from sqlalchemy.orm import relationship
from .base import Base


class Channel(Base):
    __tablename__ = "channel"

    channel_id = Column(Integer, primary_key=True, index=True)
    mm_team_id = Column(Integer, ForeignKey("mm_team.mm_team_id"))
    name = Column(String(50))
    code = Column(String(50))

    team = relationship("MM_Team")
