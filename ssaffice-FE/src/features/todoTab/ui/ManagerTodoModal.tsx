import { useMemo, useState } from 'react'
import { ManagerTodoFirstElements } from '../model/ManagerTodoFirstElements'
import { TodoModal } from '@/shared/ui'

type ManagerTodoModalProps = {
  closeRequest: () => void
  modaltype: 'CREATE' | 'VIEW' | 'EDIT'
  manageType: 'TEAM' | 'PERSONAL' | undefined
  scheduleId?: string
}

export const ManagerTodoModal = ({
  closeRequest,
  modaltype,
  manageType,
  scheduleId,
}: ManagerTodoModalProps) => {
  const elements = ManagerTodoFirstElements(modaltype)

  const [title, setTitle] = useState(elements.title)
  const [description, setDescription] = useState(elements.description)
  const [selectedState, setSelectedState] = useState(elements.selectedState)
  const [endDate, setEndDate] = useState(elements.endDate)
  const [reminder, setReminder] = useState(elements.remindRequests)
  const [userIds, setUserIds] = useState<string[]>([])
  const [isRequired, setIsRequired] = useState(elements.isEssentialYn)

  const headTitle = useMemo(() => {
    switch (modaltype) {
      case 'CREATE':
        return '할 일 등록'
      case 'VIEW':
        return null
    }
  }, [modaltype])

  const handleOnClickSave = () => {
    // 등록 api 보내기
    // 필수 입력값 검사
    // scheduleSourceTypeCd = "GLOBAL" or "TEAM" or "PERSONAL"
    // isEssentialYn = "Y" or "N"
    // isEnrollYn = "Y" or "N" -> 미등록 공지에서 등록한놈인지 (이 모달에선 N 고정)
    console.log(
      title,
      scheduleId,
      description,
      selectedState,
      endDate,
      reminder,
      userIds,
      isRequired,
    )
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
          <TodoModal.Status selectedState={selectedState} setSelectedState={setSelectedState} />
          <TodoModal.Button saveRequest={handleOnClickSave} />
        </TodoModal.Flex>

        <TodoModal.DetailsSection>
          <TodoModal.Assignee
            manageType={manageType}
            user={elements.user}
            userType='manager'
            userIds={userIds}
            setUserIds={setUserIds}
          />
          <TodoModal.Manager createUser={elements.createUser} userType='manager' />
          <TodoModal.EndDate endDate={endDate} setEndDate={setEndDate} />

          {manageType === 'PERSONAL' ? null : (
            <TodoModal.Required isRequired={isRequired} setIsRequired={setIsRequired} />
          )}

          <TodoModal.Reminder reminder={reminder} setReminder={setReminder} />
        </TodoModal.DetailsSection>
      </TodoModal.RightSection>
    </TodoModal>
  )
}
