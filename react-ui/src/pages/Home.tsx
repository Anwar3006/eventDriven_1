import React, { useState } from "react";

import BlogCard from "@/components/BlogCard";
import { useQuery } from "@tanstack/react-query";
import { GetAllBlogPosts } from "@/actions/GetAllBlogPosts";
import { BlogPostResponse } from "@/types/blogPostResponse";
import { Button } from "@/components/ui/button";
import PostCreation from "@/components/PostCreationDialog";

const BlogHomePage = () => {
  const [openDialog, setDialogOpen] = useState(false);

  const { data, isLoading } = useQuery<BlogPostResponse[]>({
    queryKey: ["all_posts"],
    queryFn: GetAllBlogPosts,
    refetchInterval: 6 * 60 * 1000, //6 minutes
  });

  if (isLoading) {
    return (
      <div className="flex justify-center items-center h-screen">
        <div className="animate-spin rounded-full h-32 w-32 border-t-2 border-b-2 border-blue-500"></div>
      </div>
    );
  }

  const OnButtonClick = (e: React.MouseEvent) => {
    e.preventDefault();
    setDialogOpen(true);
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <PostCreation openDialog={openDialog} setOpenDialog={setDialogOpen} />

      <div className="flex items-center justify-between mb-8">
        <h1 className="text-4xl font-bold mb-8 text-center">Recent Blogs</h1>
        <Button variant={"outline"} className="py-2" onClick={OnButtonClick}>
          Create a Post
        </Button>
      </div>

      <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
        {data!.map((blog) => (
          <BlogCard key={blog.id} blog={blog} />
        ))}
      </div>
      {data!.length === 0 && (
        <div className="text-center text-gray-500">
          No blogs found. Check back later!
        </div>
      )}
    </div>
  );
};

export default BlogHomePage;
