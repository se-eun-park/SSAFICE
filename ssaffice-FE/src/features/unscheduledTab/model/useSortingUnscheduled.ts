import { useDateFormatter } from '@/shared/model'
import type { UnscheduledListDisplay } from './types'
import type { ScheduleItemDisplay } from '@/features/todoTab'

export const useSortingUnscheduled = (
  datas: ScheduleItemDisplay[],
  type: 'by deadline' | 'by registration', //
): UnscheduledListDisplay => {
  const result: UnscheduledListDisplay = {}

  datas.forEach((each) => {
    let keyDate: string = '1970-01-01' // 임의 기본값

    switch (type) {
      case 'by deadline':
        if (each.todo.endDateTime) {
          keyDate = useDateFormatter('YYYY-MM-DD(string)', each.todo.endDateTime) as string
        }
        break

      case 'by registration':
        keyDate = useDateFormatter('YYYY-MM-DD(string)', each.announcement.createdAt) as string
        break
    }

    if (!result[keyDate]) {
      result[keyDate] = []
    }

    result[keyDate].push(each)
  })

  const sortedResult: UnscheduledListDisplay = {}

  Object.keys(result)
    .sort((a, b) => {
      return new Date(a).getTime() - new Date(b).getTime()
    })
    .forEach((key) => {
      switch (type) {
        case 'by deadline': {
          sortedResult[key] = result[key].sort((a, b) => {
            if (a.todo.endDateTime && b.todo.endDateTime)
              return a.todo.endDateTime.getTime() - b.todo.endDateTime.getTime()
            else return 0
          })
          break
        }

        case 'by registration': {
          sortedResult[key] = result[key].sort(
            (a, b) => a.announcement.createdAt.getTime() - b.announcement.createdAt.getTime(),
          )
          break
        }
      }
    })

  return sortedResult
}
