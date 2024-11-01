from mmapi import *

# 로그인 후 메시지 요청
token = login_to_mattermost() ## 해당 토큰은 관리자 계정의 토큰이라 가정한다.
if token:
    print(get_user_info(token))
    user_teams = get_user_teams(token)
    print(user_teams)
    
    user_channels = get_channel_list_by_user_id(token)
    # # channel_id = []
    # for channel in user_channels:
    #     if 'test' in channel['display_name']:            
    #         print(channel)
            
    if user_channels:
    # #     # print("User Channels:", user_channels)  # 디버깅: 채널 정보 출력
        first_channel_id = user_channels[0]['id']
    # #     # user_team = get_user_teams(token)
    # #     # first_team_id = user_team[0]['id']
        print("first_channel_id : ", first_channel_id)
        print(get_channel_members_by_channel_id(token, first_channel_id))                        
        
    #     channel = get_channel_info_by_channel_id(token, first_channel_id)
    #     # print(channel)
        # messages = get_channel_message_list_by_channel_id(token, first_channel_id)
        # orders = messages['order']
    #     # print(orders)        
        # message_list = []
        # for order in orders :
        #     # user_id = message
        #     id = messages['posts'][order]['id']
        #     message = messages['posts'][order]['message']  
        #     dict = { 'id' : id, 'message' : message }
        #     message_list.append(dict)
        # print(message_list)      
       
    #     message_list = json.dumps(message_list, indent = 2, ensure_ascii=False)
    #     output_message = pipeline.invoke({"data" : message_list})
    #     print(output_message)
    # else:
    #     print("사용자가 속한 채널이 없습니다.")
else:
    print("로그인 실패로 API 호출을 진행할 수 없습니다.")


