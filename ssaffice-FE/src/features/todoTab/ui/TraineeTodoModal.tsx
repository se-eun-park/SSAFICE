import { useMemo, useState } from 'react'
import { TraineeTodoFirstElements } from '../model/TraineeTodoFirstElements'
import { TodoModal } from '@/shared/ui'

type TraineeTodoModalProps = {
  closeRequest: () => void
  modaltype: 'CREATE' | 'VIEW' | 'EDIT'
  scheduleId?: string
}

export const TraineeTodoModal = ({
  closeRequest,
  modaltype,
  scheduleId,
}: TraineeTodoModalProps) => {
  const elements = TraineeTodoFirstElements(modaltype)

  const [modalType, setModalType] = useState(modaltype)
  const [title, setTitle] = useState(elements.title)
  const [description, setDescription] = useState(elements.description)
  const [selectedState, setSelectedState] = useState(elements.selectedState)
  const [endDate, setEndDate] = useState(elements.endDate)
  const [reminder, setReminder] = useState(elements.remindRequests)

  const headTitle = useMemo(() => {
    switch (modalType) {
      case 'CREATE':
        return '할 일 등록'
      case 'VIEW':
        return null
      case 'EDIT':
        return '할 일 수정'
    }
  }, [modalType])

  const handleOnClickSave = () => {
    // 등록 api 보내기
    // 필수 입력값 검사
    // scheduleSourceTypeCd = "GLOBAL" or "TEAM" or "PERSONAL"
    // isEssentialYn = "Y" or "N"
    // isEnrollYn = "Y" or "N" -> 미등록 공지에서 등록한놈인지 (이 모달에선 N 고정)
    console.log(title, description, selectedState, endDate, reminder)
    closeRequest()
  }

  const handleOnClickEdit = () => {
    setModalType('EDIT')
  }

  const handleOnClickEditSave = () => {
    // 일정 수정 사항 검사 후 있으면 api 보내기
    console.log(scheduleId, title, description, selectedState, endDate, reminder)
    setModalType('VIEW')
    // 수정사항 없으면 그냥 view로 변경
  }

  return (
    <TodoModal modaltype={modalType}>
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
          <TodoModal.Button
            saveRequest={handleOnClickSave}
            editRequest={handleOnClickEdit}
            saveEditRequest={handleOnClickEditSave}
          />
        </TodoModal.Flex>

        <TodoModal.DetailsSection>
          <TodoModal.Assignee
            user={elements.user}
            userType='trainee'
            userIds={[]} // type error 방지를 위해 빈 배열 전달
            setUserIds={() => []}
          />
          <TodoModal.Manager createUser={elements.createUser} userType='trainee' />
          <TodoModal.EndDate endDate={endDate} setEndDate={setEndDate} />
          <TodoModal.Reminder reminder={reminder} setReminder={setReminder} />
        </TodoModal.DetailsSection>
      </TodoModal.RightSection>
    </TodoModal>
  )
}
