from mmapi import *

# 로그인 후 메시지 요청
token = make_mattermost_admin_token() ## 해당 토큰은 관리자 계정의 토큰이라 가정한다.
if token:
    user_teams = get_user_teams(token)
    print(user_teams)
else:
    print("로그인 실패로 API 호출을 진행할 수 없습니다.")


