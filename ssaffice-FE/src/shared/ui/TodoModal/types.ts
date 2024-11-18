import { ReactNode } from 'react'

// base props
export type BaseResponse = {
  children?: ReactNode
  modaltype?: 'CREATE' | 'VIEW' | 'EDIT'
}

// sub props
export type ExitButtonResponse = {
  closeRequest: () => void
}

export type TaskTitleResponse = BaseResponse & {
  title: string
  setTitle: (title: string) => void
}

export type TaskDescriptionResponse = BaseResponse & {
  description: string
  setDescription: (description: string) => void
}

export type TaskStatusResponse = BaseResponse & {
  selectedState: string
  setSelectedState: (selectedState: string) => void
}

export type AssigneeResponse = BaseResponse & {
  user: {
    name: string
    profileImgUrl: string
  }
  userIds: string[]
  setUserIds: (userIds: string[]) => void

  userType: string
  manageType?: 'TEAM' | 'PERSONAL' | undefined
}

export type ManagerResponse = BaseResponse & {
  createUser: {
    name: string
    profileImgUrl: string
  }
  userType: string
}

export type EndDateResponse = BaseResponse & {
  endDate: string
  setEndDate: (endDate: string) => void
}

type ReminderProps = {
  remindtype: string
  remindDateTime: string
}

export type ReminderResponse = BaseResponse & {
  reminder: ReminderProps[]
  setReminder: (reminder: ReminderProps[]) => void
}

export type SaveEditButtonResponse = BaseResponse & {
  saveRequest: () => void
  editRequest?: () => void
  saveEditRequest?: () => void
}

export type RequiredResponse = BaseResponse & {
  isRequired: string
  setIsRequired: (isRequired: string) => void
}
