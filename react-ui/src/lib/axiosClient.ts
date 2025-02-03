import axios from "axios";

const baseURL =
  import.meta.env.VITE_JAVA_API_URL ||
  `http://localhost:8080/api/${import.meta.env.VITE_JAVA_API_VERSION}`;

const axiosClient = axios.create({
  baseURL,
  headers: {
    "Content-Type": "application/json",
  },
});

// Request interceptor
axiosClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("jwt");
    if (
      token &&
      config.url !== "/auth/register" &&
      config.url !== "/auth/login"
    ) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);
export default axiosClient;
