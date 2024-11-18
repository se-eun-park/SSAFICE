import { RefreshIcon } from '@/assets/svg'
import { useRefreshMattermostConnection } from '@/shared/model'

export const RefreshMattermostConnection = () => {
  const { latestRefreshTime, refreshMattermostConnect } = useRefreshMattermostConnection()
  return (
    <div
      className='
        flex gap-spacing-8 items-center
    '
    >
      <button className='flex gap-spacing-8 items-center' onClick={refreshMattermostConnect}>
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
        {latestRefreshTime}
      </div>
    </div>
  )
}
