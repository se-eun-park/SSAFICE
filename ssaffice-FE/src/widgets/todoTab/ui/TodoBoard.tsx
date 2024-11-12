import { Card, CardColumn } from '@/features/todoTab'
import { GetTodoData } from '@/entities/todoTab'

export const TodoBoard = () => {
  return (
    <div className='grid w-full h-full grid-cols-3 overflow-y-auto mt-spacing-24 gap-x-spacing-10'>
      <CardColumn label='해야 할 일' columnLength={12}>
        {GetTodoData.filter((data) => data.scheduleStatusTypeCd === 'todo').map((todo) => (
          <Card
            key={todo.scheduleId}
            title={todo.title}
            endDateTime={todo.endDateTime}
            scheduleSourceType={todo.scheduleSourceTypeCd}
            createUser={todo.createUser}
          />
        ))}
      </CardColumn>
      <CardColumn label='진행 중' columnLength={18}>
        {GetTodoData.filter((data) => data.scheduleStatusTypeCd === 'progress').map((todo) => (
          <Card
            key={todo.scheduleId}
            title={todo.title}
            endDateTime={todo.endDateTime}
            scheduleSourceType={todo.scheduleSourceTypeCd}
            createUser={todo.createUser}
          />
        ))}
      </CardColumn>
      <CardColumn label='완료' columnLength={2}>
        {GetTodoData.filter((data) => data.scheduleStatusTypeCd === 'done').map((todo) => (
          <Card
            key={todo.scheduleId}
            title={todo.title}
            endDateTime={todo.endDateTime}
            scheduleSourceType={todo.scheduleSourceTypeCd}
            createUser={todo.createUser}
          />
        ))}
      </CardColumn>
    </div>
  )
}
