import { ManageEachTodoDateGroup } from './ManageEachTodoDateGroup'
import { dummyEachTodos } from '@/features/manageEachTodoTab'
import { useSortingEachTodo } from '@/features/manageEachTodoTab/model/useSortingEachTodo'

export const ManageEachTodoList = () => {
  const sortedTodos = useSortingEachTodo(dummyEachTodos)
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
          <ManageEachTodoDateGroup
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
