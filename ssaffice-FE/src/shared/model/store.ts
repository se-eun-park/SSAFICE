import { create } from 'zustand'
import { persist } from 'zustand/middleware'

type TabStateStoreProps = {
  isTabOpen: boolean
  isAnimation: boolean
  setIsTabOpen: (value: boolean) => void
  setIsAnimation: (value: boolean) => void
}

const useTabStateStore = create(
  persist<TabStateStoreProps>(
    (set) => ({
      isTabOpen: true,
      isAnimation: false,

      setIsTabOpen: () => set((state) => ({ isTabOpen: !state.isTabOpen })),
      setIsAnimation: (value) => set({ isAnimation: value }),
    }),
    {
      name: 'tab-state',
    },
  ),
)

export const useIsTabOpenStore = () => useTabStateStore((state) => state.isTabOpen)
export const useSetIsTabOpenStore = () => useTabStateStore((state) => state.setIsTabOpen)

export const useIsAnimationStore = () => useTabStateStore((state) => state.isAnimation)
export const useSetIsAnimationStore = () => useTabStateStore((state) => state.setIsAnimation)
