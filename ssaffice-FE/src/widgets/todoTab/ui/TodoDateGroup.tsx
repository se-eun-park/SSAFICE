import { AddIcon } from '@/assets/svg'
import { useClickedToggle, useDateFormatter } from '@/shared/model'
// import { useCalculateStatusCounts, type ScheduleItemDisplay } from '@/features/todoTab'
import { useCalculateStatusCounts } from '@/features/todoTab'
import { TodoItem } from './TodoItem'
import { ScheduleSummaries } from '@/features/manageEachTodoTab/model/types'

type TodoDateGroupProps = {
  date: string
  // dailySchedules: ScheduleItemDisplay[]
  dailySchedules: ScheduleSummaries[]
  todoListReload: () => void
  isLast?: boolean
}

export const TodoDateGroup = ({
  date,
  dailySchedules,
  todoListReload,
  isLast,
}: TodoDateGroupProps) => {
  const { isClicked, handleIsClicked } = useClickedToggle()
  const statusCounts: number[] = useCalculateStatusCounts({
    param: { todos: dailySchedules, type: 'user' },
  })

  return (
    <div className='relative flex flex-col'>
      <div className='sticky top-0 z-10 flex gap-spacing-8 pt-spacing-24 pb-spacing-16 bg-color-bg-tertiary'>
        {/* 날짜 영역 */}
        <div className=' text-color-text-primary body-lg-medium'>
          {useDateFormatter('MM월 DD일 ?요일', new Date(date)) as string}
        </div>

        <div className='flex items-center justify-center px-spacing-8 py-spacing-2 text-color-text-interactive-inverse body-sm-medium bg-color-bg-interactive-disabled rounded-radius-circle'>
          {/* count of TODOs */}
          {statusCounts[0]}
        </div>

        <div className='flex items-center justify-center px-spacing-8 py-spacing-2 text-color-text-interactive-inverse body-sm-medium bg-color-bg-info rounded-radius-circle'>
          {/* count of IN_PROGRESS */}
          {statusCounts[1]}
        </div>

        <div className='flex items-center justify-center px-spacing-8 py-spacing-2 text-color-text-interactive-inverse body-sm-medium bg-color-bg-success rounded-radius-circle'>
          {/* count of DONEs */}
          {statusCounts[2]}
        </div>
      </div>

      <div className={`flex flex-col gap-spacing-16 ${isLast ? 'pb-spacing-16' : ''}`}>
        {isClicked ? (
          <div
            className='
                flex gap-spacing-8 items-center
                h-[56px]
                '
          >
            <TodoItem
              todoListReload={todoListReload}
              backToAddNewTodoButton={handleIsClicked}
              today={new Date(date)}
            />
          </div>
        ) : (
          <div
            className='
                flex gap-spacing-8 items-center
                h-[56px] p-spacing-16
                hover:bg-color-bg-interactive-secondary-hover
                '
            onClick={handleIsClicked}
          >
            <div className='w-[24px] h-[24px] p-[5px]'>
              <AddIcon />
            </div>
            <div className='text-color-text-primary body-md-medium'>할일 등록하기</div>
          </div>
        )}

        {/* todoItems */}
        {dailySchedules.map((each) => (
          <TodoItem key={each.scheduleId} todo={each} todoListReload={todoListReload} />
        ))}
      </div>
    </div>
  )
}
