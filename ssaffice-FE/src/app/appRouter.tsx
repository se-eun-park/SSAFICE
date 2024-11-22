import { createBrowserRouter } from 'react-router-dom'
import { NotFoundPage } from '@/pages/notFound'
import { LoginPage } from '@/pages/login'
import { SignupPage } from '@/pages/signup'
import { LandingPage } from '@/pages/landing'
import { MainPage } from '@/pages/main'
import { baseLayout } from './layouts/baseLayout'
import { ProPage } from '@/pages/pro'
import ProtectedRoute from '@/app/layouts/ProtectedRoute'
import { SSORedirect } from '@/pages/redirect'

export const appRouter = () => {
  return createBrowserRouter([
    {
      path: '/',
      element: baseLayout,
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
          path: 'signup',
          element: <SignupPage />,
        },
        {
          path: '/sso/providers/ssafy/callback',
          element: <SSORedirect />,
        },
        {
          path: 'main',
          element: (
            <ProtectedRoute role='ROLE_USER'>
              <MainPage />
            </ProtectedRoute>
          ),
        },
        {
          path: 'pro',
          element: (
            <ProtectedRoute role='ROLE_ADMIN'>
              <ProPage />
            </ProtectedRoute>
          ),
        },
      ],
    },
  ])
}
