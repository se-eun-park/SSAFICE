import { useSortingUnscheduled } from '@/features/unscheduledTab'
import { UnscheduledDateGroup } from './UnscheduledDateGroup'
import type { UnscheduledListDisplay } from '@/features/unscheduledTab'
import { dummyUnschedules } from '@/features/unscheduledTab'

export const UnscheduledList = () => {
  const datas: UnscheduledListDisplay = useSortingUnscheduled(dummyUnschedules, 'by deadline')
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
        {Object.entries(datas).map(([date, dailyUnschedules], index) => (
          <UnscheduledDateGroup
            key={date}
            date={date}
            dailyUnschedules={dailyUnschedules}
            isLast={index === Object.entries(datas).length - 1}
          />
        ))}
      </div>
    </div>
  )
}
