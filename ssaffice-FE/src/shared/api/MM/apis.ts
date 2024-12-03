import { instance } from '@/shared/api'

import type { postMmSyncBody } from './types'

export const postMmSync = async (data: postMmSyncBody) => {
  const response = await instance
    .post('/api/mm/login', data)
    .then((res) => res.data)
    .catch((error) => {
      return { error: error.message }
    })

  return response
}
