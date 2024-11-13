import { LoginButton } from './LoginButton'

export const LoginForm = () => {
  return (
    <div className='flex flex-col w-full gap-10'>
      <LoginButton label='로그인' />
      <LoginButton
        label='싸피 로그인'
        classname='border bg-color-bg-primary border-color-border-primary text-color-text-interactive-secondary'
      />
    </div>
  )
}
