import { TodoFlag } from '@/assets/svg'
import type { ScheduleItemDisplay } from '@/features/todoTab'

type TodoItemProps = {
  todo: ScheduleItemDisplay
}

export const TodoItem = ({ todo }: TodoItemProps) => {
  return (
    <div
      className='
      flex justify-between
      p-spacing-16
      bg-color-bg-primary
      border-color-border-tertiary border-spacing-1
      rounded-radius-8
    '
    >
      <div
        className='
        flex self-start gap-spacing-8 items-center
      '
      >
        {/* 할일 왼쪽 부분(svg, title) */}
        <div
          className='
          flex 
          w-spacing-24 h-spacing-24 
          justify-center items-center
        '
        >
          <div className='flex w-[14px] h-[18px]'>
            {todo.todo.scheduleStatusTypeCd && <TodoFlag type={todo.todo.scheduleStatusTypeCd} />}
          </div>
        </div>

        <div
          className={`
            flex flex-1
            text-color-text-primary body-sm-medium
            ${todo.todo.scheduleStatusTypeCd === 'DONE' && 'line-through'}
          `}
        >
          {todo.todo.title}
        </div>
      </div>

      <div className=''>{/* 할 일 상태 드롭다운 파트 */}</div>
    </div>
  )
}
