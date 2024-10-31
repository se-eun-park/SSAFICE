import { RouteToLoginButton, NotificationButton, ProfileButton } from '@/features/navbar'
import { Logo } from '@/assets/svg'
import { useLoginStateStore } from '@/entities/session'

export const Navbar = () => {
  // 임시 로그인 상태 확인용 스토어
  const isLogin = useLoginStateStore()

  return (
    <header className='sticky top-0 flex justify-between w-full border-b px-spacing-80 bg-color-bg-primary h-fit py-spacing-20 border-color-border-tertiary'>
      <Logo className='w-52' />
      {isLogin ? (
        <div className='flex items-center gap-x-spacing-40'>
          <NotificationButton />
          <ProfileButton />
        </div>
      ) : (
        <RouteToLoginButton />
      )}
    </header>
  )
}
