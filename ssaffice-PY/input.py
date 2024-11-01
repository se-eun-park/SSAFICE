from setup import get_db

def insert_test(test):  
  with get_db() as db: 
    db.add(test)
    db.commit()
