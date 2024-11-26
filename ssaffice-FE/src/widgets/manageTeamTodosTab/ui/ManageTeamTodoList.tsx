import { ManageTeamTodoDateGroup } from './ManageTeamTodoDateGroup'
import { useSortingTeamTodo } from '@/features/manageTeamTodoTab/model/useSortingTeamTodo'
import { instance } from '@/shared/api'
import { useDateFormatter } from '@/shared/model'
import { useQuery } from '@tanstack/react-query'

// api

export const ManageTeamTodoList = () => {
  const { data, isLoading, error } = useQuery({
    queryKey: ['teamTodos'],
    queryFn: async () => {
      const { data } = await instance.get(
        `/api/notice/admin/my?filterType=createdAt&sort=endDateTime,asc&start=${useDateFormatter('API REQUEST: start', new Date('2024-01-01')) as string}&end=${useDateFormatter('API REQUEST: end', new Date('2024-12-31')) as string}`,
      )
      return data
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

  // 데이터를 정렬
  const sortedTodos = useSortingTeamTodo(data)

  return (
    <div className='relative w-full h-full'>
      <div className='relative flex flex-col w-full h-full'>
        {Object.entries(sortedTodos).map(([date, dailySchedules], index) => (
          <ManageTeamTodoDateGroup
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
