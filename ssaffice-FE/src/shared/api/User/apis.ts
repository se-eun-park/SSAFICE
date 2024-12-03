import { instance } from '@/shared/api'

import type { postUserSignupDataResponse } from './types'

export const postUserSignup = async (userId: number | null, data: postUserSignupDataResponse) => {
  const response = await instance.post(`/api/users/${userId}`, data).then((res) => res.data)
  return response
}
