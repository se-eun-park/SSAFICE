import requests
from fastapi import FastAPI, Form
from pydantic import BaseModel
from fastapi.responses import JSONResponse
from setup import config
from mmapi import login, get_user_info

ssafy_api_key = config.SSAFY_API_KEY

app = FastAPI()

@app.post("/login")
def login_for_mapping(user_id: str = Form(...), user_pw: str = Form(...)):    
    token = login(user_id, user_pw)
    user_info = get_user_info(token)
    response = user_info['id']
    
    print(response)
    if response.status_code == 200:
        return response.json()
    else:
        print("Failed to login with status code:", response.status_code)
        return None