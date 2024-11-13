import type { AnnouncementItemDisplay, createUser } from '@/features/announcementTab'

export type TodoItemDisplay = {
  scheduleId: number
  title: string
  startDateTime?: Date
  endDateTime?: Date
  scheduleSourceTypeCd?: 'GLOBAL' | 'TEAM' | 'PERSONAL'
  scheduleStatusTypeCd?: 'TODO' | 'IN_PROGRESS' | 'DONE'
  isEssential?: boolean
  isEnroll?: boolean

  user: createUser
}

export type ScheduleItemDisplay = {
  todo: TodoItemDisplay
  announcement: AnnouncementItemDisplay
}

// 선택적 파라메터는 테스트 후 필수로 바꾸어 주세요

export type TodoListDisplay = {
  // 일정 조회 API response
  todos: ScheduleItemDisplay[]
  statusCounts: number[] // 차례로 TODO, IN_PROGRESS, DONE의 개수가 옵니다
}

// sorting은 BE에서 해주니까 빼고 작업 (날짜로 묶을 필요 없음)

// MARK: DATAS
export const dummyTodos: TodoListDisplay = {
  todos: [
    // 2024-11-13
    {
      todo: {
        scheduleId: 1,
        title: '팀 미팅 준비',
        startDateTime: new Date('2024-11-13T09:00:00'),
        endDateTime: new Date('2024-11-13T10:00:00'),
        scheduleSourceTypeCd: 'TEAM',
        scheduleStatusTypeCd: 'TODO',
        isEssential: false,
        isEnroll: true,
        user: {
          userId: 1,
          name: '김철수',
        },
      },
      announcement: {
        user: { userId: 2, name: '박영희' },
        createdAt: new Date('2024-11-13T08:00:00'),
        title: '팀 미팅 일정 공지',
        content: '팀 미팅이 2024년 11월 13일 오전 9시에 진행됩니다.',
        noticeId: 1,
      },
    },
    {
      todo: {
        scheduleId: 2,
        title: '회식 장소 예약',
        startDateTime: new Date('2024-11-13T10:00:00'),
        endDateTime: new Date('2024-11-13T11:00:00'),
        scheduleSourceTypeCd: 'PERSONAL',
        scheduleStatusTypeCd: 'IN_PROGRESS',
        isEssential: true,
        isEnroll: false,
        user: {
          userId: 3,
          name: '이민호',
        },
      },
      announcement: {
        user: { userId: 4, name: '정하늘' },
        createdAt: new Date('2024-11-13T09:00:00'),
        title: '회식 장소 공지',
        content: '회식 장소 예약 완료되었습니다.',
        noticeId: 2,
      },
    },
    {
      todo: {
        scheduleId: 3,
        title: '프로젝트 리뷰',
        startDateTime: new Date('2024-11-13T11:00:00'),
        endDateTime: new Date('2024-11-13T12:00:00'),
        scheduleSourceTypeCd: 'GLOBAL',
        scheduleStatusTypeCd: 'DONE',
        isEssential: true,
        isEnroll: true,
        user: {
          userId: 5,
          name: '최영수',
        },
      },
      announcement: {
        user: { userId: 6, name: '김소정' },
        createdAt: new Date('2024-11-13T10:00:00'),
        title: '프로젝트 리뷰 완료',
        content: '프로젝트 리뷰가 2024년 11월 13일 11시에 종료되었습니다.',
        noticeId: 3,
      },
    },
    {
      todo: {
        scheduleId: 4,
        title: '디자인 검토',
        startDateTime: new Date('2024-11-13T12:00:00'),
        endDateTime: new Date('2024-11-13T13:00:00'),
        scheduleSourceTypeCd: 'PERSONAL',
        scheduleStatusTypeCd: 'TODO',
        isEssential: false,
        isEnroll: true,
        user: {
          userId: 7,
          name: '홍길동',
        },
      },
      announcement: {
        user: { userId: 8, name: '이서진' },
        createdAt: new Date('2024-11-13T11:00:00'),
        title: '디자인 검토 공지',
        content: '디자인 검토 일정이 2024년 11월 13일 오후 12시에 진행됩니다.',
        noticeId: 4,
      },
    },
    {
      todo: {
        scheduleId: 5,
        title: '개발 업무 정리',
        startDateTime: new Date('2024-11-13T13:00:00'),
        endDateTime: new Date('2024-11-13T14:00:00'),
        scheduleSourceTypeCd: 'TEAM',
        scheduleStatusTypeCd: 'IN_PROGRESS',
        isEssential: true,
        isEnroll: true,
        user: {
          userId: 9,
          name: '박준형',
        },
      },
      announcement: {
        user: { userId: 10, name: '김유리' },
        createdAt: new Date('2024-11-13T12:00:00'),
        title: '개발 업무 정리 공지',
        content: '개발 업무 정리가 2024년 11월 13일 오후 1시에 시작됩니다.',
        noticeId: 5,
      },
    },
    {
      todo: {
        scheduleId: 6,
        title: '팀 보고서 작성',
        startDateTime: new Date('2024-11-13T14:00:00'),
        endDateTime: new Date('2024-11-13T15:00:00'),
        scheduleSourceTypeCd: 'GLOBAL',
        scheduleStatusTypeCd: 'DONE',
        isEssential: false,
        isEnroll: false,
        user: {
          userId: 11,
          name: '한지민',
        },
      },
      announcement: {
        user: { userId: 12, name: '정윤희' },
        createdAt: new Date('2024-11-13T13:00:00'),
        title: '팀 보고서 제출 완료',
        content: '팀 보고서가 2024년 11월 13일 오후 2시에 제출되었습니다.',
        noticeId: 6,
      },
    },
    {
      todo: {
        scheduleId: 7,
        title: '고객 미팅 준비',
        startDateTime: new Date('2024-11-13T15:00:00'),
        endDateTime: new Date('2024-11-13T16:00:00'),
        scheduleSourceTypeCd: 'PERSONAL',
        scheduleStatusTypeCd: 'TODO',
        isEssential: true,
        isEnroll: true,
        user: {
          userId: 13,
          name: '윤상호',
        },
      },
      announcement: {
        user: { userId: 14, name: '강태형' },
        createdAt: new Date('2024-11-13T14:00:00'),
        title: '고객 미팅 일정 공지',
        content: '고객 미팅 준비가 2024년 11월 13일 오후 3시에 시작됩니다.',
        noticeId: 7,
      },
    },
    // 2024-11-14 ~ 2024-11-20 일정은 동일한 패턴으로 반복됩니다
    {
      todo: {
        scheduleId: 8,
        title: '팀 미팅 준비',
        startDateTime: new Date('2024-11-14T09:00:00'),
        endDateTime: new Date('2024-11-14T10:00:00'),
        scheduleSourceTypeCd: 'TEAM',
        scheduleStatusTypeCd: 'TODO',
        isEssential: false,
        isEnroll: true,
        user: {
          userId: 1,
          name: '김철수',
        },
      },
      announcement: {
        user: { userId: 2, name: '박영희' },
        createdAt: new Date('2024-11-14T08:00:00'),
        title: '팀 미팅 일정 공지',
        content: '팀 미팅이 2024년 11월 14일 오전 9시에 진행됩니다.',
        noticeId: 8,
      },
    },
    {
      todo: {
        scheduleId: 9,
        title: '회식 장소 예약',
        startDateTime: new Date('2024-11-14T10:00:00'),
        endDateTime: new Date('2024-11-14T11:00:00'),
        scheduleSourceTypeCd: 'PERSONAL',
        scheduleStatusTypeCd: 'IN_PROGRESS',
        isEssential: true,
        isEnroll: false,
        user: {
          userId: 3,
          name: '이민호',
        },
      },
      announcement: {
        user: { userId: 4, name: '정하늘' },
        createdAt: new Date('2024-11-14T09:00:00'),
        title: '회식 장소 공지',
        content: '회식 장소 예약 완료되었습니다.',
        noticeId: 9,
      },
    },
    {
      todo: {
        scheduleId: 10,
        title: '프로젝트 리뷰',
        startDateTime: new Date('2024-11-14T11:00:00'),
        endDateTime: new Date('2024-11-14T12:00:00'),
        scheduleSourceTypeCd: 'GLOBAL',
        scheduleStatusTypeCd: 'DONE',
        isEssential: true,
        isEnroll: true,
        user: {
          userId: 5,
          name: '최영수',
        },
      },
      announcement: {
        user: { userId: 6, name: '김소정' },
        createdAt: new Date('2024-11-14T10:00:00'),
        title: '프로젝트 리뷰 완료',
        content: '프로젝트 리뷰가 2024년 11월 14일 11시에 종료되었습니다.',
        noticeId: 10,
      },
    },
    {
      todo: {
        scheduleId: 11,
        title: '디자인 검토',
        startDateTime: new Date('2024-11-14T12:00:00'),
        endDateTime: new Date('2024-11-14T13:00:00'),
        scheduleSourceTypeCd: 'PERSONAL',
        scheduleStatusTypeCd: 'TODO',
        isEssential: false,
        isEnroll: true,
        user: {
          userId: 7,
          name: '홍길동',
        },
      },
      announcement: {
        user: { userId: 8, name: '이서진' },
        createdAt: new Date('2024-11-14T11:00:00'),
        title: '디자인 검토 공지',
        content: '디자인 검토 일정이 2024년 11월 14일 오후 12시에 진행됩니다.',
        noticeId: 11,
      },
    },
    {
      todo: {
        scheduleId: 12,
        title: '개발 업무 정리',
        startDateTime: new Date('2024-11-14T13:00:00'),
        endDateTime: new Date('2024-11-14T14:00:00'),
        scheduleSourceTypeCd: 'TEAM',
        scheduleStatusTypeCd: 'IN_PROGRESS',
        isEssential: true,
        isEnroll: true,
        user: {
          userId: 9,
          name: '박준형',
        },
      },
      announcement: {
        user: { userId: 10, name: '김유리' },
        createdAt: new Date('2024-11-14T12:00:00'),
        title: '개발 업무 정리 공지',
        content: '개발 업무 정리가 2024년 11월 14일 오후 1시에 시작됩니다.',
        noticeId: 12,
      },
    },
    {
      todo: {
        scheduleId: 13,
        title: '팀 보고서 작성',
        startDateTime: new Date('2024-11-14T14:00:00'),
        endDateTime: new Date('2024-11-14T15:00:00'),
        scheduleSourceTypeCd: 'GLOBAL',
        scheduleStatusTypeCd: 'DONE',
        isEssential: false,
        isEnroll: false,
        user: {
          userId: 11,
          name: '한지민',
        },
      },
      announcement: {
        user: { userId: 12, name: '정윤희' },
        createdAt: new Date('2024-11-14T13:00:00'),
        title: '팀 보고서 제출 완료',
        content: '팀 보고서가 2024년 11월 14일 오후 2시에 제출되었습니다.',
        noticeId: 13,
      },
    },
    {
      todo: {
        scheduleId: 14,
        title: '고객 미팅 준비',
        startDateTime: new Date('2024-11-14T15:00:00'),
        endDateTime: new Date('2024-11-14T16:00:00'),
        scheduleSourceTypeCd: 'PERSONAL',
        scheduleStatusTypeCd: 'TODO',
        isEssential: true,
        isEnroll: true,
        user: {
          userId: 13,
          name: '윤상호',
        },
      },
      announcement: {
        user: { userId: 14, name: '강태형' },
        createdAt: new Date('2024-11-14T14:00:00'),
        title: '고객 미팅 일정 공지',
        content: '고객 미팅 준비가 2024년 11월 14일 오후 3시에 시작됩니다.',
        noticeId: 14,
      },
    },
  ],
  statusCounts: [18, 15, 16], // TODO: 18개, IN_PROGRESS: 15개, DONE: 16개
}
