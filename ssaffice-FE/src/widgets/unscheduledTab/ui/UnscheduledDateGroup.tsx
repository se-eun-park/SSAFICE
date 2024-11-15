import type { UnscheduledItemDisplay } from '@/features/unscheduledTab'
import { UnscheduledItem } from './UnscheduledItem'
import { useDateFormatter } from '@/shared/model'

type UnscheduledDateGroupProps = {
  date: string
  dailyUnschedules: UnscheduledItemDisplay[]
}
export const UnscheduledDateGroup = ({ date, dailyUnschedules }: UnscheduledDateGroupProps) => {
  return (
    <div
      className='
        flex flex-col
        relative
      '
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
        {dailyUnschedules.map((each) => (
          <UnscheduledItem key={each.todo.scheduleId} unscheduledItem={each} />
        ))}
      </div>
    </div>
  )
}
