import { useIsTabOpenStore, useSetIsTabOpenStore, useSetIsAnimationStore } from '@/shared/model'
import { SearchBar, TabLayout } from '@/shared/ui'
import { HamburgerMenuIcon } from '@/assets/svg'
import { TodoList } from './TodoList'
import { TodoBoard } from './TodoBoard'

export const TodoTab = () => {
  const isTabOpen = useIsTabOpenStore()
  const setIsTabOpen = useSetIsTabOpenStore()

  const setIsAnimation = useSetIsAnimationStore()

  const handleOnClickOpen = () => {
    setIsTabOpen(isTabOpen)
    setIsAnimation(false)
  }

  return (
    <TabLayout>
      <TabLayout.Header>
        <div className='flex gap-x-spacing-12'>
          {!isTabOpen && (
            <button onClick={handleOnClickOpen} className='animate-slideExpandFast'>
              <HamburgerMenuIcon className='w-6' />
            </button>
          )}
          <h1 className='min-w-max'>할 일</h1>
        </div>
      </TabLayout.Header>

      <TabLayout.Add>
        <SearchBar />
      </TabLayout.Add>

      <TabLayout.Content>{isTabOpen ? <TodoList /> : <TodoBoard />}</TabLayout.Content>
    </TabLayout>
  )
}
