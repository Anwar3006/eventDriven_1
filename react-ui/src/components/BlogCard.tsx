import { Heart, MessageSquare } from "lucide-react";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "./ui/card";
import { Avatar, AvatarFallback, AvatarImage } from "@radix-ui/react-avatar";
import { formatDistanceToNow } from "date-fns";
import { BlogPostResponse } from "@/types/blogPostResponse";
import { Button } from "./ui/button";
import { useNavigate } from "react-router-dom";
import slugify from "react-slugify";

const BlogCard = ({ blog }: { blog: BlogPostResponse }) => {
  const navigate = useNavigate();

  const date = blog.createdAt
    ? formatDistanceToNow(new Date(blog.createdAt), { addSuffix: true })
    : "-";

  const OnButtonClick = (e: React.MouseEvent) => {
    e.preventDefault();

    //navigate to blog page
    const postTitleSlug = slugify(blog.title, { delimiter: "_" });
    navigate(`/posts/${postTitleSlug}`);
  };

  return (
    <Card className="hover:shadow-lg transition-shadow duration-300 mb-6">
      <img
        src={blog.coverImg}
        alt={blog.title}
        className="w-full h-48 object-cover rounded-t-lg"
      />
      <CardHeader>
        <div className="flex items-center space-x-4 mb-2">
          <Avatar>
            <AvatarImage src={blog.author.avatarUrl} />
            <AvatarFallback className="p-2 rounded-full border border-zinc-900 bg-sky-100">
              {blog.author.name
                .split(" ")
                .map((n: any) => n[0])
                .join("")}
            </AvatarFallback>
          </Avatar>
          <div>
            <CardTitle className="text-xl">{blog.title}</CardTitle>
            <CardDescription className="text-sm text-gray-500">
              {blog.author.name} • {date}
            </CardDescription>
          </div>
        </div>
      </CardHeader>
      <CardContent>
        <p className="text-gray-600 mb-4">{blog.excerpt}</p>

        <div className="flex justify-between items-center">
          <div className="flex items-center space-x-4">
            <div className="flex items-center space-x-1">
              <Heart className="w-5 h-5 text-red-500" fill="red" />
              <span>{3}</span>
            </div>
            <div className="flex items-center space-x-1">
              <MessageSquare className="w-5 h-5 text-blue-500" />
              <span>{5}</span>
            </div>
          </div>
          <Button
            className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 transition-colors"
            onClick={OnButtonClick}
          >
            Read More
          </Button>
        </div>
      </CardContent>
    </Card>
  );
};

export default BlogCard;
