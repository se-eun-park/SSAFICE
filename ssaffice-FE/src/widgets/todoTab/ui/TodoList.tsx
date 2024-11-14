import { AddIcon } from '@/assets/svg'
import { dummyTodos } from '@/features/todoTab'
import { useClickedToggle, useDateFormatter } from '@/shared/model'
import { TodoItem } from './TodoItem'

export const TodoList = () => {
  const { isClicked, handleIsClicked } = useClickedToggle()
  const datas = dummyTodos

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
        flex flex-col gap-spacing-16
        py-spacing-24 px-spacing-16
      '
      >
        <div
          className='
          flex gap-spacing-8
        '
        >
          {/* 날짜 영역 */}
          <div
            className='
            text-color-text-primary body-lg-medium
          '
          >
            {useDateFormatter('MM월 DD일 ?요일') as string}
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
            {datas.statusCounts[0]}
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
            {datas.statusCounts[1]}
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
            {datas.statusCounts[2]}
          </div>
        </div>

        <div className='flex flex-col gap-spacing-16'>
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
          {datas.todos.map((each) => (
            <TodoItem todo={each} />
          ))}
        </div>
      </div>
    </div>
  )
}
