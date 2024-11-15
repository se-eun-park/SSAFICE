import { ScheduleItemDisplay } from './types'

export const useCalculateStatusCounts = (todos: ScheduleItemDisplay[]): number[] => {
  const result: number[] = []

  // count of todos
  const scheduleTodo: ScheduleItemDisplay[] = todos.filter((each) => {
    return each.todo.scheduleStatusTypeCd === 'TODO'
  })
  result[0] = scheduleTodo.length

  // count of in_progress
  const scheduleProgress: ScheduleItemDisplay[] = todos.filter((each) => {
    return each.todo.scheduleStatusTypeCd === 'IN_PROGRESS'
  })
  result[1] = scheduleProgress.length

  // count of done
  const scheduleDone: ScheduleItemDisplay[] = todos.filter((each) => {
    return each.todo.scheduleStatusTypeCd === 'DONE'
  })
  result[2] = scheduleDone.length

  return result
}
