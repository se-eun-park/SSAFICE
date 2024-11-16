import { AnnouncementDateGroup } from './AnnouncementDateGroup'
import { useSortingAnnouncement } from '@/features/announcementTab'
import type { AnnouncementListDisplay } from '@/features/announcementTab'
import { dummyAnnouncements } from '@/features/announcementTab'

export const AnnouncementList = () => {
  const datas: AnnouncementListDisplay = useSortingAnnouncement(dummyAnnouncements)

  return (
    <div
      className='
        w-full h-full
      '
    >
      <div
        className='
            relative
            flex flex-col
            w-full h-full
        '
      >
        {Object.entries(datas).map(([date, dailyAnnouncements], index) => (
          <AnnouncementDateGroup
            key={date}
            date={date}
            dailyAnnouncements={dailyAnnouncements}
            isLast={index === Object.entries(datas).length - 1}
          />
        ))}
      </div>
    </div>
  )
}
