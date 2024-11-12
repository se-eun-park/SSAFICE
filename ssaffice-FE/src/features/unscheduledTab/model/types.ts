import { createUser } from '@/features/announcementTab'
// 유저 타입은 아래 공지, 일정 타입에도 다 들어가 있지만,
// API 응답에서 데이터 계층구조가 변동되어 유저가 공지/일정에 소속되지 않고 독립적으로 오는 경우 사용해 주세요

import { AnnouncementItemDisplayType } from '@/features/announcementTab'
import { TodoItemDisplayType } from '@/features/todoTab/model/types'

// 미등록 공지 타입은 일정 + 공지가 더해진 형태의 타입입니다.
export type UnscheduledItemDisplayType = {
  todo: TodoItemDisplayType
  announcement: AnnouncementItemDisplayType
  // user: createUser // 유저 정보가 일정/공지에서 독립되어 오는 경우
}

export type UnscheduledListDisplayType = Record<string, UnscheduledItemDisplayType>
// 날짜로 묶은 미등록 공지 items

// 더미 데이터 배열입니다.
// MARK: datas
export const dummyUnschedules: UnscheduledItemDisplayType[] = [
  {
    todo: {
      scheduleId: 1,
      title: '프로젝트 미팅',
      startDateTime: new Date('2024-11-15T10:00:00'),
      endDateTime: new Date('2024-11-15T11:00:00'),
      scheduleSourceTypeCd: 'TEAM',
      scheduleStatusTypeCd: 'IN_PROGRESS',
      user: {
        userId: 101,
        email: 'alice@example.com',
        name: 'Alice Johnson',
        profileImageUrl: 'https://example.com/images/alice.jpg',
      },
    },
    announcement: {
      user: {
        userId: 101,
        email: 'alice@example.com',
        name: 'Alice Johnson',
        profileImageUrl: 'https://example.com/images/alice.jpg',
      },
      createdAt: new Date('2024-11-10T09:00:00'),
      title: '예정된 미팅 일정 안내',
      content: '프로젝트 미팅은 11월 15일 오전 10시에 진행됩니다.',
      noticeId: 1,
    },
  },
  {
    todo: {
      scheduleId: 2,
      title: '밥과 점심',
      startDateTime: new Date('2024-11-16T12:00:00'),
      endDateTime: new Date('2024-11-16T13:00:00'),
      scheduleSourceTypeCd: 'PERSONAL',
      scheduleStatusTypeCd: 'TODO',
      user: {
        userId: 102,
        email: 'bob@example.com',
        name: 'Bob Lee',
        profileImageUrl: 'https://example.com/images/bob.jpg',
      },
    },
    announcement: {
      user: {
        userId: 102,
        email: 'bob@example.com',
        name: 'Bob Lee',
        profileImageUrl: 'https://example.com/images/bob.jpg',
      },
      createdAt: new Date('2024-11-10T12:00:00'),
      title: '점심 미팅 알림',
      content: '11월 16일 정오에 점심 미팅이 예정되어 있습니다.',
      noticeId: 2,
    },
  },
  {
    todo: {
      scheduleId: 3,
      title: '팀 브레인스토밍 세션',
      startDateTime: new Date('2024-11-18T14:00:00'),
      endDateTime: new Date('2024-11-18T16:00:00'),
      scheduleSourceTypeCd: 'TEAM', // 팀 일정
      scheduleStatusTypeCd: 'TODO', // 해야 할 일
      user: {
        userId: 103,
        email: 'carol@example.com',
        name: 'Carol Smith',
        profileImageUrl: 'https://example.com/images/carol.jpg',
      },
    },
    announcement: {
      user: {
        userId: 103,
        email: 'carol@example.com',
        name: 'Carol Smith',
        profileImageUrl: 'https://example.com/images/carol.jpg',
      },
      createdAt: new Date('2024-11-12T10:00:00'),
      title: '브레인스토밍 세션 안내',
      content: '브레인스토밍 세션은 11월 18일 오후 2시에 진행됩니다.',
      noticeId: 3,
    },
  },
  {
    todo: {
      scheduleId: 4,
      title: '병원 예약',
      startDateTime: new Date('2024-11-19T09:30:00'),
      endDateTime: new Date('2024-11-19T10:00:00'),
      scheduleSourceTypeCd: 'PERSONAL', // 개인 일정
      scheduleStatusTypeCd: 'TODO', // 해야 할 일
      user: {
        userId: 104,
        email: 'david@example.com',
        name: 'David Wang',
        profileImageUrl: 'https://example.com/images/david.jpg',
      },
    },
    announcement: {
      user: {
        userId: 104,
        email: 'david@example.com',
        name: 'David Wang',
        profileImageUrl: 'https://example.com/images/david.jpg',
      },
      createdAt: new Date('2024-11-14T15:00:00'),
      title: '병원 예약 알림',
      content: '11월 19일 오전 9시 30분에 병원 예약이 있습니다.',
      noticeId: 4,
    },
  },
  {
    todo: {
      scheduleId: 5,
      title: '이브와 코드 리뷰',
      startDateTime: new Date('2024-11-20T13:00:00'),
      endDateTime: new Date('2024-11-20T14:00:00'),
      scheduleSourceTypeCd: 'TEAM', // 팀 일정
      scheduleStatusTypeCd: 'TODO', // 해야 할 일
      user: {
        userId: 105,
        email: 'eve@example.com',
        name: 'Eve Adams',
        profileImageUrl: 'https://example.com/images/eve.jpg',
      },
    },
    announcement: {
      user: {
        userId: 105,
        email: 'eve@example.com',
        name: 'Eve Adams',
        profileImageUrl: 'https://example.com/images/eve.jpg',
      },
      createdAt: new Date('2024-11-13T11:30:00'),
      title: '코드 리뷰 회의 안내',
      content: '이브와의 코드 리뷰 회의가 11월 20일 오후 1시에 예정되어 있습니다.',
      noticeId: 5,
    },
  },
  {
    todo: {
      scheduleId: 6,
      title: '월간 보고서 제출',
      startDateTime: new Date('2024-11-21T09:00:00'),
      endDateTime: new Date('2024-11-21T10:00:00'),
      scheduleSourceTypeCd: 'GLOBAL', // 전사 일정
      scheduleStatusTypeCd: 'TODO', // 해야 할 일
      user: {
        userId: 106,
        email: 'frank@example.com',
        name: 'Frank Miller',
        profileImageUrl: 'https://example.com/images/frank.jpg',
      },
    },
    announcement: {
      user: {
        userId: 106,
        email: 'frank@example.com',
        name: 'Frank Miller',
        profileImageUrl: 'https://example.com/images/frank.jpg',
      },
      createdAt: new Date('2024-11-15T08:00:00'),
      title: '월간 보고서 제출 안내',
      content: '11월 21일까지 월간 보고서를 제출해 주시기 바랍니다.',
      noticeId: 6,
    },
  },
  {
    todo: {
      scheduleId: 7,
      title: '팀 회식',
      startDateTime: new Date('2024-11-22T18:30:00'),
      endDateTime: new Date('2024-11-22T21:00:00'),
      scheduleSourceTypeCd: 'TEAM', // 팀 일정
      scheduleStatusTypeCd: 'TODO', // 해야 할 일
      user: {
        userId: 107,
        email: 'grace@example.com',
        name: 'Grace Lee',
        profileImageUrl: 'https://example.com/images/grace.jpg',
      },
    },
    announcement: {
      user: {
        userId: 107,
        email: 'grace@example.com',
        name: 'Grace Lee',
        profileImageUrl: 'https://example.com/images/grace.jpg',
      },
      createdAt: new Date('2024-11-16T14:30:00'),
      title: '팀 회식 안내',
      content: '11월 22일 오후 6시 30분에 팀 회식이 있습니다. 참석 부탁드립니다.',
      noticeId: 7,
    },
  },
  {
    todo: {
      scheduleId: 8,
      title: '정기 점검',
      startDateTime: new Date('2024-11-23T08:00:00'),
      endDateTime: new Date('2024-11-23T12:00:00'),
      scheduleSourceTypeCd: 'GLOBAL', // 전사 일정
      scheduleStatusTypeCd: 'TODO', // 해야 할 일
      user: {
        userId: 108,
        email: 'hannah@example.com',
        name: 'Hannah Scott',
        profileImageUrl: 'https://example.com/images/hannah.jpg',
      },
    },
    announcement: {
      user: {
        userId: 108,
        email: 'hannah@example.com',
        name: 'Hannah Scott',
        profileImageUrl: 'https://example.com/images/hannah.jpg',
      },
      createdAt: new Date('2024-11-17T10:00:00'),
      title: '정기 점검 공지',
      content: '11월 23일 오전 8시부터 정기 점검이 진행됩니다. 불편을 드려 죄송합니다.',
      noticeId: 8,
    },
  },
  {
    todo: {
      scheduleId: 9,
      title: '오픈 소스 기여 회의',
      startDateTime: new Date('2024-11-24T15:00:00'),
      endDateTime: new Date('2024-11-24T16:00:00'),
      scheduleSourceTypeCd: 'TEAM', // 팀 일정
      scheduleStatusTypeCd: 'TODO', // 해야 할 일
      user: {
        userId: 109,
        email: 'ian@example.com',
        name: 'Ian Thomas',
        profileImageUrl: 'https://example.com/images/ian.jpg',
      },
    },
    announcement: {
      user: {
        userId: 109,
        email: 'ian@example.com',
        name: 'Ian Thomas',
        profileImageUrl: 'https://example.com/images/ian.jpg',
      },
      createdAt: new Date('2024-11-18T11:30:00'),
      title: '오픈 소스 기여 회의 안내',
      content: '11월 24일 오후 3시 오픈 소스 기여 회의가 있습니다. 많은 참여 부탁드립니다.',
      noticeId: 9,
    },
  },
  {
    todo: {
      scheduleId: 10,
      title: '헬스장 트레이닝',
      startDateTime: new Date('2024-11-25T07:00:00'),
      endDateTime: new Date('2024-11-25T08:00:00'),
      scheduleSourceTypeCd: 'PERSONAL', // 개인 일정
      scheduleStatusTypeCd: 'TODO', // 해야 할 일
      user: {
        userId: 110,
        email: 'jackson@example.com',
        name: 'Jackson Brooks',
        profileImageUrl: 'https://example.com/images/jackson.jpg',
      },
    },
    announcement: {
      user: {
        userId: 110,
        email: 'jackson@example.com',
        name: 'Jackson Brooks',
        profileImageUrl: 'https://example.com/images/jackson.jpg',
      },
      createdAt: new Date('2024-11-19T13:00:00'),
      title: '헬스장 트레이닝 알림',
      content: '11월 25일 오전 7시에 트레이닝 세션이 예정되어 있습니다. 준비해 주세요.',
      noticeId: 10,
    },
  },
  {
    todo: {
      scheduleId: 11,
      title: '웹사이트 업데이트 예정',
      startDateTime: new Date('2024-11-26T10:00:00'),
      endDateTime: new Date('2024-11-26T11:00:00'),
      scheduleSourceTypeCd: 'GLOBAL', // 전사 일정
      scheduleStatusTypeCd: 'TODO', // 해야 할 일
      user: {
        userId: 111,
        email: 'karen@example.com',
        name: 'Karen Wilson',
        profileImageUrl: 'https://example.com/images/karen.jpg',
      },
    },
    announcement: {
      user: {
        userId: 111,
        email: 'karen@example.com',
        name: 'Karen Wilson',
        profileImageUrl: 'https://example.com/images/karen.jpg',
      },
      createdAt: new Date('2024-11-20T08:00:00'),
      title: '웹사이트 업데이트 공지',
      content:
        '11월 26일 오전 10시부터 웹사이트 업데이트가 진행됩니다. 서비스 이용에 차질이 있을 수 있습니다.',
      noticeId: 11,
    },
  },
  {
    todo: {
      scheduleId: 12,
      title: '영어 회화 클래스',
      startDateTime: new Date('2024-11-27T17:00:00'),
      endDateTime: new Date('2024-11-27T18:00:00'),
      scheduleSourceTypeCd: 'PERSONAL', // 개인 일정
      scheduleStatusTypeCd: 'TODO', // 해야 할 일
      user: {
        userId: 112,
        email: 'lily@example.com',
        name: 'Lily Green',
        profileImageUrl: 'https://example.com/images/lily.jpg',
      },
    },
    announcement: {
      user: {
        userId: 112,
        email: 'lily@example.com',
        name: 'Lily Green',
        profileImageUrl: 'https://example.com/images/lily.jpg',
      },
      createdAt: new Date('2024-11-21T14:00:00'),
      title: '영어 회화 클래스 안내',
      content: '11월 27일 오후 5시부터 영어 회화 클래스가 시작됩니다.',
      noticeId: 12,
    },
  },
  {
    todo: {
      scheduleId: 13,
      title: '회의 준비',
      startDateTime: new Date('2024-11-28T08:30:00'),
      endDateTime: new Date('2024-11-28T09:00:00'),
      scheduleSourceTypeCd: 'GLOBAL', // 전사 일정
      scheduleStatusTypeCd: 'TODO', // 해야 할 일
      user: {
        userId: 113,
        email: 'michael@example.com',
        name: 'Michael Scott',
        profileImageUrl: 'https://example.com/images/michael.jpg',
      },
    },
    announcement: {
      user: {
        userId: 113,
        email: 'michael@example.com',
        name: 'Michael Scott',
        profileImageUrl: 'https://example.com/images/michael.jpg',
      },
      createdAt: new Date('2024-11-22T09:00:00'),
      title: '회의 준비 안내',
      content: '11월 28일 오전 8시 30분 회의 준비가 필요합니다.',
      noticeId: 13,
    },
  },
  {
    todo: {
      scheduleId: 14,
      title: '소셜 미디어 홍보',
      startDateTime: new Date('2024-11-29T10:00:00'),
      endDateTime: new Date('2024-11-29T11:00:00'),
      scheduleSourceTypeCd: 'TEAM', // 팀 일정
      scheduleStatusTypeCd: 'TODO', // 해야 할 일
      user: {
        userId: 114,
        email: 'nancy@example.com',
        name: 'Nancy Davis',
        profileImageUrl: 'https://example.com/images/nancy.jpg',
      },
    },
    announcement: {
      user: {
        userId: 114,
        email: 'nancy@example.com',
        name: 'Nancy Davis',
        profileImageUrl: 'https://example.com/images/nancy.jpg',
      },
      createdAt: new Date('2024-11-23T10:30:00'),
      title: '소셜 미디어 홍보 일정 안내',
      content: '11월 29일 오전 10시에 소셜 미디어 홍보 활동이 예정되어 있습니다.',
      noticeId: 14,
    },
  },
  {
    todo: {
      scheduleId: 15,
      title: '기술 워크숍',
      startDateTime: new Date('2024-11-30T13:00:00'),
      endDateTime: new Date('2024-11-30T16:00:00'),
      scheduleSourceTypeCd: 'PERSONAL', // 개인 일정
      scheduleStatusTypeCd: 'TODO', // 해야 할 일
      user: {
        userId: 115,
        email: 'olivia@example.com',
        name: 'Olivia Turner',
        profileImageUrl: 'https://example.com/images/olivia.jpg',
      },
    },
    announcement: {
      user: {
        userId: 115,
        email: 'olivia@example.com',
        name: 'Olivia Turner',
        profileImageUrl: 'https://example.com/images/olivia.jpg',
      },
      createdAt: new Date('2024-11-24T13:00:00'),
      title: '기술 워크숍 안내',
      content:
        '11월 30일 오후 1시에 기술 워크숍이 시작됩니다. 참여를 원하시는 분들은 사전 등록 바랍니다.',
      noticeId: 15,
    },
  },
]
