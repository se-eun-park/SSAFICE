import { useSortingSchedule } from '@/features/todoTab'
import { TodoDateGroup } from './TodoDateGroup'
import { useQuery } from '@tanstack/react-query'
import { instance } from '@/shared/api'
import { useDateFormatter } from '@/shared/model'

type todoListProps = {
  startDate: Date
  endDate: Date
}
export const TodoList = ({ startDate, endDate }: todoListProps) => {
  const { data, isLoading, error } = useQuery({
    queryKey: ['eachTodos_user', startDate, endDate],
    queryFn: async () => {
      const { data } = await instance.get(
        `/api/schedules/my?filterType=createdAt&sort=endDateTime,asc&start=${useDateFormatter('API REQUEST: start', startDate) as string}&end=${useDateFormatter('API REQUEST: end', endDate) as string}`,
      )
      return data.scheduleSummaries
    },
  })

  if (isLoading) {
    return <div>Loading...</div>
  }

  if (error) {
    return <div>Error loading data</div>
  }

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
