import { ManageTeamTodoDateGroup } from './ManageTeamTodoDateGroup'
import { dummyTeamTodos } from '@/features/manageTeamTodoTab/model/types'
import { useSortingTeamTodo } from '@/features/manageTeamTodoTab/model/useSortingTeamTodo'

export const ManageTeamTodoList = () => {
  const sortedTodos = useSortingTeamTodo(dummyTeamTodos)
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
          <ManageTeamTodoDateGroup
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
