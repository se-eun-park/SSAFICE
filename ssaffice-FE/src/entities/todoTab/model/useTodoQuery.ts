import { getTraineeScheduleList } from '@/shared/api/Schedule'
import { useQuery } from '@tanstack/react-query'

export const useTraineeScheduleList = (
  filterType: string,
  filterStartDateTime: string,
  filterEndDateTime: string,
) => {
  return useQuery({
    queryKey: ['todoData', filterType, filterStartDateTime, filterEndDateTime],
    queryFn: () => getTraineeScheduleList(filterType, filterStartDateTime, filterEndDateTime),
  })
}
