export type MattermostChannel = {
  channelId: number
  name: string
}

export type MattermostTeam = {
  teamId: number
  name: string
  channels: MattermostChannel[]
}

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
