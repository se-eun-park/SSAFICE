import { instance } from '@/shared/api'

export const getTraineeScheduleList = async (
  filterType: string,
  filterStartDateTime: string,
  filterEndDateTime: string,
) => {
  return await instance
    .post(`/api/schedules/my?sort=${filterType},desc`, {
      params: { filterType, filterStartDateTime, filterEndDateTime },
    })
    .then((res) => res.data)
}
