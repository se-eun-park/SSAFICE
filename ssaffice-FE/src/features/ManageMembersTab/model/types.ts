export type MattermostChannel = {
  channelId: number
  name: string
}

export type MattermostTeam = {
  teamId: number
  name: string
  channels: MattermostChannel[]
}

export type SsafyUserRoles = {
  // 여기부터 작성
  roleId?: string
  description: string
}

export type SsafyUser = {
  // 유저 정보 조회 API 응답 그대로 작성
  userId: number
  email: string
  name: string
  profileImage?: string
  cohortNum?: number // 프로의 경우에는 없음
  regionCd: string // 지역
  classNum?: number // 반
  trackCd?: string // 트랙
  curriculumCd?: string // 공통/특화/자율
  disabledYn?: boolean // 비활성화 유저인가
  roles: SsafyUserRoles
}

// MARK: DATAS
export const dummySsafyUsers: SsafyUser[] = [
  {
    userId: 1,
    email: 'user1@ssafy.com',
    name: 'John Doe',
    regionCd: 'Seoul',
    cohortNum: 11,
    classNum: 3,
    trackCd: 'SEOUL',
    roles: {
      description: '학생',
    },
  },
  {
    userId: 2,
    email: 'user2@ssafy.com',
    name: 'Jane Smith',
    regionCd: 'Busan',
    cohortNum: 12,
    classNum: 1,
    trackCd: 'DAEGU',
    roles: {
      description: '학생',
    },
  },
  {
    userId: 3,
    email: 'user3@ssafy.com',
    name: 'Michael Johnson',
    regionCd: 'Incheon',
    cohortNum: 11,
    classNum: 2,
    trackCd: 'BU_UL_GYEONG',
    roles: {
      description: '프로',
    },
  },
  {
    userId: 4,
    email: 'user4@ssafy.com',
    name: 'Emily Davis',
    regionCd: 'Seoul',
    cohortNum: 12,
    classNum: 5,
    trackCd: 'GWANGJU',
    roles: {
      description: '학생',
    },
  },
  {
    userId: 5,
    email: 'user5@ssafy.com',
    name: 'David Lee',
    regionCd: 'Daejeon',
    cohortNum: 11,
    classNum: 6,
    trackCd: 'DAEJEON',
    roles: {
      description: '프로',
    },
  },
  {
    userId: 6,
    email: 'user6@ssafy.com',
    name: 'Sophia Kim',
    regionCd: 'Seoul',
    cohortNum: 12,
    classNum: 4,
    trackCd: 'SEOUL',
    roles: {
      description: '학생',
    },
  },
  {
    userId: 7,
    email: 'user7@ssafy.com',
    name: 'James Brown',
    regionCd: 'Gwangju',
    cohortNum: 11,
    classNum: 7,
    trackCd: 'DAEGU',
    roles: {
      description: '학생',
    },
  },
  {
    userId: 8,
    email: 'user8@ssafy.com',
    name: 'Olivia Wilson',
    regionCd: 'Ulsan',
    cohortNum: 12,
    classNum: 8,
    trackCd: 'BU_UL_GYEONG',
    roles: {
      description: '프로',
    },
  },
  {
    userId: 9,
    email: 'user9@ssafy.com',
    name: 'Liam Taylor',
    regionCd: 'Busan',
    cohortNum: 11,
    classNum: 9,
    trackCd: 'GWANGJU',
    roles: {
      description: '프로',
    },
  },
  {
    userId: 10,
    email: 'user10@ssafy.com',
    name: 'Isabella Moore',
    regionCd: 'Seoul',
    cohortNum: 12,
    classNum: 5,
    trackCd: 'DAEJEON',
    roles: {
      description: '학생',
    },
  },
  {
    userId: 11,
    email: 'user11@ssafy.com',
    name: 'Ethan Anderson',
    regionCd: 'Incheon',
    cohortNum: 11,
    classNum: 2,
    trackCd: 'SEOUL',
    roles: {
      description: '학생',
    },
  },
  {
    userId: 12,
    email: 'user12@ssafy.com',
    name: 'Ava Thomas',
    regionCd: 'Daejeon',
    cohortNum: 12,
    classNum: 1,
    trackCd: 'DAEGU',
    roles: {
      description: '프로',
    },
  },
  {
    userId: 13,
    email: 'user13@ssafy.com',
    name: 'Mason Jackson',
    regionCd: 'Seoul',
    cohortNum: 11,
    classNum: 6,
    trackCd: 'BU_UL_GYEONG',
    roles: {
      description: '학생',
    },
  },
  {
    userId: 14,
    email: 'user14@ssafy.com',
    name: 'Charlotte Harris',
    regionCd: 'Gwangju',
    cohortNum: 12,
    classNum: 7,
    trackCd: 'GWANGJU',
    roles: {
      description: '프로',
    },
  },
  {
    userId: 15,
    email: 'user15@ssafy.com',
    name: 'Amelia Clark',
    regionCd: 'Incheon',
    cohortNum: 11,
    classNum: 8,
    trackCd: 'DAEJEON',
    roles: {
      description: '학생',
    },
  },
  {
    userId: 16,
    email: 'user16@ssafy.com',
    name: 'Benjamin Lewis',
    regionCd: 'Ulsan',
    cohortNum: 12,
    classNum: 9,
    trackCd: 'SEOUL',
    roles: {
      description: '프로',
    },
  },
  {
    userId: 17,
    email: 'user17@ssafy.com',
    name: 'Harper Young',
    regionCd: 'Busan',
    cohortNum: 11,
    classNum: 4,
    trackCd: 'DAEGU',
    roles: {
      description: '학생',
    },
  },
  {
    userId: 18,
    email: 'user18@ssafy.com',
    name: 'Jack Walker',
    regionCd: 'Seoul',
    cohortNum: 12,
    classNum: 3,
    trackCd: 'BU_UL_GYEONG',
    roles: {
      description: '프로',
    },
  },
  {
    userId: 19,
    email: 'user19@ssafy.com',
    name: 'Lily Perez',
    regionCd: 'Gwangju',
    cohortNum: 11,
    classNum: 5,
    trackCd: 'GWANGJU',
    roles: {
      description: '학생',
    },
  },
  {
    userId: 20,
    email: 'user20@ssafy.com',
    name: 'Lucas Hall',
    regionCd: 'Daejeon',
    cohortNum: 12,
    classNum: 2,
    trackCd: 'DAEJEON',
    roles: {
      description: '학생',
    },
  },
  {
    userId: 21,
    email: 'user21@ssafy.com',
    name: 'Mila Allen',
    regionCd: 'Seoul',
    cohortNum: 11,
    classNum: 6,
    trackCd: 'SEOUL',
    roles: {
      description: '프로',
    },
  },
  {
    userId: 22,
    email: 'user22@ssafy.com',
    name: 'Oliver Scott',
    regionCd: 'Busan',
    cohortNum: 12,
    classNum: 8,
    trackCd: 'DAEGU',
    roles: {
      description: '학생',
    },
  },
  {
    userId: 23,
    email: 'user23@ssafy.com',
    name: 'Emma King',
    regionCd: 'Incheon',
    cohortNum: 11,
    classNum: 7,
    trackCd: 'BU_UL_GYEONG',
    roles: {
      description: '학생',
    },
  },
  {
    userId: 24,
    email: 'user24@ssafy.com',
    name: 'Henry Wright',
    regionCd: 'Seoul',
    cohortNum: 12,
    classNum: 9,
    trackCd: 'DAEJEON',
    roles: {
      description: '프로',
    },
  },
  {
    userId: 25,
    email: 'user25@ssafy.com',
    name: 'Evelyn Lee',
    regionCd: 'Gwangju',
    cohortNum: 11,
    classNum: 2,
    trackCd: 'SEOUL',
    roles: {
      description: '학생',
    },
  },
  {
    userId: 26,
    email: 'user26@ssafy.com',
    name: 'James Harris',
    regionCd: 'Ulsan',
    cohortNum: 12,
    classNum: 3,
    trackCd: 'DAEGU',
    roles: {
      description: '학생',
    },
  },
  {
    userId: 27,
    email: 'user27@ssafy.com',
    name: 'Maddison Clark',
    regionCd: 'Daejeon',
    cohortNum: 11,
    classNum: 6,
    trackCd: 'BU_UL_GYEONG',
    roles: {
      description: '프로',
    },
  },
  {
    userId: 28,
    email: 'user28@ssafy.com',
    name: 'Alexander Turner',
    regionCd: 'Seoul',
    cohortNum: 12,
    classNum: 1,
    trackCd: 'GWANGJU',
    roles: {
      description: '학생',
    },
  },
  {
    userId: 29,
    email: 'user29@ssafy.com',
    name: 'Zoe Carter',
    regionCd: 'Busan',
    cohortNum: 11,
    classNum: 7,
    trackCd: 'DAEJEON',
    roles: {
      description: '프로',
    },
  },
  {
    userId: 30,
    email: 'user30@ssafy.com',
    name: 'Leo Perez',
    regionCd: 'Incheon',
    cohortNum: 12,
    classNum: 4,
    trackCd: 'SEOUL',
    roles: {
      description: '학생',
    },
  },
  {
    userId: 31,
    email: 'user31@ssafy.com',
    name: 'Chloe Evans',
    regionCd: 'Seoul',
    cohortNum: 11,
    classNum: 9,
    trackCd: 'DAEGU',
    roles: {
      description: '프로',
    },
  },
  {
    userId: 32,
    email: 'user32@ssafy.com',
    name: 'Sebastian Edwards',
    regionCd: 'Gwangju',
    cohortNum: 12,
    classNum: 5,
    trackCd: 'BU_UL_GYEONG',
    roles: {
      description: '학생',
    },
  },
  {
    userId: 33,
    email: 'user33@ssafy.com',
    name: 'Luna Collins',
    regionCd: 'Ulsan',
    cohortNum: 11,
    classNum: 2,
    trackCd: 'SEOUL',
    roles: {
      description: '프로',
    },
  },
  {
    userId: 34,
    email: 'user34@ssafy.com',
    name: 'Aiden Morris',
    regionCd: 'Daejeon',
    cohortNum: 12,
    classNum: 8,
    trackCd: 'DAEJEON',
    roles: {
      description: '학생',
    },
  },
  {
    userId: 35,
    email: 'user35@ssafy.com',
    name: 'Madeline Murphy',
    regionCd: 'Seoul',
    cohortNum: 11,
    classNum: 4,
    trackCd: 'GWANGJU',
    roles: {
      description: '학생',
    },
  },
]

