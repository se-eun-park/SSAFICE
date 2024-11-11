import { AnnouncementItemDisplayType } from '@/features/announcementTab'
import { AnnouncementItem } from './AnnouncementItem'
import { useDateFormatter } from '@/shared/model'

type AnnouncementDateGroupParam = {
  date: string
  dailyAnnouncements: AnnouncementItemDisplayType[]
}
export const AnnouncementDateGroup = ({ date, dailyAnnouncements }: AnnouncementDateGroupParam) => {
  return (
    <div
      className='
        flex flex-col
        relative'
    >
      <div
        className='
          pt-spacing-24 pb-spacing-16
          text-color-text-primary body-lg-medium
          sticky top-0
          bg-color-bg-tertiary z-10
      '
      >
        {`${useDateFormatter('MM월 DD일 ?요일', new Date(date))}`}
      </div>
      <div className='flex flex-col gap-spacing-16'>
        {dailyAnnouncements.map((each) => (
          <AnnouncementItem key={each.noticeId} announcementItem={each} />
        ))}
      </div>
    </div>
  )
}
