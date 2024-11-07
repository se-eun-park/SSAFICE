from sqlalchemy import Column, Integer, String
from .base import Base


class MM_Team(Base):
    __tablename__ = "mm_team"

    mm_team_id = Column(Integer, primary_key=True, index=True)
    name = Column(String(50))
    team_code = Column(String(50))
