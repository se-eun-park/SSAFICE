import { useSortingSchedule } from '@/features/todoTab'
import { TodoDateGroup } from './TodoDateGroup'
import { useQuery } from '@tanstack/react-query'
import { instance } from '@/shared/api'
import { useDateFormatter } from '@/shared/model'
import { dummyTodoDatas } from '@/entities/dummy'
import { useState } from 'react'

type todoListProps = {
  startDate: Date
  endDate: Date
  selectedSort: 'endDateTime' | 'createdAt'
  selectedState: string
}
export const TodoList = ({ startDate, endDate, selectedSort }: todoListProps) => {
  const [reloadTrigger, setReloadTrigger] = useState(false) // boolean, toggle 식으로 작동
  const todoListReload = () => {
    setReloadTrigger(!reloadTrigger)
  }

  const { data, isLoading, error } = useQuery({
    queryKey: ['eachTodos_user', startDate, endDate, selectedSort, reloadTrigger],
    queryFn: async () => {
      const { data } = await instance.get(
        `/api/schedules/my?filterType=createdAt&sort=${selectedSort},asc&start=${useDateFormatter('API REQUEST: start', startDate) as string}&end=${useDateFormatter('API REQUEST: end', endDate) as string}`,
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

  // const -> let (DUMMY TEST)
  let sortedTodos = useSortingSchedule(data, selectedSort)
  sortedTodos = useSortingSchedule(dummyTodoDatas, selectedSort) // 요 라인 앞에 let 붙어고 윗줄 주석 붙여서 죽이기

  return (
    <div className='relative w-full h-full '>
      <div className='relative flex flex-col w-full h-full '>
        {Object.entries(sortedTodos).map(([date, dailySchedules], index) => (
          <TodoDateGroup
            key={date}
            date={date}
            dailySchedules={dailySchedules}
            isLast={index === Object.entries(sortedTodos).length - 1}
            todoListReload={todoListReload}
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
