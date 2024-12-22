import { instance } from '@/shared/api'

export const getNoticeDetail = async (noticeId: number) => {
  const response = await instance
    .get(`/api/notice/${noticeId}`)
    .then((res) => res.data)
    .catch((err) => err.response)
  return response
}
