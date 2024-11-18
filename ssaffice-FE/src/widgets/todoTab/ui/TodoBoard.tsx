import { useState } from 'react'
import { DndContext, DragEndEvent, PointerSensor, useSensor, useSensors } from '@dnd-kit/core'
import { restrictToFirstScrollableAncestor } from '@dnd-kit/modifiers'

import { useLockScrollX } from '@/features/todoTab/model/hooks'
import { Card, CardColumn } from '@/features/todoTab'
import { GetTodoData, CardColumnData } from '@/entities/todoTab'
import type { GetTodoResponse } from '@/entities/todoTab'

export const TodoBoard = () => {
  // hook
  useLockScrollX('.grid')

  const pointerSensor = useSensor(PointerSensor, {
    activationConstraint: {
      distance: 5,
    },
  })
  const sensors = useSensors(pointerSensor)

  // state
  const [tasks, setTasks] = useState<GetTodoResponse[]>(GetTodoData)
  const [columnLength, setColumnLength] = useState(
    CardColumnData.reduce(
      (acc, column) => {
        acc[column.id] = column.columnLength
        return acc
      },
      {} as { [key: string]: number },
    ),
  )

  // event
  const handleDragEnd = (event: DragEndEvent) => {
    const { active, over } = event

    if (!over) return

    const taskId = active.id as string
    const oldStatus = active.data.current?.status
    const newStatus = over.id as GetTodoResponse['scheduleStatusTypeCd']

    if (oldStatus === newStatus) return

    setTasks(() => {
      const updatedTasks = tasks.filter((task) => task.scheduleId !== taskId)
      const updatedTask = tasks.find((task) => task.scheduleId === taskId)

      if (updatedTask) {
        return [{ ...updatedTask, scheduleStatusTypeCd: newStatus }, ...updatedTasks]
      }

      return tasks
    })

    setColumnLength((prev) => {
      const prevLength = prev[oldStatus]
      const newLength = prev[newStatus]

      return {
        ...prev,
        [oldStatus]: prevLength - 1,
        [newStatus]: newLength + 1,
      }
    })
  }

  return (
    <div className='grid w-full h-full grid-cols-3 overflow-x-hidden overflow-y-scroll overscroll-contain mt-spacing-24 gap-x-spacing-10'>
      <DndContext
        onDragEnd={handleDragEnd}
        sensors={sensors}
        modifiers={[restrictToFirstScrollableAncestor]}
      >
        {CardColumnData.map((column) => (
          <CardColumn
            key={column.id}
            id={column.id}
            label={column.label}
            columnLength={columnLength[column.id]}
          >
            {tasks
              .filter((data) => data.scheduleStatusTypeCd === column.id)
              .map((todo) => (
                <Card
                  key={todo.scheduleId}
                  scheduleId={todo.scheduleId}
                  title={todo.title}
                  endDateTime={todo.endDateTime}
                  scheduleStatusTypeCd={todo.scheduleStatusTypeCd}
                  scheduleSourceTypeCd={todo.scheduleSourceTypeCd}
                  createUser={todo.createUser}
                />
              ))}
          </CardColumn>
        ))}
      </DndContext>
    </div>
  )
}
