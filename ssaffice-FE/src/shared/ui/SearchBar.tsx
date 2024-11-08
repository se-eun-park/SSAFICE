import { SearchIcon } from '@/assets/svg'

export const SearchBar = () => {
  return (
    <div
      className='
        flex justify-start gap-spacing-16
        w-full h-[56px] p-spacing-16
        border border-color-border-disabled border-spacing-1
        rounded-radius-16
        '
    >
      <div
        className='
        flex justify-center items-center
        w-[18px]
        '
      >
        <SearchIcon />
      </div>
      <input
        type='text'
        placeholder='검색'
        className='
        flex-1
        placeholder:text-color-text-disabled placeholder:body-md-medium
        focus:outline-none
      '
      />
    </div>
  )
}
