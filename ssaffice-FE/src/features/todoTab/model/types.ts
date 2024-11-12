import { createUser } from '@/features/announcementTab'

export type TodoItemDisplayType = {
  scheduleId: number
  title: string
  startDateTime?: Date
  endDateTime?: Date
  scheduleSourceTypeCd?: 'GLOBAL' | 'TEAM' | 'PERSONAL'
  scheduleStatusTypeCd?: 'TODO' | 'IN_PROGRESS' | 'DONE'

  user: createUser
}

// 선택적 파라메터는 테스트 후 필수로 바꾸어 주세요
