import { dummyTodos, useSortingSchedule } from '@/features/todoTab'
import { TodoDateGroup } from './TodoDateGroup'

export const TodoList = () => {
  const datas = dummyTodos
  const sortedTodos = useSortingSchedule(datas.todos, 'by deadline')

  return (
    <div
      className='
      w-full mb-[99px] 
      bg-color-bg-tertiary
      rounded-radius-8
      overflow-y-scroll
    '
    >
      <div
        className='
        relative
        px-spacing-16
      '
      >
        <div
          className='
          flex flex-col
          relative
        '
        >
          {Object.entries(sortedTodos).map(([date, dailySchedules], index) => (
            <TodoDateGroup
              key={date}
              date={date}
              dailySchedules={dailySchedules}
              isLast={index === Object.entries(sortedTodos).length - 1}
            />
          ))}
        </div>
      </div>
    </div>
  )
}
