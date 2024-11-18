import { dummyTodos, useSortingSchedule } from '@/features/todoTab'
import { TodoDateGroup } from './TodoDateGroup'

export const TodoList = () => {
  const datas = dummyTodos
  const sortedTodos = useSortingSchedule(datas.todos, 'by deadline')

  return (
    <div
      className='
        relative
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
    // </div>
  )
}
