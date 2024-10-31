import { useNavigate } from 'react-router-dom'

export const RouteToLoginButton = () => {
  const navigate = useNavigate()

  const routeToLogin = () => {
    navigate('/login')
  }

  return (
    <button
      onClick={routeToLogin}
      className='w-fit h-fit bg-color-bg-interactive-primary px-spacing-28 py-spacing-10 rounded-radius-32'
    >
      <span className='text-color-text-interactive-inverse heading-desktop-lg'>로그인</span>
    </button>
  )
}
