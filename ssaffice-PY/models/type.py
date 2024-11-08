import enum
from sqlalchemy import String
from sqlalchemy.types import TypeDecorator

# Enum 클래스 정의
class NoticeSourceType(enum.Enum):
    GLOBAL = "GLOBAL"
    TEAM = "TEAM"

class ScheduleSourceType(enum.Enum):
    GLOBAL_NOTICE = "GLOBAL_NOTICE"
    TEAM_NOTICE = "TEAM_NOTICE"
    PERSONAL = "PERSONAL"

class ScheduleStatusType(enum.Enum):
    TODO = "TODO"
    IN_PROGRESS = "IN_PROGRESS"
    DONE = "DONE"


# Boolean to Y/N 변환기
class BooleanToYN(TypeDecorator):
    impl = String(1)

    def process_bind_param(self, value, dialect):
        return "Y" if value else "N"

    def process_result_value(self, value, dialect):
        return value == "Y"
