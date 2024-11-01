import { useNavigate } from 'react-router-dom'
import { useSetLoginStateStore } from '@/entities/session'

type Action = () => void

type LoginButtonProps = {
  classname?: string
  label: string
}

export const LoginButton = ({
  classname = 'bg-color-bg-interactive-primary text-color-text-interactive-inverse',
  label,
}: LoginButtonProps) => {
  const navigate = useNavigate()
  // 임시 로그인 상태 확인 store
  const setLoginState = useSetLoginStateStore()

  const handleOnClickButton = (label: string) => {
    const actions: Record<string, Action> = {
      로그인: () => {
        setLoginState(true)
        navigate('/main')
      },
      // 싸피 로그인 생기면 로직 추가
    }

    const defaultAction = () => {
      console.error('동작이 지정되지 않은 버튼입니다.')
    }

    ;(actions[label] || defaultAction)()
  }

  return (
    <button
      onClick={() => handleOnClickButton(label)}
      className={`w-full py-spacing-16 body-lg-medium rounded-radius-8 ${classname}`}
    >
      {label}
    </button>
  )
}
