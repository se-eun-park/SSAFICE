import { useQuery } from '@tanstack/react-query'
import { getNoticeDetail } from '@/shared/api/Notice'

export const useNoticeDetail = (noticeId: number) => {
  return useQuery({
    queryKey: ['noticeDetail', noticeId],
    queryFn: () => getNoticeDetail(noticeId),
  })
}
