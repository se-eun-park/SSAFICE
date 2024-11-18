import { useDateFormatter } from '@/shared/model'
import { EachTodoItemDisplay, EachTodoListDisplay } from './types'

export const useSortingEachTodo = (datas: EachTodoItemDisplay[]): EachTodoListDisplay => {
  const result: EachTodoListDisplay = {}

  datas.forEach((each) => {
    const keyDate: string = useDateFormatter(
      'YYYY-MM-DD(string)',
      each.scheduleSummaries.createdAt,
    ) as string

    if (!result[keyDate]) {
      result[keyDate] = []
    }

    result[keyDate].push(each)
  })

  const sortedResult: EachTodoListDisplay = {}

  Object.keys(result)
    .sort((a, b) => {
      return new Date(a).getTime() - new Date(b).getTime()
    })
    .forEach((key) => {
      sortedResult[key] = result[key].sort(
        (a, b) => a.scheduleSummaries.createdAt.getTime() - b.scheduleSummaries.createdAt.getTime(),
      )
    })

  return sortedResult
}
