import { TeamSelectDropdown } from '@/features/pro'
import { RefreshMattermostConnection } from '@/shared/ui'

export const ManageMembersTab = () => {
  return (
    <>
      <div
        className='
          flex gap-spacing-10
          w-full h-[66px]
        '
      >
        {/* 팀 선택 & MM refresh 영역 */}
        <TeamSelectDropdown />
        <RefreshMattermostConnection />
      </div>

      <div
        className='
          flex flex-col justify-center items-center
          px-[405px] py-[478px]
          text-color-text-disabled body-lg-medium
        '
      >
        {/* 
          이 영역은 나중에 screen에 맞추어서 반응형으로 잡고 
          center 정렬하면 좋을 것 같음 / h-65vh 잡으면 딱 예쁘게 나옵니다
        */}
        팀과 채널을 먼저 선택해주세요.
      </div>
    </>
  )
}
