import { useState } from 'react'
import { LoginButton } from './LoginButton'
import ssafyIcon from '/img/ssafy-icon.png'
import { instance } from '@/shared/model/index.ts'
import { useNavigate } from 'react-router-dom'
import { useSetLoginStateStore } from '@/entities/session/index.ts'
// import { useLoginFormModel } from '../model/useLoginFormModel'
// import { CommonModal } from '@/shared/ui/CommonModal/CommonModal'
// import { ModalName } from '@/shared/model'

export const LoginForm = () => {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const setIsAuthenticated = useSetLoginStateStore()

  const navigate = useNavigate()

  function handleLogin() {
    instance
      .post('/api/auth/login', {
        username: email,
        password: password,
      })
      .then((res) => {
        const response = res.data
        if (response) {
          setIsAuthenticated(true)
          navigate('/main')
        }
      })
      .catch((err) => {
        console.error(err)
      })
  }

  // const [modalName, setModalName] = useState<ModalName>('LoginFail')

  return (
    <div className='flex flex-col w-full'>
      <div className='flex flex-col gap-spacing-64'>
        <div className='flex flex-col gap-spacing-24'>
          <div className='flex flex-col gap-spacing-12'>
            <div className='flex justify-start text-color-text-tertiary body-md-semibold'>
              이메일
            </div>
            <input
              type='email'
              className='flex w-full border px-spacing-16 py-spacing-12 border-color-border-secondary rounded-radius-8 placeholder:color-text-disabled placeholder:body-md-medium'
              placeholder='EMAIL'
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>
          <div className='flex flex-col gap-spacing-12'>
            <div className='flex justify-start text-color-text-tertiary body-md-semibold'>
              비밀번호
            </div>
            <input
              type='password'
              className='flex w-full border px-spacing-16 py-spacing-12 border-color-border-secondary rounded-radius-8 placeholder:color-text-disabled placeholder:body-md-medium'
              placeholder='PASSWORD'
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>
        </div>
        <LoginButton label='로그인' onClick={handleLogin} />

        {/* <CommonModal name={modalName} opened={isOpen} closeRequest={close} /> */}
      </div>

      <div className='relative flex flex-col items-center justify-center w-full h-spacing-32'>
        <div className='absolute inset-0 flex items-center px-spacing-4'>
          <div className='w-full border-t border-color-border-primary' />
        </div>
        <span className='relative z-10 px-2 bg-white text-color-text-disabled body-sm-medium'>
          또는
        </span>
      </div>

      <LoginButton
        label='SSAFY 로그인'
        icon={ssafyIcon}
        onClick={() => {}}
        className='border text-color-text-info-bold bg-color-bg-info-subtle border-color-border-info-subtle'
      />
    </div>
  )
}
