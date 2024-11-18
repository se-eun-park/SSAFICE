import { getTraineeScheduleList, getTraineeScheduleDetail } from '@/shared/api/Schedule'
import { useQuery } from '@tanstack/react-query'

// 교육생 할 일 리스트 조회
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

export const useTraineeScheduleDetail = (scheduleId: number) => {
  return useQuery({
    queryKey: ['todoDetailData', scheduleId],
    queryFn: () => getTraineeScheduleDetail(scheduleId),
  })
}
