import React, { useMemo, useState } from "react";
import {
  Calendar,
  Clock,
  Heart,
  Share2,
  BookmarkPlus,
  ChevronLeft,
  BellIcon,
} from "lucide-react";
import { Card, CardContent } from "@/components/ui/card";
import { Avatar, AvatarImage, AvatarFallback } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { useNavigate, useParams } from "react-router-dom";
import { useMutation, useQuery } from "@tanstack/react-query";
import { GetBlogPost } from "@/actions/GetBlogPost";
import { BlogPostResponse } from "@/types/blogPostResponse";
import { debounce } from "lodash";
import { SubscribeUser, UnSubscribeUser } from "@/actions/SubscribeUser";

const Blogdata = () => {
  const navigate = useNavigate();
  const params = useParams();

  const [isSubscribed, setIsSubscribed] = useState(false);

  const { data, isPending } = useQuery<BlogPostResponse>({
    queryKey: ["single_data"],
    queryFn: () => GetBlogPost(params.slug as string),
  });

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString("en-US", {
      year: "numeric",
      month: "long",
      day: "numeric",
    });
  };

  const onButtonClick = (e: React.MouseEvent) => {
    e.preventDefault();
    navigate("/home");
  };

  const { mutate } = useMutation({
    mutationFn: async (shouldSubscribe: boolean) => {
      if (shouldSubscribe) {
        await SubscribeUser(data?.id as string);
      } else {
        await UnSubscribeUser(data?.id as string);
      }
    },
    onError: (error, variables) => {
      // Revert the UI state on error
      setIsSubscribed(!variables);
      console.error("Subscription action failed:", error);
    },
  });

  const debouncedMutate = useMemo(
    () => debounce((shouldSubscribe: boolean) => mutate(shouldSubscribe), 1000),
    [mutate]
  );

  const handleSubscribe = (e: React.MouseEvent) => {
    e.preventDefault();

    const newState = !isSubscribed;
    setIsSubscribed(newState);
    debouncedMutate(newState);
  };

  return (
    <div className="max-w-4xl mx-auto px-4 py-8 bg-slate-100">
      {/* Header Section */}
      <div className="space-y-6 mb-8">
        <Button
          variant="default"
          asChild
          onClick={onButtonClick}
          className="cursor-pointer"
        >
          <div className="flex items-center justify-center">
            <ChevronLeft size={20} />
            <span>Back</span>
          </div>
        </Button>

        <h1 className="text-4xl font-bold tracking-tight py-6">
          {data?.title}
        </h1>

        <div className="flex items-center space-x-4">
          <Avatar className="h-12 w-12">
            <AvatarImage src={data?.author.avatarUrl} alt={data?.author.name} />
            <AvatarFallback>{data?.author.name.charAt(0)}</AvatarFallback>
          </Avatar>

          <div>
            <div className="font-medium">{data?.author.name}</div>
            <div className="flex items-center text-sm text-gray-500 space-x-4">
              <span className="flex items-center">
                <Calendar className="w-4 h-4 mr-1" />
                {formatDate(data?.createdAt!)}
              </span>
              <span className="flex items-center">
                <Clock className="w-4 h-4 mr-1" />5 min read
              </span>
            </div>
          </div>
        </div>
      </div>

      {/* Cover Image */}
      <div className="relative h-96 mb-8 rounded-lg overflow-hidden">
        <img
          src={"https://picsum.photos//1200?random=1"}
          alt="Cover"
          className="object-cover w-full h-full"
        />
      </div>

      {/* Action Bar */}
      <Card className="mb-8 sticky top-4 z-10">
        <CardContent className="flex justify-between items-center py-4">
          <div className="flex space-x-2">
            <Button variant="ghost" size="sm" className="flex items-center">
              <Heart className="w-4 h-4 mr-2" />
              <span>{data?.subscribers.length}</span>
            </Button>
            <Button variant="ghost" size="sm">
              <Share2 className="w-4 h-4 mr-2" />
              Share
            </Button>
          </div>
          <Button
            variant="secondary"
            size="sm"
            className={`w-max-[8rem] w-min-[8rem] w-[8rem] ${
              isSubscribed
                ? "bg-zinc-800 text-white hover:text-gray-400 hover:bg-zinc-200"
                : "bg-gray-200"
            }`}
            onClick={handleSubscribe}
          >
            {isSubscribed ? (
              <BellIcon className="w-4 h-4 mr-2" fill="white" />
            ) : (
              <BellIcon className="w-4 h-4 mr-2" />
            )}
            {isSubscribed ? "Subscribed" : "Subscribe"}
          </Button>
        </CardContent>
      </Card>

      {/* Content */}
      <article className="prose lg:prose-lg max-w-none">
        <div dangerouslySetInnerHTML={{ __html: data?.content! }} />
      </article>

      {/* Author Card */}
      <Card className="mt-12">
        <CardContent className="flex items-center space-x-4 p-6">
          <Avatar className="h-16 w-16">
            <AvatarImage src={data?.author.avatarUrl} alt={data?.author.name} />
            <AvatarFallback>{data?.author.name.charAt(0)}</AvatarFallback>
          </Avatar>
          <div>
            <h3 className="font-semibold text-lg">
              Written by {data?.author.name}
            </h3>
            <p className="text-gray-500">
              Technical writer and software engineer passionate about emerging
              technologies and their impact on the future of web development.
            </p>
          </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default Blogdata;
