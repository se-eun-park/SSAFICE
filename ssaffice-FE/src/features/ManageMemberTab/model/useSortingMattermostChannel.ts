import { ChannelSummary } from '@/features/todoTab'
import { MattermostTeam } from './types'

export const useSortingMattermostChannel = (channel: ChannelSummary[]): MattermostTeam[] => {
  const sorted: Record<string, MattermostTeam> = {}
  console.log(channel)

  if (channel === undefined || channel.length === 0)
    return [
      {
        teamId: 'temp_teamId',
        name: 'temp_teamName',
        channels: [
          {
            channelId: 'temp_channel1',
            name: 'temp_channel1',
          },
          {
            channelId: 'temp_channel2',
            name: 'temp_channel2',
          },
          {
            channelId: 'temp_channel3',
            name: 'temp_channel3',
          },
        ],
      },
    ] // 받아온 리스트가 없는 경우. 지금은 임시로 dummy data 넣엇슴다

  channel.forEach((each) => {
    if (!sorted[each.mmTeamId])
      sorted[each.mmTeamId] = {
        teamId: each.mmTeamId,
        name: each.mmTeamName,
        channels: [
          {
            channelId: each.channelId,
            name: each.channelName,
          },
        ],
      }
    else
      sorted[each.mmTeamId].channels.push({
        channelId: each.channelId,
        name: each.channelName,
      })
  })

  const result: MattermostTeam[] = []
  Object.keys(sorted).forEach((each) => {
    result.push(sorted[each])
  })

  return result
}
