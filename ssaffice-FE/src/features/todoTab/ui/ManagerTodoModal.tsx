import { useEffect, useMemo, useState } from 'react'
import { ManagerTodoFirstElements } from '../model/ManagerTodoFirstElements'
import { TodoModal } from '@/shared/ui'
import { postManagerSchedule } from '@/shared/api/Schedule'
import { postManagerTeamSchedule } from '@/shared/api/Notice'

type RemindRequest = {
  remindTypeCd: string
  remindDateTime: string
}

type ManagerTodoModalProps = {
  closeRequest: () => void
  modaltype: 'CREATE' | 'VIEW' | 'EDIT'
  manageType: 'TEAM' | 'PERSONAL' | undefined
}

export const ManagerTodoModal = ({
  closeRequest,
  modaltype,
  manageType,
}: ManagerTodoModalProps) => {
  const elements = ManagerTodoFirstElements(modaltype)

  const [title, setTitle] = useState(elements.title)
  const [description, setDescription] = useState(elements.description)
  // const [selectedState, setSelectedState] = useState(elements.selectedState)
  const [endDate, setEndDate] = useState(elements.endDate)
  const [reminder, setReminder] = useState(elements.remindRequests)
  const [fileList, setFileList] = useState<File[]>([])
  const [userIds, setUserIds] = useState<number[]>([])
  const [channelId, setChannelId] = useState('')
  const [noticeType, setNoticeType] = useState('')
  const [isRequired, setIsRequired] = useState(elements.isEssentialYn)
  const [isDisabled, setIsDisabled] = useState(true)

  useEffect(() => {
    if (manageType === 'PERSONAL') {
      if (!title || userIds.length === 0 || !endDate) {
        setIsDisabled(true)
        return
      }

      setIsDisabled(false)
    } else {
      if (!channelId || !noticeType || !endDate || !title) {
        setIsDisabled(true)
        return
      }

      setIsDisabled(false)
    }
  }, [title, userIds, channelId, noticeType, endDate])

  const headTitle = useMemo(() => {
    switch (modaltype) {
      case 'CREATE':
        return '할 일 등록'
      case 'VIEW':
        return null
    }
  }, [modaltype])

  const handleOnClickSave = () => {
    const startDateTime = `${new Date().toISOString().split('T')[0]}T00:00:00`
    const endDateTime = `${endDate}T23:59:59`

    if (manageType === 'PERSONAL') {
      const createData = {
        title: title,
        memo: description,
        startDateTime: startDateTime,
        endDateTime: endDateTime,
        scheduleStatusTypeCd: 'TODO',
        remindRequests: reminder as RemindRequest[],
      }
      postManagerSchedule({ createData, userIds })
    } else {
      const createData = {
        title: title,
        content: description,
        startDateTime: startDateTime,
        endDateTime: endDateTime,
        noticeTypeCd: noticeType,
        essentialYn: isRequired,
        channelId: channelId,
      }
      postManagerTeamSchedule(createData, fileList)
    }

    closeRequest()
  }

  return (
    <TodoModal modaltype={modaltype}>
      <TodoModal.LeftSection>
        <div className='flex items-center gap-x-spacing-10 mb-spacing-16'>
          <TodoModal.ExitButton closeRequest={closeRequest} />
          <h1 className='heading-desktop-lg text-color-text-primary'>{headTitle}</h1>
        </div>
        <TodoModal.Title title={title} setTitle={setTitle}>
          {title}
        </TodoModal.Title>
        <TodoModal.Description description={description} setDescription={setDescription}>
          {description}
        </TodoModal.Description>
      </TodoModal.LeftSection>

      <TodoModal.RightSection>
        <TodoModal.Flex>
          <div />
          <TodoModal.Button isDisabled={isDisabled} saveRequest={handleOnClickSave} />
        </TodoModal.Flex>

        <TodoModal.File
          manageType={manageType}
          userType='manager'
          fileList={fileList}
          setFileList={setFileList}
        />

        <TodoModal.DetailsSection>
          <TodoModal.Assignee
            manageType={manageType}
            user={elements.user}
            userType='manager'
            userIds={userIds}
            setUserIds={setUserIds}
            setChannelId={setChannelId}
            setNoticeType={setNoticeType}
          />
          <TodoModal.Manager
            user={elements.user}
            createUser={elements.createUser}
            userType='manager'
          />
          <TodoModal.EndDate userType='manager' endDate={endDate} setEndDate={setEndDate} />

          {manageType === 'PERSONAL' ? null : (
            <TodoModal.Required isRequired={isRequired} setIsRequired={setIsRequired} />
          )}

          {manageType === 'PERSONAL' ? (
            <TodoModal.Reminder reminder={reminder} setReminder={setReminder} />
          ) : null}
        </TodoModal.DetailsSection>
      </TodoModal.RightSection>
    </TodoModal>
  )
}
