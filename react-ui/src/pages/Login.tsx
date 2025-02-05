import { LoginUser } from "@/actions/AuthUser";
import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { LoginInputSchema } from "@/zodSchema/authInputSchema";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { Loader2Icon } from "lucide-react";
import { useCallback } from "react";
import { useForm } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "sonner";
import { z } from "zod";

const Login = () => {
  const navigate = useNavigate();

  const form = useForm<z.infer<typeof LoginInputSchema>>({
    resolver: zodResolver(LoginInputSchema),
    defaultValues: {
      email: "",
      password: "",
    },
  });

  const { mutate, isPending } = useMutation({
    mutationFn: LoginUser,
    onSuccess: () => {
      form.reset(), form.clearErrors();
      toast.info("User logged in successfully", { id: "login_user" });

      navigate("/home");
    },
    onError: () => {
      toast.error("User login failed", { id: "login_user" });
    },
  });

  const onSubmit = useCallback(
    (values: z.infer<typeof LoginInputSchema>) => {
      mutate(values);
    },
    [mutate]
  );

  return (
    <div className="bg-sky-100 flex justify-center items-center h-screen">
      {/*  Left: Image  */}
      <div className="w-1/2 h-screen hidden lg:block">
        <img
          src="https://img.freepik.com/fotos-premium/imagen-fondo_910766-187.jpg?w=826"
          alt="Placeholder Image"
          className="object-cover w-full h-full"
        />
      </div>

      {/*  Right: Login Form */}
      <div className="lg:p-36 md:p-52 sm:20 p-8 w-full lg:w-1/2">
        <h1 className="text-2xl font-semibold mb-4">Login</h1>

        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
            {/*  Email Input */}
            <FormField
              control={form.control}
              name="email"
              render={({ field }) => (
                <FormItem>
                  <FormLabel className="block text-zinc-800 text-base">
                    Email Address
                  </FormLabel>
                  <FormControl>
                    <Input
                      placeholder="kwameB@gmail.com"
                      {...field}
                      className="w-full border border-gray-300 rounded-md py-[1.5rem] px-3 focus:outline-none focus:border-blue-500 bg-white"
                    />
                  </FormControl>

                  <FormMessage />
                </FormItem>
              )}
            />

            {/* Password Input */}
            <FormField
              control={form.control}
              name="password"
              render={({ field }) => (
                <FormItem>
                  <FormLabel className="block text-zinc-800 text-base">
                    Password
                  </FormLabel>
                  <FormControl>
                    <Input
                      placeholder="********"
                      {...field}
                      type="password"
                      className="w-full border border-gray-300 rounded-md py-[1.5rem] px-3 focus:outline-none focus:border-blue-500 bg-white"
                    />
                  </FormControl>

                  <FormMessage />
                </FormItem>
              )}
            />

            {/*  Forgot Password Link */}
            <div className="mb-6 text-blue-500">
              <a href="#" className="hover:underline">
                Forgot Password?
              </a>
            </div>

            {/* Login Button */}
            <Button
              type="submit"
              className="bg-sky-500 hover:bg-blue-600 text-white font-semibold rounded-md py-6 px-4 w-full text-base"
            >
              {isPending ? (
                <Loader2Icon size={16} className="animate-spin" />
              ) : (
                "Login"
              )}
            </Button>
          </form>
        </Form>

        {/* Sign up  Link */}
        <div className="mt-6 text-muted-foreground text-center font-semibold">
          No account?{" "}
          <Link to="/register">
            <span className="underline text-emerald-500">Create one Here</span>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Login;
