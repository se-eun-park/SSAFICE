export type createUser = {
  userId: number
  email?: string // 더미데이터 생성 시 번거로움 때문에 선택적 파라메터 지정
  name: string
  profileImageUrl?: string
} // 임시 타입입니다, user 타입이 따로 생기면 추후 교체해 주세요

export type AnnouncementItemDisplayType = {
  // API 응답 그대로 매칭
  user: createUser // 작성자
  createdAt: Date // display 시 시간 formatting하는 유틸함수 붙여서 쓰는 걸로
  title: string
  content: string
  startDateTime?: Date
  endDateTime?: Date
  noticeId: number
  isEssential?: boolean // 나중에 essential parameter로 바꾸던지 (일단 지금은 선택)
  taskTypeCd?: string
}

export type AnnouncementListDisplayType = Record<string, AnnouncementItemDisplayType[]>
// 날짜로 묶은 공지 items

// API 연결 후 삭제해 주세요
export const dummyAnnouncements: AnnouncementItemDisplayType[] = [
  {
    user: { userId: 1, name: 'John Doe' },
    createdAt: new Date('2024-09-12T08:45:00'),
    title: '공지 제목 1',
    content: '공지 내용 1번입니다. 이 공지는 주로 시스템 유지보수와 관련된 내용입니다.',
    noticeId: 1,
  },
  {
    user: { userId: 2, name: 'Jane Smith' },
    createdAt: new Date('2024-09-12T08:45:00'),
    title: '공지 제목 2',
    content: '공지 내용 2번입니다. 시스템 업데이트와 관련된 공지입니다.',
    noticeId: 2,
  },
  {
    user: { userId: 3, name: 'Alice Johnson' },
    createdAt: new Date('2024-09-12T08:45:00'),
    title: '공지 제목 3',
    content: '공지 내용 3번입니다. 중요 공지 사항입니다.',
    noticeId: 3,
  },
  {
    user: { userId: 4, name: 'Bob Brown' },
    createdAt: new Date('2024-09-18T11:00:00'),
    title: '공지 제목 4',
    content: '공지 내용 4번입니다. 서비스 점검 일정에 대한 내용입니다.',
    noticeId: 4,
  },
  {
    user: { userId: 5, name: 'Charlie Davis' },
    createdAt: new Date('2024-09-29T13:00:00'),
    title: '공지 제목 5',
    content: '공지 내용 5번입니다. 긴급 보안 패치 안내입니다.',
    noticeId: 5,
  },
  {
    user: { userId: 1, name: 'John Doe' },
    createdAt: new Date('2024-09-29T13:00:00'),
    title: '공지 제목 6',
    content: '공지 내용 6번입니다. 시스템 점검 및 업그레이드 작업 안내입니다.',
    noticeId: 6,
  },
  {
    user: { userId: 2, name: 'Jane Smith' },
    createdAt: new Date('2024-09-29T13:00:00'),
    title: '공지 제목 7',
    content: '공지 내용 7번입니다. 중요 업데이트 공지입니다.',
    noticeId: 7,
  },
  {
    user: { userId: 3, name: 'Alice Johnson' },
    createdAt: new Date('2024-10-10T16:15:00'),
    title: '공지 제목 8',
    content: '공지 내용 8번입니다. 사용자 인터페이스 변경사항에 대한 안내입니다.',
    noticeId: 8,
  },
  {
    user: { userId: 4, name: 'Bob Brown' },
    createdAt: new Date('2024-08-30T12:45:00'),
    title: '공지 제목 9',
    content: '공지 내용 9번입니다. 새로운 기능에 대한 발표입니다.',
    noticeId: 9,
  },
  {
    user: { userId: 5, name: 'Charlie Davis' },
    createdAt: new Date('2024-08-30T12:42:00'),
    title: '공지 제목 10',
    content: '공지 내용 10번입니다. 내부 보안 점검 일정 안내입니다.',
    noticeId: 10,
  },
  {
    user: { userId: 1, name: 'John Doe' },
    createdAt: new Date('2024-10-03T17:30:00'),
    title: '공지 제목 11',
    content: '공지 내용 11번입니다. 데이터 백업 작업 공지입니다.',
    noticeId: 11,
  },
  {
    user: { userId: 2, name: 'Jane Smith' },
    createdAt: new Date('2024-09-25T19:00:00'),
    title: '공지 제목 12',
    content: '공지 내용 12번입니다. 사용자 계정 관리 관련 공지입니다.',
    noticeId: 12,
  },
  {
    user: { userId: 3, name: 'Alice Johnson' },
    createdAt: new Date('2024-10-07T10:00:00'),
    title: '공지 제목 13',
    content: '공지 내용 13번입니다. 시스템 업데이트 내용입니다.',
    noticeId: 13,
  },
  {
    user: { userId: 4, name: 'Bob Brown' },
    createdAt: new Date('2024-08-15T14:00:00'),
    title: '공지 제목 14',
    content: '공지 내용 14번입니다. 긴급 시스템 점검 안내입니다.',
    noticeId: 14,
  },
  {
    user: { userId: 5, name: 'Charlie Davis' },
    createdAt: new Date('2024-09-10T13:30:00'),
    title: '공지 제목 15',
    content: '공지 내용 15번입니다. 서비스 중단 안내입니다.',
    noticeId: 15,
  },
  {
    user: { userId: 1, name: 'John Doe' },
    createdAt: new Date('2024-09-20T12:00:00'),
    title: '공지 제목 16',
    content: '공지 내용 16번입니다. 시스템 유지보수 안내입니다.',
    noticeId: 16,
  },
  {
    user: { userId: 2, name: 'Jane Smith' },
    createdAt: new Date('2024-10-12T10:00:00'),
    title: '공지 제목 17',
    content: '공지 내용 17번입니다. 신규 기능 런칭 공지입니다.',
    noticeId: 17,
  },
  {
    user: { userId: 3, name: 'Alice Johnson' },
    createdAt: new Date('2024-08-25T16:30:00'),
    title: '공지 제목 18',
    content: '공지 내용 18번입니다. 성능 개선 작업 공지입니다.',
    noticeId: 18,
  },
  {
    user: { userId: 4, name: 'Bob Brown' },
    createdAt: new Date('2024-09-17T08:00:00'),
    title: '공지 제목 19',
    content: '공지 내용 19번입니다. 새로운 정책 발표 공지입니다.',
    noticeId: 19,
  },
  {
    user: { userId: 5, name: 'Charlie Davis' },
    createdAt: new Date('2024-10-02T11:30:00'),
    title: '공지 제목 20',
    content: '공지 내용 20번입니다. 서버 점검 공지입니다.',
    noticeId: 20,
  },
  {
    user: { userId: 1, name: 'John Doe' },
    createdAt: new Date('2024-09-28T09:15:00'),
    title: '공지 제목 21',
    content: '공지 내용 21번입니다. 신규 서비스 출시 공지입니다.',
    noticeId: 21,
  },
  {
    user: { userId: 2, name: 'Jane Smith' },
    createdAt: new Date('2024-08-20T08:30:00'),
    title: '공지 제목 22',
    content: '공지 내용 22번입니다. 긴급 업데이트 안내입니다.',
    noticeId: 22,
  },
  {
    user: { userId: 3, name: 'Alice Johnson' },
    createdAt: new Date('2024-10-04T07:45:00'),
    title: '공지 제목 23',
    content: '공지 내용 23번입니다. 보안 패치 관련 공지입니다.',
    noticeId: 23,
  },
  {
    user: { userId: 4, name: 'Bob Brown' },
    createdAt: new Date('2024-09-16T13:00:00'),
    title: '공지 제목 24',
    content: '공지 내용 24번입니다. 사용자 인터페이스 변경 안내입니다.',
    noticeId: 24,
  },
  {
    user: { userId: 5, name: 'Charlie Davis' },
    createdAt: new Date('2024-10-09T12:00:00'),
    title: '공지 제목 25',
    content: '공지 내용 25번입니다. 서버 점검 및 서비스 일시 중단 공지입니다.',
    noticeId: 25,
  },
  {
    user: { userId: 5, name: 'Charlie Davis' },
    createdAt: new Date('2024-10-09T12:01:00'),
    title: '공지 제목 26',
    content: '공지 내용 26번입니다. 서버 점검 및 서비스 일시 중단 공지입니다.',
    noticeId: 26,
  },
  {
    user: { userId: 5, name: 'Charlie Davis' },
    createdAt: new Date('2024-10-09T11:00:00'),
    title: '공지 제목 27',
    content:
      '공지 내용 27번입니다. 서버 점검 및 서비스 일시 중단 공지입니다.공지 내용 27번입니다. 서버 점검 및 서비스 일시 중단 공지입니다.공지 내용 27번입니다. 서버 점검 및 서비스 일시 중단 공지입니다.공지 내용 27번입니다. 서버 점검 및 서비스 일시 중단 공지입니다.공지 내용 27번입니다. 서버 점검 및 서비스 일시 중단 공지입니다.공지 내용 27번입니다. 서버 점검 및 서비스 일시 중단 공지입니다.공지 내용 27번입니다. 서버 점검 및 서비스 일시 중단 공지입니다.공지 내용 27번입니다. 서버 점검 및 서비스 일시 중단 공지입니다.공지 내용 27번입니다. 서버 점검 및 서비스 일시 중단 공지입니다.공지 내용 27번입니다. 서버 점검 및 서비스 일시 중단 공지입니다.공지 내용 27번입니다. 서버 점검 및 서비스 일시 중단 공지입니다.공지 내용 27번입니다. 서버 점검 및 서비스 일시 중단 공지입니다.공지 내용 27번입니다. 서버 점검 및 서비스 일시 중단 공지입니다.공지 내용 27번입니다. 서버 점검 및 서비스 일시 중단 공지입니다.공지 내용 27번입니다. 서버 점검 및 서비스 일시 중단 공지입니다.공지 내용 27번입니다. 서버 점검 및 서비스 일시 중단 공지입니다.공지 내용 27번입니다. 서버 점검 및 서비스 일시 중단 공지입니다.',
    noticeId: 27,
  },
  {
    user: { userId: 5, name: 'Charlie Davis' },
    createdAt: new Date('2024-10-09T14:00:00'),
    title: '공지 제목 28',
    content: '공지 내용 28번입니다. 서버 점검 및 서비스 일시 중단 공지입니다.',
    noticeId: 28,
  },
  {
    user: { userId: 5, name: 'Charlie Davis' },
    createdAt: new Date('2024-10-09T13:00:00'),
    title: '공지 제목 29',
    content: '공지 내용 29번입니다. 서버 점검 및 서비스 일시 중단 공지입니다.',
    noticeId: 29,
  },
  {
    user: { userId: 4, name: 'Bob Brown' },
    createdAt: new Date('2024-10-09T14:00:00'),
    title: '공지 제목 14',
    content: '공지 내용 14번입니다. 긴급 시스템 점검 안내입니다.',
    noticeId: 30,
  },
  {
    user: { userId: 4, name: 'Bob Brown' },
    createdAt: new Date('2024-10-09T14:00:00'),
    title: '공지 제목 14',
    content: '공지 내용 14번입니다. 긴급 시스템 점검 안내입니다.',
    noticeId: 31,
  },
  {
    user: { userId: 4, name: 'Bob Brown' },
    createdAt: new Date('2024-10-09T14:00:00'),
    title: '공지 제목 14',
    content: '공지 내용 14번입니다. 긴급 시스템 점검 안내입니다.',
    noticeId: 32,
  },
  {
    user: { userId: 4, name: 'Bob Brown' },
    createdAt: new Date('2024-10-09T14:00:00'),
    title: '공지 제목 14',
    content: '공지 내용 14번입니다. 긴급 시스템 점검 안내입니다.',
    noticeId: 33,
  },
]
