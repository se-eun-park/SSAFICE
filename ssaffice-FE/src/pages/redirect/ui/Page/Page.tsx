import { useSetLoginStateStore, useSetUserIdStore } from '@/entities/session'
import { instance } from '@/shared/api'
import { useQuery } from '@tanstack/react-query'
import { useNavigate } from 'react-router-dom'

export const SSORedirect = () => {
  const navigate = useNavigate()
  const setIsAuthenticated = useSetLoginStateStore()
  const setIsUserId = useSetUserIdStore()
  const params = new URLSearchParams(window.location.search)
  const codeParam = params.get('code')
  const {} = useQuery({
    queryKey: ['SSO', codeParam],
    queryFn: async () => {
      const { data } = await instance.post('/api/sso/providers/SSAFY/login', codeParam, {
        headers: {
          'Content-Type': 'text/plain',
        },
      })
      if (data && data.success) {
        setIsAuthenticated(true)
        navigate('/main')
      } else if (data && !data.success) {
        setIsUserId(data.userId)
        navigate('/signup')
      } else {
        alert('SSAFY 교육생이 아닙니다.')
        navigate('/login')
      }
      return data
    },
  })

  return null
}
