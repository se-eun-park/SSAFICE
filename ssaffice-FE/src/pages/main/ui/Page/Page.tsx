import { SummaryTab } from '@/widgets/summaryTab'
import { AnnouncementTab } from '@/widgets/announcementTab'
import { TodoTab } from '@/widgets/todoTab'
import { useIsTabOpenStore, useIsAnimationStore } from '@/shared/model'

export const MainPage = () => {
  const isTabOpen = useIsTabOpenStore()
  const isAnimation = useIsAnimationStore()

  return (
    <main className='flex flex-col w-full min-h-[calc(100vh+190px)] gap-y-spacing-32 pt-spacing-40'>
      <SummaryTab />

      <div
        className={`flex gap-x-spacing-40 px-spacing-32 ${isAnimation ? ' animate-gapShrink' : ''}`}
      >
        {isTabOpen && <AnnouncementTab />}
        <TodoTab />
      </div>
    </main>
  )
}
