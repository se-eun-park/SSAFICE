// import { dummyTodos, useSortingSchedule } from '@/features/todoTab'
import { useSortingSchedule } from '@/features/todoTab'
import { TodoDateGroup } from './TodoDateGroup'
import { useQuery } from '@tanstack/react-query'
import { instance } from '@/shared/api'

export const TodoList = () => {
  const { data, isLoading, error } = useQuery({
    queryKey: ['eachTodos'],
    queryFn: async () => {
      const { data } = await instance.post('/api/schedules/my', {
        filterStartDateTime: new Date('2024-01-01'),
        filterEndDateTime: new Date('2024-11-30'),
        filterType: 'createdAt',
      })
      return data.scheduleSummaries
    },
  })

  // 데이터가 아직 로딩 중이라면 로딩 상태 표시
  if (isLoading) {
    return <div>Loading...</div>
  }

  // API 호출에 실패했을 경우 에러 메시지 표시
  if (error) {
    return <div>Error loading data</div>
  }
  console.log(data)

  const sortedTodos = useSortingSchedule(data, 'by deadline')
  return (
    <div className='relative w-full h-full '>
      <div className='relative flex flex-col w-full h-full '>
        {Object.entries(sortedTodos).map(([date, dailySchedules], index) => (
          <TodoDateGroup
            key={date}
            date={date}
            dailySchedules={dailySchedules}
            isLast={index === Object.entries(sortedTodos).length - 1}
          />
        ))}
      </div>
    </div>
  )
}
