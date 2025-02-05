import axiosClient from "@/lib/axiosClient";
import slugify from "react-slugify";

export const GetBlogPost = async (identifier: string) => {
  try {
    identifier = slugify(identifier, { delimiter: " " });
    const blogPost = await axiosClient.get(`/posts/${identifier}`);
    if (!blogPost.data) {
      throw new Error("No blog post found");
    }

    return blogPost.data;
  } catch (error) {
    console.error("@GetBlogPostError: ", error);
    throw error;
  }
};
