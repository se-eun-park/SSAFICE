import { useDateFormatter } from '@/shared/model'
// import type { ScheduleItemDisplay, ScheduleListDisplay } from './types'
import type { ScheduleListDisplay } from './types'
import { ScheduleSummaries } from '@/features/manageEachTodoTab/model/types'

export const useSortingSchedule = (
  //datas: ScheduleItemDisplay[],
  datas: ScheduleSummaries[],
  sortType: 'endDateTime' | 'createdAt',
) => {
  const result: ScheduleListDisplay = {}
  const todaySchedule: ScheduleListDisplay = {}
  const restSchedules: ScheduleListDisplay = {}

  datas.forEach((each) => {
    let keyDate: string = '1970-01-01' // 임의 기본값

    switch (sortType) {
      case 'endDateTime':
        if (each.endDateTime) {
          keyDate = useDateFormatter('YYYY-MM-DD(string)', each.endDateTime) as string
        }
        break

      case 'createdAt':
        keyDate = useDateFormatter('YYYY-MM-DD(string)', each.createdAt) as string
        break
    }

    if (!result[keyDate]) {
      result[keyDate] = []
    }

    result[keyDate].push(each)
    if (keyDate === (useDateFormatter('YYYY-MM-DD(string)', new Date()) as string)) {
      if (!todaySchedule[keyDate]) todaySchedule[keyDate] = []
      todaySchedule[keyDate].push(each)
    } else {
      if (!restSchedules[keyDate]) restSchedules[keyDate] = []
      restSchedules[keyDate].push(each)
    }
  })

  if (Object.entries(todaySchedule).length !== 1) {
    // 오늘 날짜에 해당하는 스케줄이 없는 경우, 빈 배열 리턴
    // (날짜:일정 배열의 key:value 형태로 묶기 때문에, todaySchedule의 길이가 1보다 클 수 없습니다)
    todaySchedule[useDateFormatter('YYYY-MM-DD(string)', new Date()) as string] = []
  }

  const sortedResult: ScheduleListDisplay = {}
  const sortedRestSchedules: ScheduleListDisplay = {}

  const sort = (prev: ScheduleListDisplay, sorted: ScheduleListDisplay) => {
    Object.keys(prev)
      .sort((a, b) => {
        return new Date(a).getTime() - new Date(b).getTime()
      })
      .forEach((key) => {
        switch (sortType) {
          case 'endDateTime': {
            sorted[key] = prev[key].sort((a, b) => {
              if (a.endDateTime && b.endDateTime)
                return new Date(a.endDateTime).getTime() - new Date(b.endDateTime).getTime()
              else return 0
            })
            break
          }

          case 'createdAt': {
            sorted[key] = prev[key].sort(
              (a, b) => new Date(a.createdAt).getTime() - new Date(b.createdAt).getTime(),
            )
            break
          }
        }
      })
  }

  sort(result, sortedResult)
  sort(restSchedules, sortedRestSchedules)

  return { sortedResult, todaySchedule, restSchedules }
}
