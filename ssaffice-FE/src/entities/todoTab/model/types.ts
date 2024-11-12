export type GetTodoDataProps = {
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

export type CardColumnDataProps = {
  id: string
  label: string
  columnLength: number
}
