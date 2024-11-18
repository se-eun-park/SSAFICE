import { useSetLoginStateStore } from '@/entities/session'
import { instance } from '@/shared/api'
import { useQuery } from '@tanstack/react-query'
import { useNavigate } from 'react-router-dom'

export const SSORedirect = () => {
  const navigate = useNavigate()
  const setIsAuthenticated = useSetLoginStateStore()
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
      if (data) {
        setIsAuthenticated(true)
        navigate('/login')
      }
      return data
    },
  })

  return null
}
