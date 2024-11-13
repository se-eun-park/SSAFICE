export const GetAnnouncement = () => {
  return (
    <div>
      <h2 className='heading-desktop-lg mb-spacing-4 text-color-text-primary min-w-max'>공지</h2>

      <ul className='flex flex-col border-t px-spacing-4 py-spacing-10 border-color-border-info gap-y-spacing-10 text-color-text-primary'>
        <li className='flex items-center body-md-medium min-w-max'>
          전체 <span className='ml-spacing-8'>720</span>
        </li>
        <li className='flex items-center body-md-medium min-w-max'>
          중요 <span className='ml-spacing-8 text-color-text-danger'>365</span>
        </li>
        <li className='flex items-center body-md-medium min-w-max'>
          등록 <span className='ml-spacing-8 text-color-text-info-bold'>120</span>
        </li>
      </ul>
    </div>
  )
}
