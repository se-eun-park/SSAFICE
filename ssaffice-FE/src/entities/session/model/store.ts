import { create } from 'zustand'

type SessionStoreProps = {
  isAuthenticated: boolean
  setIsAuthenticated: (value: boolean) => void
}

const useSessionStore = create<SessionStoreProps>((set) => ({
  isAuthenticated: !!localStorage.getItem('access_token'),
  setIsAuthenticated: (value) => set({ isAuthenticated: value }),
}))

export const useLoginStateStore = () => useSessionStore((state) => state.isAuthenticated)
export const useSetLoginStateStore = () => useSessionStore((state) => state.setIsAuthenticated)
