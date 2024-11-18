import { useRef, useState } from 'react'
import { DropDown } from '@/shared/ui'
import { useClickOutsideToggle } from '@/shared/model'
import { UserIcon, PasswordResetIcon, LogoutIcon } from '@/assets/svg'
import { useNavigate } from 'react-router-dom'
import { useSetLoginStateStore } from '@/entities/session/index.ts'

export const ClickProfileButton = () => {
  const [isOpen, setIsOpen] = useState(false)
  const dropDownRef = useRef<HTMLDivElement | null>(null)
  const navigate = useNavigate()
  const setIsAuthenticated = useSetLoginStateStore()

  useClickOutsideToggle(dropDownRef, setIsOpen)

  const handleOnClickUserIcon = () => {
    setIsOpen(!isOpen)
  }

  const handleOnClickPasswordReset = () => {
    console.log('비밀번호 변경')
  }

  const handleOnClickLogout = () => {
    localStorage.removeItem('access_token')
    setIsAuthenticated(false)
    navigate('/login')
  }

  return (
    <div ref={dropDownRef} className='relative'>
      <button
        onClick={handleOnClickUserIcon}
        className={`p-1 rounded-full ${isOpen ? 'bg-color-bg-interactive-secondary-press' : 'hover:bg-color-bg-interactive-secondary-hover'}`}
      >
        <UserIcon className='w-7' />
      </button>

      {/* DropDown 컴포넌트 */}
      <DropDown
        isOpen={isOpen}
        isShadow={true}
        isDivide={true}
        width='w-[23.25rem]'
        position='right-0 mt-spacing-8'
      >
        <DropDown.Content isPaddingY={true}>
          <DropDown.Image>
            <img
              src='https://i.pinimg.com/564x/4d/b2/42/4db2422c74f12f70391ec386bf95e4db.jpg'
              alt='내 프로필 사진'
              className='w-10 rounded-full aspect-square'
            />
          </DropDown.Image>
          <DropDown.Title>곽성재(교육생)</DropDown.Title>
          <DropDown.SubTitle>seongJ@gamil.com</DropDown.SubTitle>
        </DropDown.Content>

        <DropDown.Content
          onClickEvent={handleOnClickPasswordReset}
          isHover={true}
          isPaddingY={true}
        >
          <DropDown.Image>
            <PasswordResetIcon className='w-4' />
          </DropDown.Image>
          <DropDown.SubTitle color='text-color-text-primary'>비밀번호 변경</DropDown.SubTitle>
        </DropDown.Content>

        <DropDown.Content onClickEvent={handleOnClickLogout} isHover={true} isPaddingY={true}>
          <DropDown.Image>
            <LogoutIcon className='w-4' />
          </DropDown.Image>
          <DropDown.SubTitle color='text-color-text-danger'>로그아웃</DropDown.SubTitle>
        </DropDown.Content>
      </DropDown>
    </div>
  )
}
