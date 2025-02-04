import { useLoginStateStore } from '@/entities/session'
import { useTraineeScheduleDetail } from '@/entities/todoTab'
import { instance } from '@/shared/api'
import { useQuery } from '@tanstack/react-query'

type TraineeTodoFirstElementsProps = {
  modaltype: 'CREATE' | 'VIEW' | 'EDIT'
  scheduleId: number
}

export const TraineeTodoFirstElements = ({
  modaltype,
  scheduleId,
}: TraineeTodoFirstElementsProps) => {
  const isAuthenticated = useLoginStateStore()

  const { data: user } = useQuery({
    queryKey: ['userData'],
    queryFn: async () => {
      const { data } = await instance.get('/api/users/me')
      return data
    },
    enabled: isAuthenticated,
  })

  switch (modaltype) {
    case 'CREATE':
      return {
        title: '',
        description: '',
        selectedState: 'TODO',
        manageType: '',
        user: {
          name: user?.name,
          profileImgUrl: user?.profileImgUrl,
        },
        createUser: {
          name: user?.name,
          profileImgUrl: user?.profileImgUrl,
        },
        endDate: '',
        remindRequests: [],
      }
    case 'VIEW':
      const { data: detail, isLoading } = useTraineeScheduleDetail(scheduleId)

      if (isLoading)
        return {
          title: '',
          description: '',
          selectedState: 'Loading',
          manageType: '',

          user: {
            name: '',
            profileImgUrl: '',
          },
          createUser: {
            name: '',
            profileImgUrl: '',
          },
          endDate: '',
          remindRequests: [],
        }

      const remindList = Array.isArray(detail?.remindSummarys)
        ? detail.remindSummarys.map((remind: any) => {
            return {
              remindTypeCd: remind.remindTypeCd,
              remindDateTime: remind.remindDateTime,
            }
          })
        : []

      return {
        title: detail?.title,
        description: detail?.memo,
        selectedState: detail?.scheduleStatusTypeCd,
        manageType: detail?.scheduleSourceTypeCd,

        user: {
          name: detail?.chargeUser?.name,
          profileImgUrl: detail?.chargeUser?.profileImgUrl,
        },
        createUser: {
          name: detail?.createUser?.name,
          profileImgUrl: detail?.createUser?.profileImgUrl,
        },
        endDate: detail?.endDateTime ? detail?.endDateTime.split('T')[0] : '',
        remindRequests: [...remindList],
      }
    case 'EDIT':
      return {
        title: '',
        description: '',
        selectedState: 'Loading',
        manageType: '',

        user: {
          name: '',
          profileImgUrl: '',
        },
        createUser: {
          name: '',
          profileImgUrl: '',
        },
        endDate: '',
        remindRequests: [],
      }
  }
}
