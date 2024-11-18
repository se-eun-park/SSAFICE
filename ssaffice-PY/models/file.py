from sqlalchemy import Column, BigInteger, Integer, String

from .base import Base
from .type import *
from io import BytesIO
import hashlib
import base64



class File(Base):
    __tablename__ = "file"

    file_id = Column(Integer, primary_key=True, index=True)
    file_type = Column(String(20))
    file_name = Column(String(100))
    file_size = Column(BigInteger)
    ref_id = Column(BigInteger)
    hash = Column(String(100))
    mime_type = Column(String(20))
    is_deleted = Column(BooleanToYN, default=False)
    order_idx = Column(Integer)


def generate_hash(file):
    file = BytesIO(file)  
    digest = hashlib.sha256()
    buffer_size = 8192
    while chunk := file.read(buffer_size):
        digest.update(chunk)
    hash_bytes = digest.digest()
    return base64.urlsafe_b64encode(hash_bytes).rstrip(b"=").decode("utf-8")
