import { ReactNode } from 'react'

export type GetTodoResponse = {
  scheduleId: string
  title: string
  startDateTime?: string
  endDateTime: string
  scheduleSourceTypeCd: string
  scheduleStatusTypeCd: string
  createUser: {
    userId: string
    name: string
    profileImgUrl: string
  }
}

export type CardColumnResponse = {
  children: ReactNode
  id: string
  label: string
  columnLength: number
}
