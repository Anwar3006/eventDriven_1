import axiosClient from "@/lib/axiosClient";
import { blogPostInputSchema } from "@/zodSchema/blogPostInputSchema";
import { z } from "zod";

export const CreatePost = async (data: z.infer<typeof blogPostInputSchema>) => {
  try {
    const post = axiosClient.post("/posts", data);
    if (!post) {
      throw new Error("Failed to create post");
    }

    return post;
  } catch (error) {
    console.error("@CreatePostError: ", error);
    throw error;
  }
};
