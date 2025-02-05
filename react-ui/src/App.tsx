import { lazy, Suspense } from "react";
import { Route, Routes } from "react-router-dom";
import LoadingPage from "./components/LoadingPage";

const LoginPage = lazy(() => import("@/pages/Login"));
const RegisterPage = lazy(() => import("@/pages/Register"));
const LandingPage = lazy(() => import("@/pages/LandingPage"));
const HomePage = lazy(() => import("@/pages/Home"));
const BlogPostPage = lazy(() => import("@/pages/BlogPost"));

function App() {
  return (
    <Suspense fallback={<LoadingPage />}>
      <Routes>
        <Route index element={<LandingPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/home" element={<HomePage />} />
        <Route path="/posts/:slug" element={<BlogPostPage />} />
      </Routes>
    </Suspense>
  );
}

export default App;
