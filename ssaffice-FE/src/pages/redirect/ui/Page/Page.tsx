import { instance } from '@/shared/model'
import { useQuery } from '@tanstack/react-query'

export const SSORedirect = () => {
  const params = new URLSearchParams(window.location.search)
  const codeParam = params.get('code')
  if (codeParam) console.log(codeParam)
  const {} = useQuery({
    queryKey: ['SSO', codeParam],
    queryFn: async () => {
      const { data } = await instance.post('/api/sso/providers/SSAFY/login', codeParam, {
        headers: {
          'Content-Type': 'text/plain',
        },
      })
      if (data) {
        console.log(data)
      }
    },
  })

  return null
}
