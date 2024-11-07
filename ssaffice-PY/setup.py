import os
from pathlib import Path
from dotenv import load_dotenv
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from contextlib import contextmanager

env_path = Path(__file__).resolve().parent / ".env"

load_dotenv(dotenv_path=env_path)


class Setting:
    DB_HOST: str  ## mariadB
    DB_PORT: str  ## mariaDB
    DB_NAME: str  ## mariaDB
    DB_USERNAME: str  ## mariaDB
    DB_PASSWORD: str  ## mariaDB
    OPENAI_API_KEY: str  ## openai
    SSAFY_API_KEY: str  ## ssafy
    MM_ID: str  ## mattermost
    MM_PW: str  ## mattermost
    OPENAI_MODEL_NAME: str  ## openai
    MM_BASEURL: str  ## mattermost
    SSAFY_BASE_URL: str  ## ssafy
    SSAFY_TEAM_ID: str  ## ssafy
    MM_WEBSOCKET_URL: str  ## mattermost
    MM_WEBHOOK_URL: str  ## mattermost
    S3_ACCESS_KEY: str  ## s3
    S3_SECRET_KEY: str  ## s3
    S3_PREFIX: str  ## s3
    S3_BUCKET_NAME: str  ## s3


config = Setting()
config.DB_HOST = os.getenv("DB_HOST")
config.DB_PORT = os.getenv("DB_PORT")
config.DB_NAME = os.getenv("DB_NAME")
config.DB_USERNAME = os.getenv("DB_USERNAME")
config.DB_PASSWORD = os.getenv("DB_PASSWORD")
config.OPENAI_API_KEY = os.getenv("OPENAI_API_KEY")
config.SSAFY_API_KEY = os.getenv("SSAFY_API_KEY")
config.MM_ID = os.getenv("MM_ID")
config.MM_PW = os.getenv("MM_PW")
config.OPENAI_MODEL_NAME = os.getenv("OPENAI_MODEL_NAME")
config.MM_BASEURL = os.getenv("MM_BASEURL")
config.SSAFY_BASE_URL = os.getenv("SSAFY_BASE_URL")
config.SSAFY_TEAM_ID = os.getenv("SSAFY_TEAM_ID")
config.MM_WEBSOCKET_URL = os.getenv("MM_WEBSOCKETURL")
config.MM_WEBHOOK_URL = os.getenv("MM_WEBHOOK_URL")
config.S3_ACCESS_KEY = os.getenv("S3_ACCESS_KEY")
config.S3_SECRET_KEY = os.getenv("S3_SECRET_KEY")
config.S3_PREFIX = os.getenv("S3_PREFIX")
config.S3_BUCKET_NAME = os.getenv("S3_BUCKET_NAME")


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
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)


# db랑 연결하기 위한 메서드
@contextmanager
def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()
