import { Card, CardColumn } from '@/features/todoTab'
import { GetTodoData, CardColumnData } from '@/entities/todoTab'

export const TodoBoard = () => {
  return (
    <div className='grid w-full h-full grid-cols-3 overflow-y-auto mt-spacing-24 gap-x-spacing-10'>
      {CardColumnData.map((column) => (
        <CardColumn key={column.id} label={column.label} columnLength={column.columnLength}>
          {GetTodoData.filter((data) => data.scheduleStatusTypeCd === column.type).map((todo) => (
            <Card
              key={todo.scheduleId}
              title={todo.title}
              endDateTime={todo.endDateTime}
              scheduleSourceType={todo.scheduleSourceTypeCd}
              createUser={todo.createUser}
            />
          ))}
        </CardColumn>
      ))}
    </div>
  )
}
