import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { blogPostInputSchema } from "@/zodSchema/blogPostInputSchema";
import { zodResolver } from "@hookform/resolvers/zod";

import { useForm } from "react-hook-form";
import { z } from "zod";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { useAuth } from "@/hooks/authHook";
import { useNavigate } from "react-router-dom";
import { Button } from "./ui/button";
import {
  QueryClient,
  useMutation,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query";
import { CreatePost } from "@/actions/CreatePost";
import { toast } from "sonner";

const PostCreation = ({
  openDialog,
  setOpenDialog,
}: {
  openDialog: boolean;
  setOpenDialog: (open: boolean) => void;
}) => {
  const navigate = useNavigate();
  const { user } = useAuth();
  if (!user) {
    navigate("/login");
  }

  return (
    <Dialog open={openDialog} onOpenChange={setOpenDialog}>
      <DialogContent className="sm:max-w-[1200px] max-h-[90vh] overflow-y-auto">
        <DialogHeader>
          <DialogTitle className="text-2xl font-semibold mb-1">
            Create a New Blog Post
          </DialogTitle>
        </DialogHeader>

        <div className="px-8">
          <DialogForm authorId={user?.id as string} setDialog={setOpenDialog} />
        </div>
      </DialogContent>
    </Dialog>
  );
};

const DialogForm = ({
  authorId,
  setDialog,
}: {
  authorId: string;
  setDialog: (open: boolean) => void;
}) => {
  const form = useForm<z.infer<typeof blogPostInputSchema>>({
    resolver: zodResolver(blogPostInputSchema),
    defaultValues: {
      title: "",
      content: "",
      status: "",
      author: "",
    },
  });

  const queryClient = useQueryClient();
  const { mutate, isPending } = useMutation({
    mutationFn: CreatePost,
    onSuccess: () => {
      form.reset(), form.clearErrors();
      setDialog(false);

      queryClient.invalidateQueries({ queryKey: ["all_posts"] });
      toast.info("Post created successfully", { id: "create_post" });
    },
    onError: () => {
      toast.error("Post creation failed", { id: "create_post" });
    },
  });

  let whichButton = "Publish";
  const handleSubmit = (data: z.infer<typeof blogPostInputSchema>) => {
    if (whichButton === "Publish") {
      data.status = "PUBLISHED";
    } else {
      data.status = "DRAFT";
    }

    data.author = authorId;
    console.log(data);
    mutate(data);
  };

  return (
    <Form {...form}>
      <form className="space-y-3" onSubmit={form.handleSubmit(handleSubmit)}>
        {/* title */}
        <FormField
          control={form.control}
          name="title"
          render={({ field }) => (
            <FormItem className="">
              <FormLabel className="text-base">Title</FormLabel>
              <FormControl>
                <Input placeholder="Title" {...field} />
              </FormControl>
            </FormItem>
          )}
        />

        {/* Content */}
        <FormField
          control={form.control}
          name="content"
          render={({ field }) => (
            <FormItem>
              <FormLabel className="text-base">Content</FormLabel>
              <FormControl>
                <Textarea {...field} rows={20} />
              </FormControl>
            </FormItem>
          )}
        />

        {/* Status */}

        {/* Submit or Draft */}
        <div className="flex items-center justify-end gap-4">
          <Button
            variant={"secondary"}
            className="hover:bg-gray-200"
            disabled={isPending}
            onClick={() => (whichButton = "Draft")}
          >
            Save for later
          </Button>

          <Button
            variant={"default"}
            disabled={isPending}
            onClick={() => (whichButton = "Publish")}
          >
            Publish
          </Button>
        </div>
      </form>
    </Form>
  );
};

export default PostCreation;
