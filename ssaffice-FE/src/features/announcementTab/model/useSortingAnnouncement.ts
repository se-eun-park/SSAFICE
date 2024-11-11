import { useDateFormatter } from '@/shared/model/useDateFormatter'
import { AnnouncementItemDisplayType, AnnouncementListDisplayType } from './types'

export const useSortingAnnouncement = (
  datas: AnnouncementItemDisplayType[],
): AnnouncementListDisplayType => {
  // 무작위 순서로 API 답변이 온다고 가정
  const result: AnnouncementListDisplayType = {}

  // 공지사항을 날짜별로 묶음
  datas.forEach((each) => {
    const keyDate: string = useDateFormatter('YYYY-MM-DD(string)', each.createdAt) as string

    if (!result[keyDate]) {
      result[keyDate] = []
    }

    // 해당 날짜에 공지사항 추가
    result[keyDate].push(each)
  })

  const sortedResult: AnnouncementListDisplayType = {}

  Object.keys(result)
    .sort((a, b) => {
      return new Date(a).getTime() - new Date(b).getTime() // 날짜순 정렬
    })
    .forEach((key) => {
      sortedResult[key] = result[key].sort((a, b) => a.createdAt.getTime() - b.createdAt.getTime())
      // 지금까지 날짜 기준으로만 정렬하고 있기 때문에,
      // 여기에서 같은 날짜 내 다시 시간순 정렬 수행
    })

  return sortedResult
}
