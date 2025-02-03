import axiosClient from "@/lib/axiosClient";
import { LoginInputProps, RegisterInputProps } from "@/types/authInputs";

export const RegisterUser = async (data: RegisterInputProps) => {
  try {
    const userauth = await axiosClient.post("/auth/register", data);
    if (!userauth) {
      throw new Error("Failed to register user");
    }
    return userauth;
  } catch (error) {
    console.error("@RegisterUserError: ", error);
    throw error;
  }
};

export const LoginUser = async (data: LoginInputProps) => {
  try {
    const userauth = await axiosClient.post("/auth/login", data);
    if (!userauth) {
      throw new Error("Failed to login user");
    }

    localStorage.setItem("jwt", userauth.data.token);
    return userauth;
  } catch (error) {
    console.error("@RegisterUserError: ", error);
    throw error;
  }
};
