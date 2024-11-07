import { useEffect } from 'react'

export const useClickOutsideToggle = (
  ref: React.RefObject<HTMLDivElement>,
  setIsOpen: React.Dispatch<React.SetStateAction<boolean>>,
) => {
  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (ref.current && !ref.current.contains(event.target as Node)) {
        setIsOpen(false)
      }
    }

    document.addEventListener('click', handleClickOutside)

    return () => {
      document.removeEventListener('click', handleClickOutside)
    }
  }, [ref])
}
