import { LoginButton } from './LoginButton'

export const LoginForm = () => {
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
              className='flex w-full border border-color-border-secondary rounded-radius-8 placeholder:body-md-medium placeholder:color-text-disabled py-spacing-12 px-spacing-16'
              placeholder='EMAIL'
            />
          </div>
          <div className='flex flex-col gap-spacing-12'>
            <div className='flex justify-start text-color-text-tertiary body-md-semibold'>
              비밀번호
            </div>
            <input
              type='password'
              className='flex w-full border border-color-border-secondary rounded-radius-8 placeholder:body-md-medium placeholder:color-text-disabled py-spacing-12 px-spacing-16'
              placeholder='PASSWORD'
            />
          </div>
        </div>
        <LoginButton label='로그인' />
      </div>

      {/* 선 위의 글자 작업 중 */}
      <div className='relative flex flex-col items-center w-full h-spacing-32'>
        <div className='flex justify-center items-center w-full border-t border-color-text-disabled'>
          {/* 선을 CSS로 구현 */}
        </div>
        <span className='absolute bg-white body-sm-medium text-color-text-disabled z-10'>
          또는 다음으로 계속
        </span>
      </div>

      <LoginButton
        label='SSAFY 계정으로 로그인'
        classname='border bg-color-bg-primary border-color-border-primary text-color-text-interactive-secondary'
      />
    </div>
  )
}
