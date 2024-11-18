type RemindRequest = {
  remindTypeCd: string
  remindDateTime: string
}

export type postScheduleResponse = {
  title: string
  memo: string
  scheduleStatusTypeCd: string
  startDateTime: string
  endDateTime: string
  remindRequests: RemindRequest[] | []
}
