import { DownArrowIcon } from '@/assets/svg'

export const TeamSelectDropdown = () => {
  return (
    <div
      className='
        flex justify-center items-center gap-spacing-8
        w-fit p-spacing-20
    '
      // onClick 시 드롭다운 작동하는 로직 넣어주세요
    >
      <div
        className='
            text-color-text-primary heading-desktop-lg
        '
      >
        팀 선택
      </div>
      <div
        className='
        flex justify-center items-center
        w-spacing-24 h-spacing-24
        border border-color-border-primary
        rounded-radius-4
      '
      >
        <DownArrowIcon className='w-13' />
      </div>
    </div>
  )
}
