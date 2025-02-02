import axiosClient from "@/lib/axiosClient";
import { RegisterInputProps } from "@/pages/Register";

export const RegisterUser = async (data: RegisterInputProps) => {
  try {
    const userauth = await axiosClient.post("/auth/register", data);
    if (!userauth) {
      throw new Error("Failed to register user");
    }
    return userauth;
  } catch (error) {
    console.error("@RegisterUserError: ", error);
  }
};
