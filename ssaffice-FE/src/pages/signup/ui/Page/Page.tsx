import { SignupForm } from '@/features/session/signup'

export const SignupPage = () => {
  return (
    <div className='flex flex-col w-full pt-[8rem] items-center'>
      <div className='flex flex-col items-center w-[28.375rem]'>
        <div className='flex flex-col w-full'>
          <h1 className='self-center text-color-text-primary heading-desktop-3xl w-fit px-spacing-64 mb-spacing-64'>
            SSAFICE 회원가입
          </h1>
        </div>
        <SignupForm />
      </div>
    </div>
  )
}
