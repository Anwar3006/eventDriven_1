import axiosClient from "@/lib/axiosClient";

export const SubscribeUser = async (userId: string) => {
  try {
    axiosClient.put(`/subscription/${userId}`);
  } catch (error) {
    console.error("@SubscribeUserError: ", error);
    throw error;
  }
};

export const UnSubscribeUser = async (userId: string) => {
  try {
    axiosClient.delete(`/subscription/${userId}`);
  } catch (error) {
    console.error("@UnSubscribeUserError: ", error);
    throw error;
  }
};
