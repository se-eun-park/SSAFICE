import { AddIcon } from '@/assets/svg'
import { dummyTodos } from '@/features/todoTab'
import { useDateFormatter } from '@/shared/model'
import { TodoItem } from './TodoItem'

export const TodoList = () => {
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

        <div
          className='
          flex flex-col gap-spacing-16
        '
        >
          {/* overflow-y-scroll이 담당하는 영역이 '할일 등록하기' 버튼도 포함인지 물어봐야 함 */}
          <div
            className='
            flex gap-spacing-8 items-center
            h-[56px] p-spacing-16
            hover:bg-color-bg-interactive-secondary-hover
          '
          >
            <div
              className='
              w-[24px] h-[24px] p-[5px]
            '
            >
              <AddIcon />
            </div>
            <div
              className='
              text-color-text-primary body-md-medium
            '
            >
              할일 등록하기
            </div>
          </div>

          {/* todoItems */}
          {datas.todos.map((each) => (
            <TodoItem todo={each} />
          ))}
        </div>
      </div>
    </div>
  )
}
