import { TeamTodoItemDisplay } from '@/features/manageTeamTodoTab'
import { ScheduleItemDisplay } from './types'
import { EachTodoItemDisplay } from '@/features/manageEachTodoTab'

export type UseCalculateStatusCountsProps = {
  param:
    | {
        todos: ScheduleItemDisplay[]
        type: 'user'
      }
    | {
        todos: TeamTodoItemDisplay[]
        type: 'managerTeamTodo'
      }
    | {
        todos: EachTodoItemDisplay[]
        type: 'managerEachTodo'
      }
}
export const useCalculateStatusCounts = ({ param }: UseCalculateStatusCountsProps): number[] => {
  const result: number[] = []

  switch (param.type) {
    case 'user': {
      // count of todos
      const scheduleTodo: ScheduleItemDisplay[] = param.todos.filter((each) => {
        return each.todo.scheduleStatusTypeCd === 'TODO'
      })
      result[0] = scheduleTodo.length

      // count of in_progress
      const scheduleProgress: ScheduleItemDisplay[] = param.todos.filter((each) => {
        return each.todo.scheduleStatusTypeCd === 'IN_PROGRESS'
      })
      result[1] = scheduleProgress.length

      // count of done
      const scheduleDone: ScheduleItemDisplay[] = param.todos.filter((each) => {
        return each.todo.scheduleStatusTypeCd === 'DONE'
      })
      result[2] = scheduleDone.length
      break
    }
    case 'managerTeamTodo': {
      const enrolledTodo: TeamTodoItemDisplay[] = param.todos.filter((each) => {
        return (
          each.scheduleEnrolledCount.enrolledCount === 0 ||
          each.scheduleEnrolledCount.enrolledCount !== each.scheduleEnrolledCount.completedCount
        )
      })
      result[0] = enrolledTodo.length

      const completedTodo: TeamTodoItemDisplay[] = param.todos.filter((each) => {
        return (
          each.scheduleEnrolledCount.enrolledCount !== 0 &&
          each.scheduleEnrolledCount.enrolledCount === each.scheduleEnrolledCount.completedCount
        )
      })
      result[1] = completedTodo.length
      break
    }
    case 'managerEachTodo': {
      const enrolledTodo: EachTodoItemDisplay[] = param.todos.filter((each) => {
        return (
          each.scheduleSummaries.isEnrollYn === 'Y' &&
          each.scheduleSummaries.scheduleStatusTypeCd !== 'DONE'
        )
      }) // 등록만 하고 완료하지는 못한 상태 (진행중)
      result[0] = enrolledTodo.length

      const completedTodo: EachTodoItemDisplay[] = param.todos.filter((each) => {
        return (
          each.scheduleSummaries.isEnrollYn === 'Y' &&
          each.scheduleSummaries.scheduleStatusTypeCd === 'DONE'
        )
      })
      result[1] = completedTodo.length
      break
    }
  }

  return result
}
