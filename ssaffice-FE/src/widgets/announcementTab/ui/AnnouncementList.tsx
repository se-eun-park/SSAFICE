import { AnnouncementDateGroup } from './AnnouncementDateGroup'
import { useSortingAnnouncement } from '@/features/announcementTab'
import { AnnouncementListDisplayType, dummyAnnouncements } from '@/features/announcementTab'

export const AnnouncementList = () => {
  const datas: AnnouncementListDisplayType = useSortingAnnouncement(dummyAnnouncements)

  return (
    <div
      className='
        w-full h-full 
    '
    >
      <div
        className='
            relative
            flex flex-col gap-spacing-16
            w-full h-full 
        '
      >
        {Object.entries(datas).map(([date, dailyAnnouncements]) => (
          <AnnouncementDateGroup key={date} date={date} dailyAnnouncements={dailyAnnouncements} />
        ))}
      </div>
    </div>
  )
}
