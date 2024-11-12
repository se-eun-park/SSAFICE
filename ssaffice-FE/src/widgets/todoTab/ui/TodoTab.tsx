import { TodoList } from './TodoList'
import { TodoBoard } from './TodoBoard'

import { HoverTitle, SelectTodoState } from '@/features/todoTab'
import { useIsTabOpenStore } from '@/shared/model'
import { TabLayout, HoverButton } from '@/shared/ui'
import { HamburgerMenuIcon, FastLeftArrowIcon, CalendarIcon, EditIcon } from '@/assets/svg'

export const TodoTab = () => {
  // store
  const isTabOpen = useIsTabOpenStore()

  // event
  const handleOnClickCalendar = () => {
    console.log('나중엔 캘린더가 열림')
  }

  const handleOnClickCreateTodo = () => {
    console.log('나중엔 할 일 등록 모달이 열림')
  }

  return (
    <TabLayout>
      <TabLayout.Header>
        <div className='flex justify-between w-full'>
          <HoverTitle
            mouseOverIcon={<FastLeftArrowIcon className='w-6 rotate-180' />}
            mouseOutIcon={<HamburgerMenuIcon className='w-6' />}
            title='할 일'
          />

          <div className='flex items-center gap-x-spacing-16'>
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
      </TabLayout.Header>

      {isTabOpen ? (
        <TabLayout.Add>
          <SelectTodoState actionType='filter' />
        </TabLayout.Add>
      ) : null}

      <TabLayout.Content>{isTabOpen ? <TodoList /> : <TodoBoard />}</TabLayout.Content>
    </TabLayout>
  )
}
