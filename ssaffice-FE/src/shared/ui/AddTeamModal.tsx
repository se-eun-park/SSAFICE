import { useState } from 'react'
import { useQuery } from '@tanstack/react-query'
import { instance } from '../api'

type AddTeamModalProps = {
  setIsOpen: (isOpen: boolean) => void
  setChannelId: (channelId: string) => void
  setChannelName: (channelName: string) => void
  setNoticeType: (noticeType: string) => void
}

type getMyChannelList = {
  channelId: string
  channelName: string
  mmTeamId: string
  mmTeamName: string
}

export const AddTeamModal = ({
  setIsOpen,
  setChannelId,
  setChannelName,
  setNoticeType,
}: AddTeamModalProps) => {
  const [chId, setChId] = useState('')
  const [chName, setChName] = useState('')
  const [ntType, setNtType] = useState('')

  const { data: channelList } = useQuery({
    queryKey: ['myMattermostInfo'],
    queryFn: async () => {
      const response = await instance.get<getMyChannelList[]>('/api/channels/my')
      return response.data
    },
  })

  const getNoticeType = (name: string) => {
    if (name.replace(/\s+/g, '').includes('공지전용')) {
      return true
    }

    return false
  }

  const handleOnClickChannel = (id: string, name: string, mmName: string) => {
    setChId(id)
    setChName(`${name} • ${mmName}`)

    if (getNoticeType(mmName)) {
      setNtType('GLOBAL')
    } else {
      setNtType('TEAM')
    }

    return
  }

  const handleOnClickSubmit = () => {
    setChannelId(chId)
    setChannelName(chName)
    setNoticeType(ntType)
    setIsOpen(false)
  }

  return (
    <div className='z-50 absolute right-2.5 top-12 flex flex-col gap-y-spacing-24 border w-[480px] h-fit bg-color-bg-primary rounded-radius-8 px-spacing-32 py-spacing-16 border-color-border-tertiary'>
      <h1 className='w-full text-center heading-desktop-sm text-color-text-primary'>채널 추가</h1>

      <div className='flex flex-col max-h-[416px] overflow-y-auto border divide-y rounded-radius-4 border-color-border-primary divide-color-border-secondary'>
        {channelList?.map((channel) => (
          <button
            key={channel.channelId}
            onClick={() =>
              handleOnClickChannel(channel.channelId, channel.channelName, channel.mmTeamName)
            }
            className={`flex items-center gap-x-spacing-12 px-spacing-16 py-spacing-12 ${chId === channel.channelId ? 'bg-color-bg-interactive-secondary-press' : 'hover:bg-color-bg-interactive-secondary-hover'}`}
          >
            <div
              className={`flex items-center justify-center min-w-8 aspect-square rounded-radius-circle ${getNoticeType(channel.mmTeamName) ? 'bg-color-bg-info' : 'bg-color-bg-warning'}`}
            >
              <p className='body-xs-medium text-color-text-interactive-inverse'>
                {getNoticeType(channel.mmTeamName) ? '전체' : '팀'}
              </p>
            </div>

            <div className='flex items-center gap-x-spacing-6 max-w-[340px]'>
              <p className='body-md-medium text-color-text-primary min-w-max'>
                {channel.channelName}
              </p>
              <p className='body-xs-medium text-color-text-disabled'>•</p>
              <p className='truncate body-xs-medium text-color-text-disabled'>
                {channel.mmTeamName}
              </p>
            </div>
          </button>
        ))}
      </div>

      <button
        onClick={handleOnClickSubmit}
        disabled={!chId}
        className='w-full h-fit bg-color-bg-interactive-primary py-spacing-12 rounded-radius-8 body-lg-medium text-color-text-interactive-inverse disabled:bg-color-bg-disabled'
      >
        추가하기
      </button>
    </div>
  )
}
