import { useState } from 'react'
import { LoginButton } from './LoginButton'
import ssafyIcon from '/img/ssafy-icon.png'
import { instance } from '@/shared/model/index.ts'
import { useNavigate } from 'react-router-dom'
import { useLoginStateStore, useSetLoginStateStore } from '@/entities/session/index.ts'
import { useQuery } from '@tanstack/react-query'
// import { useLoginFormModel } from '../model/useLoginFormModel'
// import { CommonModal } from '@/shared/ui/CommonModal/CommonModal'
// import { ModalName } from '@/shared/model'

export const LoginForm = () => {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const setIsAuthenticated = useSetLoginStateStore()
  const isAuthenticated = useLoginStateStore()
  const navigate = useNavigate()

  useQuery({
    queryKey: ['user', isAuthenticated],
    queryFn: async () => {
      const { data } = await instance.get('/api/users/me')
      if (data) {
        data?.roles[0].roleId === 'ROLE_ADMIN' ? navigate('/pro') : navigate('/main')
      }
    },
    enabled: isAuthenticated,
  })

  async function handleLogin() {
    try {
      const loginResponse = await instance.post('/api/auth/login', {
        username: email,
        password: password,
      })

      const response = loginResponse.data
      if (response) {
        setIsAuthenticated(true)
      }
    } catch (err) {
      console.error(err)
    }
  }

  async function handleSSOLogin() {
    try {
      const response = await instance.get('/api/sso/providers/SSAFY/authorization-uri')

      const url = response.data
      if (url) {
        window.location.href = url
      }
    } catch (err) {
      console.error(err)
    }
  }

  // const [modalName, setModalName] = useState<ModalName>('LoginFail')

  return (
    <div className='flex flex-col w-full'>
      <form
        onSubmit={(e) => {
          e.preventDefault()
          handleLogin()
        }}
      >
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
          onClick={handleSSOLogin}
          className='border text-color-text-info-bold bg-color-bg-info-subtle border-color-border-info-subtle'
        />
      </form>
    </div>
  )
}
