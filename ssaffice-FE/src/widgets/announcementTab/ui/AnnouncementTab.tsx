import { HoverButton } from '@/features/todoTab'
import {
  useIsTabOpenStore,
  useSetIsTabOpenStore,
  useIsAnimationStore,
  useSetIsAnimationStore,
  useIsFirstRenderStore,
  useSetIsFirstRenderStore,
} from '@/shared/model'
import { SearchBar, TabLayout } from '@/shared/ui'
import { FastLeftArrowIcon } from '@/assets/svg'

export const AnnouncementTab = () => {
  // store
  const isTabOpen = useIsTabOpenStore()
  const setIsTabOpen = useSetIsTabOpenStore()

  const isAnimation = useIsAnimationStore()
  const setIsAnimation = useSetIsAnimationStore()

  const isFirstRender = useIsFirstRenderStore()
  const setIsFirstRender = useSetIsFirstRenderStore()

  // animation
  const tabAnimationClass = `${isAnimation ? 'animate-slideShrink' : !isFirstRender ? 'animate-slideExpand' : null}`
  const contentsAnimationClass = `${isAnimation ? 'animate-fadeOut' : !isFirstRender ? 'animate-fadeIn' : null}`

  // event
  const handleOnClickClose = () => {
    if (isFirstRender) {
      setIsFirstRender(false)
    }

    setIsAnimation(true)

    setTimeout(() => {
      setIsTabOpen(isTabOpen)
    }, 400)
  }

  return (
    <TabLayout animation={tabAnimationClass}>
      <TabLayout.Header animation={contentsAnimationClass}>
        <h1>전체 공지</h1>
        <HoverButton
          icon={<FastLeftArrowIcon className='w-6' />}
          tooltip='공지 접기'
          onClickEvent={handleOnClickClose}
        />
      </TabLayout.Header>

      <TabLayout.Add animation={contentsAnimationClass}>
        <SearchBar />
      </TabLayout.Add>

      <TabLayout.Content animation={contentsAnimationClass}>
        <div className='w-full h-full rounded-radius-8 bg-color-bg-tertiary'>
          나중엔 공지 리스트 컴포넌트가 들어감
        </div>
      </TabLayout.Content>
    </TabLayout>
  )
}
