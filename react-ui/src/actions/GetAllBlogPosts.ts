import axiosClient from "@/lib/axiosClient";

export const GetAllBlogPosts = async () => {
  try {
    const allPosts = await axiosClient.get("/posts/all");
    if (!allPosts.data || allPosts.data.length === 0) {
      throw new Error("No posts found");
    }

    return allPosts.data;
  } catch (error) {
    console.error("@GetAllBlogPostsError: ", error);
    throw error;
  }
};
