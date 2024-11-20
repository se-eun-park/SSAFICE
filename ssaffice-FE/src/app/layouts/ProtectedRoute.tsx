import { useLoginStateStore } from '@/entities/session'
import { useQuery } from '@tanstack/react-query'
import { useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { instance } from '@/shared/api'

async function fetchUser() {
  const { data } = await instance.get('/api/users/me')
  return data
}

export default function ProtectedRoute({
  children,
  role,
}: {
  children: React.ReactNode
  role: string
}) {
  const isAuthenticated = useLoginStateStore()
  const navigate = useNavigate()
  const { data: user } = useQuery({
    queryKey: ['roleId'],
    queryFn: fetchUser,
    enabled: isAuthenticated,
  })

  useEffect(() => {
    if (
      !isAuthenticated ||
      (user?.roles[0]?.roleId &&
        user.roles[0].roleId !== role &&
        user.roles[0].roleId !== 'ROLE_SYSADMIN')
    ) {
      navigate('/login')
    }
  }, [isAuthenticated, user, role, navigate])

  if (
    !isAuthenticated ||
    (user?.roles[0]?.roleId !== role && user?.roles[0]?.roleId !== 'ROLE_SYSADMIN')
  ) {
    return null
  }

  return <>{children}</>
}
