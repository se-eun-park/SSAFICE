import { useState } from 'react'
import { LoginButton } from './LoginButton'
import { CommonModal } from '@/shared/ui/CommonModal/CommonModal'
import { LoginErrorModal } from './LoginErrorModal'

export const LoginForm = () => {
  const [isOpen, setIsOpen] = useState(false)
  const open = () => setIsOpen(true)
  const close = () => setIsOpen(false)

  return (
    <div className='flex flex-col w-full'>
      <div className='flex flex-col gap-spacing-64'>
        <div className='flex flex-col gap-spacing-24'>
          <div className='flex flex-col gap-spacing-12'>
            <div
              className='
              flex justify-start 
              text-color-text-tertiary body-md-semibold
              '
            >
              이메일
            </div>
            <input
              type='email'
              className='
              flex 
              w-full px-spacing-16 py-spacing-12
              border border-color-border-secondary 
              rounded-radius-8 
              placeholder:color-text-disabled placeholder:body-md-medium 
              '
              placeholder='EMAIL'
            />
          </div>
          <div className='flex flex-col gap-spacing-12'>
            <div
              className='
            flex justify-start 
            text-color-text-tertiary body-md-semibold
            '
            >
              비밀번호
            </div>
            <input
              type='password'
              className='
              flex 
              w-full px-spacing-16 py-spacing-12 
              border border-color-border-secondary 
              rounded-radius-8 
              placeholder:color-text-disabled placeholder:body-md-medium
              '
              placeholder='PASSWORD'
            />
          </div>
        </div>
        <LoginButton label='로그인' />
        <button type='button' onClick={open}>
          trigger
        </button>
        <CommonModal
          width='443px'
          height='207px'
          tsx={<LoginErrorModal errorType={`LoginFail`} />}
          opened={isOpen}
          closeRequest={close}
          hasShadow
        />
      </div>

      {/* 
          SSAFY 로그인을 진행하지 않게 되어 임시 주석 처리합니다.
      <div
        className='
      flex flex-col justify-center items-center 
      w-full h-spacing-32
      '
      >
        <span
          className='
        flex 
        text-color-text-disabled body-sm-medium
        bg-white 
        '
        >
          또는 다음으로 계속
        </span>
      </div>

      <LoginButton
        label='SSAFY 계정으로 로그인'
        classname='
        text-color-text-interactive-secondary
        bg-color-bg-primary border border-color-border-primary 
        '
      /> */}
    </div>
  )
}
