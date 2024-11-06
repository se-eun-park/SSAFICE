export const TodoBoard = () => {
  return (
    <div className='flex w-full h-full gap-x-spacing-10'>
      <div className='w-full h-full bg-color-bg-tertiary'>해야 할 일</div>
      <div className='w-full h-full bg-color-bg-tertiary'>진행중</div>
      <div className='w-full h-full bg-color-bg-tertiary'>완료</div>
    </div>
  )
}
