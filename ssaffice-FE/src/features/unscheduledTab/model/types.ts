import { createUser } from '@/features/announcementTab'
// 유저 타입은 아래 공지, 일정 타입에도 다 들어가 있지만,
// API 응답에서 데이터 계층구조가 변동되어 유저가 공지/일정에 소속되지 않고 독립적으로 오는 경우 사용해 주세요

import { AnnouncementItemDisplayType } from '@/features/announcementTab'
import { todoItemDisplayType } from '@/features/todoTab/model/types'

// 미등록 공지 타입은 일정 + 공지가 더해진 형태의 타입입니다.
export type unscheduledItemDisplayType = {
  todo: todoItemDisplayType
  announcement: AnnouncementItemDisplayType
}
