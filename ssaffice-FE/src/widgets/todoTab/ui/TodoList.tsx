import { AddIcon } from '@/assets/svg'
import { useDateFormatter } from '@/shared/model'

export const TodoList = () => {
  return (
    <div className='w-full h-full rounded-radius-8 bg-color-bg-tertiary'>
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
            {/* count of TODOs */}2
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
            {/* count of IN_PROGRESS */}3
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
            {/* count of DONEs */} 12
          </div>
        </div>

        <div
          className='
          flex flex-col gap-spacing-16
          max-h-full 
          overflow-y-scroll
        '
        >
          {/* overflow-y-scroll이 담당하는 영역이 '할일 등록하기' 버튼도 포함인지 물어봐야 함 */}
          <div
            className='
            flex gap-spacing-8 items-center
            h-[56px] p-spacing-16
          '
          >
            <div
              className='
              w-[14px] h-[14px]
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
        </div>
      </div>
    </div>
  )
}
