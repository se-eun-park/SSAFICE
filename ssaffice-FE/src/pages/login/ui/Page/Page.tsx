import { LoginForm } from '@/features/session/login'

export const LoginPage = () => {
  return (
    <div className='flex flex-col items-center w-full'>
      <div className='w-fit mt-spacing-128'>
        <h1 className='text-color-text-primary heading-desktop-3xl w-fit px-spacing-64 mb-spacing-64'>
          SSAFICE 시작하기
        </h1>
        <LoginForm />
      </div>
    </div>
  )
}
