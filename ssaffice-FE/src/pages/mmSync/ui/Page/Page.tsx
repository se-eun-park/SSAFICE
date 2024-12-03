import { SyncForm } from '@/features/session/mmSync'

export const MatterMostSyncPage = () => {
  return (
    <div className='flex flex-col w-full h-[calc(100vh-108px)] justify-center items-center'>
      <div className='flex flex-col items-center w-[475px] gap-y-spacing-64'>
        <div className='flex flex-col w-full gap-y-spacing-48'>
          <h1 className='self-center text-color-text-primary heading-desktop-3xl w-fit px-spacing-64'>
            처음 방문이신가요
          </h1>
          <p className='w-full text-center body-lg-medium text-color-text-tertiary'>
            Mattermost 연동을 통해
            <br />
            SSAFICE에서 Mattermost 공지사항도 함께 확인해보세요 !
          </p>
        </div>
        <SyncForm />
      </div>
    </div>
  )
}
