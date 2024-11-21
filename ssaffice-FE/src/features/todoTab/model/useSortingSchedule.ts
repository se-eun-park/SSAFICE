import { useDateFormatter } from '@/shared/model'
// import type { ScheduleItemDisplay, ScheduleListDisplay } from './types'
import type { ScheduleListDisplay } from './types'
import { ScheduleSummaries } from '@/features/manageEachTodoTab/model/types'

export const useSortingSchedule = (
  //datas: ScheduleItemDisplay[],
  datas: ScheduleSummaries[],
  type: 'by deadline' | 'by registration', //
): ScheduleListDisplay => {
  const result: ScheduleListDisplay = {}

  datas.forEach((each) => {
    let keyDate: string = '1970-01-01' // 임의 기본값

    switch (type) {
      case 'by deadline':
        if (each.endDateTime) {
          keyDate = useDateFormatter('YYYY-MM-DD(string)', each.endDateTime) as string
        }
        break

      case 'by registration':
        keyDate = useDateFormatter('YYYY-MM-DD(string)', each.createdAt) as string
        break
    }

    if (!result[keyDate]) {
      result[keyDate] = []
    }

    result[keyDate].push(each)
  })

  const sortedResult: ScheduleListDisplay = {}

  Object.keys(result)
    .sort((a, b) => {
      return new Date(a).getTime() - new Date(b).getTime()
    })
    .forEach((key) => {
      switch (type) {
        case 'by deadline': {
          sortedResult[key] = result[key].sort((a, b) => {
            if (a.endDateTime && b.endDateTime)
              return new Date(a.endDateTime).getTime() - new Date(b.endDateTime).getTime()
            else return 0
          })
          break
        }

        case 'by registration': {
          sortedResult[key] = result[key].sort(
            (a, b) => new Date(a.createdAt).getTime() - new Date(b.createdAt).getTime(),
          )
          break
        }
      }
    })

  return sortedResult
}
