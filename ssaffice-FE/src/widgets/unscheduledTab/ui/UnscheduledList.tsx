import { useSortingUnscheduled } from '@/features/unscheduledTab'
import { UnscheduledDateGroup } from './UnscheduledDateGroup'
import { UnscheduledListDisplayType, dummyUnschedules } from '@/features/unscheduledTab'

export const UnscheduledList = () => {
  const datas: UnscheduledListDisplayType = useSortingUnscheduled(dummyUnschedules, 'by deadline')
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
        {Object.entries(datas).map(([date, dailyUnschedules]) => (
          <UnscheduledDateGroup key={date} date={date} dailyUnschedules={dailyUnschedules} />
        ))}
      </div>
    </div>
  )
}
