import axios from "axios";

const baseURL =
  process.env.JAVA_API_URL ||
  `http://localhost:8080/api/${process.env.JAVA_API_VERSION}`;

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
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    } else {
      console.log("No token found. User not authenticated.");
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);
export default axiosClient;
