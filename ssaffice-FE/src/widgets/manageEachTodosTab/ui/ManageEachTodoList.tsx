import { ManageEachTodoDateGroup } from './ManageEachTodoDateGroup'
import { dummyEachTodos } from '@/features/manageEachTodoTab'
import { useSortingEachTodo } from '@/features/manageEachTodoTab/model/useSortingEachTodo'
import { instance } from '@/shared/model'
import { useQuery } from '@tanstack/react-query'

export const ManageEachTodoList = () => {
  const { data, isLoading, error } = useQuery({
    queryKey: ['eachTodos'],
    queryFn: async () => {
      const { data } = await instance.post('/api/schedules/admin/assigned', {
        filterStartDateTime: new Date('2024-01-01'),
        filterEndDateTime: new Date(),
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

  const sortedTodos = useSortingEachTodo(data)
  return (
    <div
      className='
        relative
        w-full h-full
      '
    >
      <div
        className='
            relative
            flex flex-col
            w-full h-full
        '
      >
        {Object.entries(sortedTodos).map(([date, dailySchedules], index) => (
          <ManageEachTodoDateGroup
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
