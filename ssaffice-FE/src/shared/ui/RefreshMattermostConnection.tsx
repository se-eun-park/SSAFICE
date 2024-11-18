import { RefreshIcon } from '@/assets/svg'
import { instance } from '@/shared/model'
import { useQuery } from '@tanstack/react-query'
import { useQueryClient } from '@tanstack/react-query'

const formatDateTime = (dateTimeString: string) => {
  const date = new Date(dateTimeString)
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hours = date.getHours()
  const minutes = date.getMinutes()

  return `${month}월 ${day}일 ${hours}:${minutes.toString().padStart(2, '0')}`
}

export const RefreshMattermostConnection = () => {
  const queryClient = useQueryClient()
  const fetchMattermostConnection = async () => {
    const response = await instance.get('/api/mm/channels')
    return response.data
  }

  const { data: user } = useQuery({
    queryKey: ['userData'],
    queryFn: async () => {
      const response = await instance.get('/api/users/me')
      return response.data
    },
  })

  const { refetch } = useQuery({
    queryKey: ['mattermostConnection'],
    queryFn: fetchMattermostConnection,
    enabled: false, // 초기에는 자동으로 실행되지 않도록 설정
  })

  const handleButtonClick = async () => {
    await refetch()
    queryClient.invalidateQueries({ queryKey: ['unscheduled'] })
    queryClient.invalidateQueries({ queryKey: ['announcements'] })
    queryClient.invalidateQueries({ queryKey: ['userData'] })
  }

  return (
    <div
      className='
        flex gap-spacing-8 items-center
    '
    >
      <button className='flex gap-spacing-8 items-center' onClick={handleButtonClick}>
        <div
          className='
        w-spacing-12 h-spacing-12
      '
        >
          <RefreshIcon />
        </div>

        <div
          className='
        flex justify-start
        w-[160px]
        body-md-medium text-color-text-primary
      '
        >
          MM 팀/채널 새로고침
        </div>
      </button>
      <div
        className='
        flex justify-end
        text-color-text-tertiary body-sm-medium
      '
      >
        {formatDateTime(user?.recentMmChannelSyncTime)}
      </div>
    </div>
  )
}
