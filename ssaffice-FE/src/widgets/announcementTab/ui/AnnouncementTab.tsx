import {
  useIsTabOpenStore,
  useSetIsTabOpenStore,
  useIsAnimationStore,
  useSetIsAnimationStore,
} from '@/shared/model'
import { SearchBar, TabLayout } from '@/shared/ui'
import { FastLeftArrowIcon } from '@/assets/svg'

export const AnnouncementTab = () => {
  const isTabOpen = useIsTabOpenStore()
  const setIsTabOpen = useSetIsTabOpenStore()

  const isAnimation = useIsAnimationStore()
  const setIsAnimation = useSetIsAnimationStore()

  const handleOnClickClose = () => {
    setIsAnimation(true)

    setTimeout(() => {
      setIsTabOpen(isTabOpen)
    }, 400)
  }

  return (
    <TabLayout animation={`${isAnimation ? 'animate-slideShrink' : 'animate-slideExpand'}`}>
      <TabLayout.Header animation={`${isAnimation ? 'animate-fadeOut' : 'animate-fadeIn'}`}>
        <h1>전체 공지</h1>
        <button onClick={handleOnClickClose}>
          <FastLeftArrowIcon className='w-6' />
        </button>
      </TabLayout.Header>

      <TabLayout.Add animation={`${isAnimation ? 'animate-fadeOut' : 'animate-fadeIn'}`}>
        <SearchBar />
      </TabLayout.Add>

      <TabLayout.Content animation={`${isAnimation ? 'animate-fadeOut' : 'animate-fadeIn'}`}>
        <div className='w-full h-full rounded-radius-8 bg-color-bg-tertiary'>
          나중엔 공지 리스트 컴포넌트가 들어감
        </div>
      </TabLayout.Content>
    </TabLayout>
  )
}
