import { z } from "zod";

export const blogPostInputSchema = z.object({
  title: z.string().min(3, { message: "Post title is required" }),
  content: z.string(),
  status: z.string(),
  author: z.string(),
});
