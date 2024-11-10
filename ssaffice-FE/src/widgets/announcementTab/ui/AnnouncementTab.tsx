import {
  useIsTabOpenStore,
  useSetIsTabOpenStore,
  useIsAnimationStore,
  useSetIsAnimationStore,
} from '@/shared/model'
import { SearchBar, TabLayout } from '@/shared/ui'
import { FastLeftArrowIcon } from '@/assets/svg'
import { AnnouncementList } from './AnnouncementList'
import { useAnnouncementTabSelectView } from '@/features/announcementTab'

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

  const { isAllNoticeView, handleNoticeViewSelect, selectedStyle, unselectedStyle } =
    useAnnouncementTabSelectView()

  return (
    <TabLayout animation={`${isAnimation ? 'animate-slideShrink' : 'animate-slideExpand'}`}>
      <TabLayout.Header animation={`${isAnimation ? 'animate-fadeOut' : 'animate-fadeIn'}`}>
        <div className='flex heading-desktop-xl'>
          <div
            onClick={() => handleNoticeViewSelect('미등록 공지')}
            className={isAllNoticeView ? unselectedStyle : selectedStyle}
          >
            미등록 공지
          </div>
          <div className='text-color-text-primary'>&nbsp;|&nbsp;</div>
          <div
            onClick={() => handleNoticeViewSelect('전체 공지')}
            className={isAllNoticeView ? selectedStyle : unselectedStyle}
          >
            전체 공지
          </div>
        </div>
        <button onClick={handleOnClickClose}>
          <FastLeftArrowIcon className='w-6' />
        </button>
      </TabLayout.Header>

      <TabLayout.Add animation={`${isAnimation ? 'animate-fadeOut' : 'animate-fadeIn'}`}>
        <SearchBar />
      </TabLayout.Add>

      <TabLayout.Content animation={`${isAnimation ? 'animate-fadeOut' : 'animate-fadeIn'}`}>
        <div
          className=' 
          max-h-[734px] py-spacing-24 px-spacing-16
          bg-color-bg-tertiary
          rounded-radius-8
          overflow-y-scroll
          '
        >
          {isAllNoticeView ? <AnnouncementList /> : <div className='min-h-[734px]'></div>}
        </div>
      </TabLayout.Content>
    </TabLayout>
  )
}
