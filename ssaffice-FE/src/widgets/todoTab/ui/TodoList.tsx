import { useSortingSchedule } from '@/features/todoTab'
import { TodoDateGroup } from './TodoDateGroup'
import { useQuery } from '@tanstack/react-query'
import { instance } from '@/shared/api'
import { useClickedToggle, useDateFormatter } from '@/shared/model'
import { useState } from 'react'
import { TodoItem } from './TodoItem'
import { AddIcon } from '@/assets/svg'

type todoListProps = {
  startDate: Date
  endDate: Date
  selectedSort: 'endDateTime' | 'createdAt'
  selectedState: string
}
export const TodoList = ({ startDate, endDate, selectedSort, selectedState }: todoListProps) => {
  const [reloadTrigger, setReloadTrigger] = useState(false) // boolean, toggle 식으로 작동
  const { isClicked, handleIsClicked } = useClickedToggle()
  const todoListReload = () => {
    setReloadTrigger(!reloadTrigger)
  }

  const { data, isLoading, error } = useQuery({
    queryKey: ['eachTodos_user', startDate, endDate, selectedSort, reloadTrigger],
    queryFn: async () => {
      const { data } = await instance.get(
        `/api/schedules/my?filterType=${selectedSort}&sort=${selectedSort},asc&start=${useDateFormatter('API REQUEST: start', startDate) as string}&end=${useDateFormatter('API REQUEST: end', endDate) as string}`,
      )
      return data.scheduleSummaries
    },
  })

  if (isLoading) {
    return <div className='flex w-full h-full'>Loading...</div>
  }

  if (error) {
    return <div>Error loading data</div>
  }

  const sortedTodos = useSortingSchedule(data, selectedSort)

  return (
    <div className='relative w-full h-full '>
      <div className='relative flex flex-col w-full h-full '>
        {/* 등록 순으로 설정된 경우, 오늘 날짜로만(*마감일도 오늘 날짜임) 간편 등록 가능합니다. */}
        {selectedSort === 'createdAt' ? (
          <>
            <div className='sticky top-0 z-10 flex gap-spacing-8 pt-spacing-24 pb-spacing-16 bg-color-bg-tertiary'>
              {/* 날짜 영역 */}
              <div className=' text-color-text-primary body-lg-medium'>
                {useDateFormatter('MM월 DD일 ?요일', new Date()) as string}
              </div>
              <div className='flex items-center justify-center px-spacing-8 py-spacing-2 text-color-text-interactive-inverse body-sm-medium bg-color-bg-info rounded-radius-circle'>
                오늘
              </div>
            </div>

            {isClicked ? (
              <div
                className='
                flex gap-spacing-8 items-center
                h-[56px]
                '
              >
                <TodoItem
                  todoListReload={todoListReload}
                  backToAddNewTodoButton={handleIsClicked}
                  today={new Date()}
                  visible
                />
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
          </>
        ) : (
          <></>
        )}
        {Object.entries(sortedTodos).map(([date, dailySchedules], index) => (
          <TodoDateGroup
            key={date}
            date={date}
            dailySchedules={dailySchedules}
            isLast={index === Object.entries(sortedTodos).length - 1}
            todoListReload={todoListReload}
            selectedState={selectedState}
            selectedSort={selectedSort}
          />
        ))}
        {Object.entries(sortedTodos).length === 0 && (
          <div
            className='
              flex justify-center items-center
              text-color-text-primary heading-desktop-md whitespace-pre-line
            '
          >
            등록된 일정이 없습니다.
          </div>
        )}
      </div>
    </div>
  )
}
