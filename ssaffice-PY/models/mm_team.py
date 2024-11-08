from sqlalchemy import Column, String
from .base import Base


class MM_Team(Base):
    __tablename__ = "mm_team"

    mm_team_id = Column(String(50), primary_key=True, index=True)
    name = Column(String(50))
