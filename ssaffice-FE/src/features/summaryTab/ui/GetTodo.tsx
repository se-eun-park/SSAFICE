export const GetTodo = () => {
  return (
    <div>
      <h2 className='heading-desktop-lg mb-spacing-4 text-color-text-primary min-w-max'>할일</h2>

      <ul className='flex flex-col border-t px-spacing-4 py-spacing-10 border-color-border-info gap-y-spacing-10 text-color-text-primary'>
        <li className='flex items-center body-md-medium min-w-max'>
          전체 <span className='ml-spacing-8'>120</span>
        </li>
        <li className='flex items-center body-md-medium min-w-max'>
          진행 <span className='ml-spacing-8 text-color-text-info-bold'>96</span>
        </li>
        <li className='flex items-center body-md-medium min-w-max'>
          완료 <span className='ml-spacing-8 text-color-text-success'>24</span>
        </li>
      </ul>
    </div>
  )
}
