import { Navbar } from '@/widgets/navbar'
import { Outlet } from 'react-router-dom'

export const baseLayout = (
  <div>
    <Navbar />
    <Outlet />
  </div>
)
