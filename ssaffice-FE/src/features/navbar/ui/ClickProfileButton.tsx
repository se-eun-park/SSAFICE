import { useRef, useState } from 'react'
import { DropDown } from '@/shared/ui'
import { instance, useClickOutsideToggle } from '@/shared/model'
import { UserIcon, PasswordResetIcon, LogoutIcon } from '@/assets/svg'
import { useNavigate } from 'react-router-dom'
import { useLoginStateStore, useSetLoginStateStore } from '@/entities/session/index.ts'
import { useQuery } from '@tanstack/react-query'

export const ClickProfileButton = () => {
  const [isOpen, setIsOpen] = useState(false)
  const dropDownRef = useRef<HTMLDivElement | null>(null)
  const navigate = useNavigate()
  const isAuthenticated = useLoginStateStore()
  const setIsAuthenticated = useSetLoginStateStore()
  const { data } = useQuery({
    queryKey: ['userData'],
    queryFn: async () => {
      const response = await instance.get('/api/users/me')
      if (response) console.log(response.data)
      return response.data
    },
    enabled: isAuthenticated,
  })

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
        {data?.profileImgUrl ? (
          <img src={data?.profileImgUrl} className='object-cover object-center w-7 h-7' />
        ) : (
          <UserIcon className='w-7' />
        )}
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
              src={data?.profileImgUrl}
              alt='내 프로필 사진'
              className='w-10 rounded-full aspect-square'
            />
          </DropDown.Image>
          <DropDown.Title>{data}</DropDown.Title>
          <DropDown.SubTitle>{data?.email}</DropDown.SubTitle>
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
