import { instance } from '@/shared/api'
import { postScheduleResponse } from './types'

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

export const postTraineeSchedule = async (data: postScheduleResponse) => {
  const response = await instance.post(`/api/schedules`, data).then((res) => res.data)
  return response
}

export const postManagerSchedule = async ({ createData, userIds }: any) => {
  const response = await instance
    .post(`/api/schedules/admin`, { scheduleRequest: createData, userIds })
    .then((res) => res.data)
  return response
}

export const postManagerTeamSchedule = async (createData: any) => {
  const formData = new FormData()
  formData.append('noticeRequest', JSON.stringify(createData))

  const response = await instance.post('/api/notice/admin', formData).then((res) => res.data)

  return response
}

export const getTraineeScheduleDetail = async (scheduleId: number) => {
  return await instance.get(`/api/schedules/${scheduleId}`).then((res) => res.data)
}
