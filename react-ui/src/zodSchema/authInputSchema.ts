import { z } from "zod";

export const RegisterInputSchema = z.object({
  name: z.string().nonempty(),
  email: z.string().email(),
  password: z.string().min(8),
});

export const LoginInputSchema = z.object({
  email: z.string().email(),
  password: z.string().min(8),
});
