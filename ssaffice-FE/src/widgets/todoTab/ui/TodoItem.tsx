import { useEffect, useState } from 'react'
import { TodoFlag } from '@/assets/svg'
// import { type ScheduleItemDisplay } from '@/features/todoTab'
import { SelectTodoState } from '@/shared/ui'
import { ScheduleSummaries } from '@/features/manageEachTodoTab/model/types'
import { useDateFormatter } from '@/shared/model'

type TodoItemProps = {
  // todo?: ScheduleItemDisplay
  todo?: ScheduleSummaries
  // todo 객체가 전달되면 -> 할일 리스트의 각 할일 컴포넌트
  // todo 객체가 전달되지 않으면 -> 새로운 할일 등록 컴포넌트
  todoListReload: () => void
  backToAddNewTodoButton?: () => void
  // 할일 간편 추가 시 startDate/endDate
  today?: Date
}

export const TodoItem = ({
  todo,
  todoListReload,
  backToAddNewTodoButton,
  today,
}: TodoItemProps) => {
  const [selectedState, setSelectedState] = useState('default')
  const [newTodo, setNewTodo] = useState<string | undefined>()
  const handleNewTodo = (val: string) => {
    setNewTodo(val)
  }
  const addNewTodoTrigger = (key: string) => {
    if (key === 'Enter') {
      console.log(newTodo)
      console.log(useDateFormatter('API REQUEST: start', today) as string)
      console.log(useDateFormatter('API REQUEST: end', today) as string)
      // 일정 등록 api 요청 붙이기 -> refresh
      //   {
      //     "title" : "최종 발표회 준비",
      //     "memo" : "",
      //     "startDateTime" : useDateFormatter('API REQUEST: start', today) as string,
      //     "endDateTime" : useDateFormatter('API REQUEST: end', today) as string,
      //     "scheduleStatusTypeCd" : selectedState,
      //     "remindRequests": [
      //     {
      //       "essentialYn": "N",
      //       "remindTypeCd": "ONCE",
      //       "remindDateTime": "2024-11-07T15:00:00"
      //     }
      //   ]
      // }

      // todoList reload하는 trigger
      todoListReload()
      backToAddNewTodoButton && backToAddNewTodoButton()
      setNewTodo(undefined) // input 빈 값으로 돌려놓기
    }
  }

  // TodoState modify request
  useEffect(() => {
    if (todo) {
      // 이미 등록된 할일 객체에서만 실행

      // 수정 요청 api -> /api/schedules/todo.scheduleId
      // { "scheduleStatusTypeCd" : selectedState}
      // todoListReload()
      console.log(selectedState)
    }
  }, [selectedState])

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
          todo.scheduleStatusTypeCd && (
            <div className='flex items-center justify-center w-spacing-24 h-spacing-24'>
              <div className='flex w-[14px] h-[18px]'>
                <TodoFlag type={todo.scheduleStatusTypeCd} />
              </div>
            </div>
          )
        ) : (
          // 새 할 일 만들기, 라벨 + 드롭다운
          <SelectTodoState
            selectedState={selectedState}
            setSelectedState={setSelectedState}
            actionType='create'
          />
        )}

        {todo ? (
          // 있는 할 일 출력, 할일 제목
          <div
            className={`
                  flex-1
                  text-color-text-primary body-sm-medium
                  ${todo.scheduleStatusTypeCd === 'DONE' && 'line-through'}
                  `}
          >
            {todo.title}
          </div>
        ) : (
          <input
            type='text'
            className='flex-1 text-color-text-primary body-sm-medium placeholder:text-color-text-disabled placeholder:body-sm-medium focus:outline-none'
            placeholder='할일을 입력해주세요'
            onChange={(e) => handleNewTodo(e.currentTarget.value)}
            onKeyDown={(e) => addNewTodoTrigger(e.key)}
          />
        )}
      </div>

      <div className='flex justify-end'>
        {todo && (
          // 할일 리스트의 각 할일 컴포넌트
          <>
            {/* 할 일 상태 드롭다운 파트 */}
            <SelectTodoState
              selectedState={todo.scheduleStatusTypeCd}
              setSelectedState={setSelectedState}
              actionType='modify'
            />
          </>
        )}
      </div>
    </div>
  )
}
