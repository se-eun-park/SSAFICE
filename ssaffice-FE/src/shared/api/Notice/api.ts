import { instance } from '@/shared/api'

export const getNoticeDetail = async (noticeId: number) => {
  const response = await instance
    .get(`/api/notice/${noticeId}`)
    .then((res) => res.data)
    .catch((err) => err.response)
  return response
}

export const postManagerTeamSchedule = async (createData: any, fileList: File[]) => {
  const formData = new FormData()
  formData.append('notice', new Blob([JSON.stringify(createData)], { type: 'application/json' }))
  formData.append('files', JSON.stringify(fileList))

  const response = await instance
    .post('/api/notice/admin', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    })
    .then((res) => res.data)

  return response
}
