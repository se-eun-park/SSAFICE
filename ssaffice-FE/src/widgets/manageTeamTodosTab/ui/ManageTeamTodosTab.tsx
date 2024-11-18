import { CalendarIcon, EditIcon } from '@/assets/svg'
import { SelectTodoState } from '@/shared/ui'
import { SelectTodoSortCondition } from '@/features/todoTab/ui/SelectTodoSortCondition'
import { HoverButton, RefreshMattermostConnection } from '@/shared/ui'
import { AnnouncementList } from '@/widgets/announcementTab'
import { TodoList } from '@/widgets/todoTab'
import { useState } from 'react'

export const ManageTeamTodosTab = () => {
  // event
  const handleOnClickCalendar = () => {
    console.log('나중엔 캘린더가 열림')
  }

  const handleOnClickCreateTodo = () => {
    console.log('나중엔 할 일 등록 모달이 열림')
  }

  const [selectedState, setSelectedState] = useState('default')

  return (
    <div className='flex w-full h-full gap-spacing-32 px-spacing-64'>
      {/* 왼쪽 영역 */}
      <div
        className='
        flex flex-col gap-spacing-24 
        p-spacing-32 w-[640px] h-full 
        bg-color-bg-secondary
        '
      >
        <div className='text-color-text-primary heading-desktop-xl'>전체 공지</div>
        <RefreshMattermostConnection />
        <div className='h-[800px] px-spacing-16 bg-color-bg-tertiary overflow-y-scroll'>
          <AnnouncementList />
        </div>
      </div>

      {/* 오른쪽 영역 */}
      <div
        className='
        flex flex-col gap-spacing-24 
        p-spacing-32 w-[640px] h-full 
        bg-color-bg-secondary
        '
      >
        <div
          className='
          flex justify-between
          text-color-text-primary heading-desktop-xl
        '
        >
          <div>팀별 할 일 관리</div>

          <div className='flex gap-spacing-16'>
            <HoverButton
              icon={<CalendarIcon className='w-6' />}
              tooltip='캘린더'
              onClickEvent={handleOnClickCalendar}
            />
            <HoverButton
              icon={<EditIcon className='w-6' />}
              tooltip='할 일 등록'
              onClickEvent={handleOnClickCreateTodo}
            />
          </div>
        </div>
        <div className='flex flex-col gap-spacing-16'>
          <div className='flex gap-spacing-16'>
            <SelectTodoState
              actionType='filter'
              selectedState={selectedState}
              setSelectedState={setSelectedState}
            />
            <SelectTodoSortCondition />
          </div>
          <div className='h-[800px] px-spacing-16 bg-color-bg-tertiary overflow-y-scroll'>
            <TodoList />
          </div>
        </div>
      </div>
    </div>
  )
}
