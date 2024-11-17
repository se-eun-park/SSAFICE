import { AddIcon } from '@/assets/svg'
import type { MattermostChannel } from '@/features/ManageMembersTab'

type ManageMembersTabContentProps = {
  channel: MattermostChannel
}
export const ManageMembersTabContent = ({ channel }: ManageMembersTabContentProps) => {
  return (
    <div
      className='
      flex flex-col
      w-full h-full
    '
    >
      {/* 상단 버튼 영역 */}
      <div
        className='
        flex justify-end
        py-spacing-20 px-spacing-24
      '
      >
        <button
          className='
          flex gap-spacing-2 items-center
          py-spacing-8 px-spacing-16
          bg-color-bg-interactive-primary rounded-radius-4
          hover:bg-color-bg-interactive-primary-hover
          active:bg-color-bg-interactive-primary-press
        '
        >
          <div className='w-spacing-12 h-spacing-12'>
            <AddIcon color='#FFFFFF' />
          </div>
          <div
            className='
            text-color-text-interactive-inverse body-md-medium
          '
          >
            관리자 추가
          </div>
        </button>
      </div>
    </div>
  )
}
