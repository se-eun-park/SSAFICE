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
      {/* refresh SVG(RefreshIcon)로 바꿔주세요. 데탑에서 svgr 작동 안 돼서 public/svg에 파일만 넣어둠 ㅜㅜ */}
      <button
        className='
        w-spacing-12 h-spacing-12
      '
        onClick={refreshMattermostConnect}
      >
        <RefreshIcon />
      </button>

      <div
        className='
        flex justify-start
        w-[160px]
        body-md-medium text-color-text-primary
      '
      >
        MM 팀/채널 새로고침
      </div>
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
