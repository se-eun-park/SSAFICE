import {
  ClickRouteToLoginButton,
  // ClickNotificationButton,
  ClickProfileButton,
} from '@/features/navbar'
import { Logo } from '@/assets/svg'
import { useLoginStateStore } from '@/entities/session/index.ts'
import { useNavigate } from 'react-router-dom'

export const Navbar = () => {
  const isAuthenticated = useLoginStateStore()
  const navigate = useNavigate()

  return (
    <header className='sticky top-0 z-30 flex items-center justify-between w-full border-b px-spacing-40 md:px-spacing-80 bg-color-bg-primary h-[76px] py-spacing-16 border-color-border-tertiary'>
      <Logo
        className='cursor-pointer h-9'
        onClick={() => {
          !isAuthenticated && navigate('/')
        }}
      />
      {isAuthenticated ? (
        <div className='flex items-center gap-x-spacing-40'>
          {/* <ClickNotificationButton /> */}
          <ClickProfileButton />
        </div>
      ) : (
        <ClickRouteToLoginButton />
      )}
    </header>
  )
}
