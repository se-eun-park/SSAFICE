from setup import get_db

def insert_notice(notice):  
  with get_db() as db: 
    db.add(notice)
    db.commit()
