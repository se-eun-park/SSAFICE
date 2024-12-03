import { LoginForm } from '@/features/session/login'

export const LoginPage = () => {
  return (
    <div className='flex flex-col w-full pt-[8rem] items-center'>
      <div className='flex flex-col items-center w-[28.375rem]'>
        <div className='flex flex-col w-full'>
          <h1 className='self-center text-color-text-primary heading-desktop-3xl min-w-max px-spacing-64 mb-spacing-64'>
            SSAFICE 시작하기
          </h1>
          <LoginForm />
        </div>
      </div>
    </div>
  )
}
