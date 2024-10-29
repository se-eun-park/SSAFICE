import { createBrowserRouter } from 'react-router-dom'
import { NotFoundPage } from '@/pages/notFound'
import { LoginPage } from '@/pages/login'
import { LandingPage } from '@/pages/landing'
import { MainPage } from '@/pages/main'

export const appRouter = () => {
  return createBrowserRouter([
    {
      path: '/',
      errorElement: <NotFoundPage />,
      children: [
        {
          index: true,
          element: <LandingPage />,
        },
        {
          path: 'login',
          element: <LoginPage />,
        },
        {
          path: 'main',
          element: <MainPage />,
        },
      ],
    },
  ])
}
