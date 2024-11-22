import { create } from 'zustand'

type SessionStoreProps = {
  isAuthenticated: boolean
  setIsAuthenticated: (value: boolean) => void
  userId: number | null
  setUserId: (value: number) => void
}

const useSessionStore = create<SessionStoreProps>((set) => ({
  isAuthenticated: !!localStorage.getItem('access_token'),
  setIsAuthenticated: (value) => set({ isAuthenticated: value }),

  userId: null,
  setUserId: (value: number) => set({ userId: value }),
}))

export const useLoginStateStore = () => useSessionStore((state) => state.isAuthenticated)
export const useSetLoginStateStore = () => useSessionStore((state) => state.setIsAuthenticated)

export const useUserIdStore = () => useSessionStore((state) => state.userId)
export const useSetUserIdStore = () => useSessionStore((state) => state.setUserId)
