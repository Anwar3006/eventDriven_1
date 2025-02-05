import { RegisterUser } from "@/actions/AuthUser";
import { Button } from "@/components/ui/button";
import { RegisterInputProps } from "@/types/authInputs";
import { RegisterInputSchema } from "@/zodSchema/authInputSchema";
import { useMutation } from "@tanstack/react-query";
import { useCallback } from "react";
import { SubmitHandler, useForm } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Loader2Icon } from "lucide-react";
import { toast } from "sonner";

const RegisterPage = () => {
  const navigate = useNavigate();

  const form = useForm<z.infer<typeof RegisterInputSchema>>({
    resolver: zodResolver(RegisterInputSchema),
    defaultValues: {
      name: "",
      email: "",
      password: "",
    },
  });

  const { mutate, isPending } = useMutation({
    mutationFn: RegisterUser,
    onSuccess: () => {
      form.reset(), form.clearErrors();
      toast.info("User registered successfully", { id: "register_user" });

      // redirect to login page
      navigate("/login");
    },
    onError: () => {
      toast.error("User registration failed", { id: "register_user" });
    },
  });

  const onSubmit: SubmitHandler<RegisterInputProps> = useCallback(
    (values: z.infer<typeof RegisterInputSchema>) => {
      console.log(values);
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
      {/*  Right: Register Form */}
      <div className="lg:p-36 md:p-52 sm:20 p-8 w-full lg:w-1/2">
        <h1 className="text-2xl font-semibold mb-4">Register</h1>

        {/* Form */}
        <Form {...form}>
          <form className="space-y-6" onSubmit={form.handleSubmit(onSubmit)}>
            {/*  Username Input */}
            <div className="mb-4">
              <FormField
                control={form.control}
                name="name"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel className="block text-zinc-800">
                      Full Name
                    </FormLabel>
                    <FormControl>
                      <Input
                        placeholder="Kwame Billings"
                        {...field}
                        className="w-full border border-gray-300 rounded-md py-[1.5rem] px-3 focus:outline-none focus:border-blue-500 bg-white"
                      />
                    </FormControl>

                    <FormMessage />
                  </FormItem>
                )}
              />
            </div>

            {/*  Email Input */}
            <div className="my-4">
              <FormField
                control={form.control}
                name="email"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel className="block text-zinc-800">
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
            </div>

            {/* Password Input */}
            <div className="mb-4 bg-sky-100">
              <FormField
                control={form.control}
                name="password"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel className="block text-zinc-800">
                      Password
                    </FormLabel>
                    <FormControl>
                      <Input
                        {...field}
                        type="password"
                        placeholder="**********"
                        className="w-full border border-gray-300 rounded-md py-[1.5rem] px-3 focus:outline-none focus:border-blue-500 bg-white"
                      />
                    </FormControl>

                    <FormMessage />
                  </FormItem>
                )}
              />
            </div>

            {/* Register Button */}
            <Button
              variant={"default"}
              className="w-full bg-emerald-500 py-6 px-4 mt-5 text-base hover:bg-green-600"
            >
              {isPending ? (
                <Loader2Icon size={16} className="animate-spin" />
              ) : (
                "Register"
              )}
            </Button>
          </form>
        </Form>

        {/* Login  Link */}
        <div className="mt-6 text-muted-foreground text-center font-semibold">
          Already have an account?{" "}
          <Link to="/login">
            <span className="underline text-sky-500">Log In Here</span>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default RegisterPage;