export const dummyMattermostTeams: MattermostTeam[] = [
  {
    teamId: 123456789,
    name: '11기 서울 8반',
    channels: [
      {
        channelId: 22022021,
        name: '공지사항',
      },
      {
        channelId: 33233212,
        name: '소통',
      },
      {
        channelId: 44545454,
        name: '강의 공유',
      },
    ],
  },
  {
    teamId: 456789456789,
    name: '11기 공통 서울 1반',
    channels: [
      {
        channelId: 456789123456,
        name: '공지사항',
      },
      {
        channelId: 789456123,
        name: '잡담',
      },
      {
        channelId: 784545,
        name: 'A108',
      },
      {
        channelId: 456789456123,
        name: 'A108(비공식)',
      },
    ],
  },
  {
    teamId: 789456789456,
    name: '11기 특화 서울 4반',
    channels: [
      {
        channelId: 78945,
        name: '공지사항',
      },
      {
        channelId: 456789,
        name: '서울 4반 이벤트',
      },
      {
        channelId: 7878,
        name: 'A401',
      },
      {
        channelId: 45645789,
        name: 'A401(비공식)',
      },
    ],
  },
  {
    teamId: 456789456,
    name: '11기 자율 서울 6반',
    channels: [
      {
        channelId: 1,
        name: '공지사항',
      },
      {
        channelId: 2,
        name: '잡담',
      },
      {
        channelId: 3,
        name: 'A605',
      },
      {
        channelId: 4,
        name: 'A605(은밀한 대화)',
      },
      {
        channelId: 5,
        name: 'A605_FE',
      },
      {
        channelId: 6,
        name: '강의 교보재 신청',
      },
    ],
  },
]
