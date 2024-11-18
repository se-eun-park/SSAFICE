import { AddIcon } from '@/assets/svg'
import { useClickedToggle, useDateFormatter } from '@/shared/model'
import { useCalculateStatusCounts, type ScheduleItemDisplay } from '@/features/todoTab'
import { TodoItem } from './TodoItem'

type TodoDateGroupProps = {
  date: string
  dailySchedules: ScheduleItemDisplay[]
  isLast?: boolean
}

export const TodoDateGroup = ({ date, dailySchedules, isLast }: TodoDateGroupProps) => {
  const { isClicked, handleIsClicked } = useClickedToggle()
  const statusCounts: number[] = useCalculateStatusCounts(dailySchedules)
  return (
    <div className='flex flex-col relative'>
      <div
        className='
          flex gap-spacing-8
          pt-spacing-24 pb-spacing-16
          bg-color-bg-tertiary 
          sticky top-0 z-10
        '
      >
        {/* 날짜 영역 */}
        <div
          className='
            text-color-text-primary body-lg-medium
            
            '
        >
          {useDateFormatter('MM월 DD일 ?요일', new Date(date)) as string}
        </div>

        <div
          className='
            flex justify-center items-center
            px-spacing-8 py-spacing-2
            text-color-text-interactive-inverse body-sm-medium
            bg-color-bg-interactive-disabled
            rounded-radius-circle
            '
        >
          {/* count of TODOs */}
          {statusCounts[0]}
        </div>

        <div
          className='
            flex justify-center items-center
            px-spacing-8 py-spacing-2
            text-color-text-interactive-inverse body-sm-medium
            bg-color-bg-info
            rounded-radius-circle
        '
        >
          {/* count of IN_PROGRESS */}
          {statusCounts[1]}
        </div>

        <div
          className='
            flex justify-center items-center
            px-spacing-8 py-spacing-2
            text-color-text-interactive-inverse body-sm-medium
            bg-color-bg-success
            rounded-radius-circle
            '
        >
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
            {/* 
        11/13 여기까지
        이 라인이랑, TodoItem todo 없는 케이스 작업하면 됨
        지금은 newTodo(새로 등록하는 경우)에서 hover 시 바탕화면 색 빼는 등등의 작업 필요
        조건부렌더링 가독성 에바면 컴포를 아예 새로 하나 만들던가 
        
        */}
            <TodoItem />
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
          <TodoItem key={each.todo.scheduleId} todo={each} />
        ))}
      </div>
    </div>
  )
}
