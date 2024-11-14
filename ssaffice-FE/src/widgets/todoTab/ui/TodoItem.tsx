import { SpreadDown, TodoFlag } from '@/assets/svg'
import { SelectTodoState, type ScheduleItemDisplay } from '@/features/todoTab'

type TodoItemProps = {
  todo?: ScheduleItemDisplay
  // todo 객체가 전달되면 -> 할일 리스트의 각 할일 컴포넌트
  // todo 객체가 전달되지 않으면 -> 새로운 할일 등록 컴포넌트
}

export const TodoItem = ({ todo }: TodoItemProps) => {
  return (
    <div
      className={`
        flex justify-between
        w-full p-spacing-16
        bg-color-bg-primary ${todo && 'hover:bg-color-bg-interactive-secondary-hover active:bg-color-bg-interactive-secondary-press'}
        border border-color-border-tertiary border-spacing-1
        rounded-radius-8
        `}
    >
      <div
        className={`
          flex gap-spacing-8 items-center
          ${todo || 'w-full'}
        `}
      >
        {/* todoItem 왼쪽 파트(상태 라벨, 할 일 이름) */}

        {/* 상태 라벨 */}
        {todo ? (
          todo.todo.scheduleStatusTypeCd && (
            <div
              className='
                  flex justify-center items-center
                  w-spacing-24 h-spacing-24 
                  '
            >
              <div className='flex w-[14px] h-[18px]'>
                <TodoFlag type={todo.todo.scheduleStatusTypeCd} />
              </div>
            </div>
          )
        ) : (
          // 새 할 일 만들기, 라벨 + 드롭다운
          <div
            className='
            flex gap-spacing-4 items-center
            rounded-radius-8
            hover:bg-color-bg-interactive-secondary-hover
            active:bg-color-bg-interactive-secondary-press
          '
          >
            <div
              className='
              flex justify-center items-center
              w-spacing-24 h-spacing-24 
              '
            >
              <div className='flex w-[14px] h-[18px]'>
                <TodoFlag type='TODO' />
              </div>
            </div>

            <div
              className='
                  flex justify-center items-center
                  w-spacing-16 h-spacing-16
                '
            >
              <div className='flex items-center w-[7px] h-[3px]'>
                <SpreadDown />
              </div>
            </div>
          </div>
        )}

        {todo ? (
          // 있는 할 일 출력, 할일 제목
          <div
            className={`
                  flex-1
                  text-color-text-primary body-sm-medium
                  ${todo.todo.scheduleStatusTypeCd === 'DONE' && 'line-through'}
                  `}
          >
            {todo.todo.title}
          </div>
        ) : (
          <input
            type='text'
            className='
              flex-1
              text-color-text-primary body-sm-medium
              placeholder:text-color-text-disabled placeholder:body-sm-medium
              focus:outline-none
            '
            placeholder='할일을 입력해주세요'
          />
        )}
      </div>

      <div className='flex justify-end'>
        {todo && (
          // 할일 리스트의 각 할일 컴포넌트
          <>
            {/* 할 일 상태 드롭다운 파트 */}
            <SelectTodoState
              state={
                todo.todo.scheduleStatusTypeCd === 'IN_PROGRESS'
                  ? 'progress'
                  : todo.todo.scheduleStatusTypeCd.toLowerCase()
              }
              actionType='modify'
            />
          </>
        )}
      </div>
    </div>
  )
}
