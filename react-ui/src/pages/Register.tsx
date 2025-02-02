import { Button } from "@/components/ui/button";
import { SubmitHandler, useForm } from "react-hook-form";
import { Link } from "react-router-dom";

export interface RegisterInputProps {
  name: string;
  email: string;
  password: string;
}

const RegisterPage = () => {
  const { register, handleSubmit } = useForm<RegisterInputProps>();

  const onSubmit: SubmitHandler<RegisterInputProps> = (data) => {
    console.log("@DATA: ", data);
  };

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

        <form onSubmit={handleSubmit(onSubmit)}>
          {/*  Username Input */}
          <div className="mb-4 bg-sky-100">
            <label htmlFor="username" className="block text-gray-600">
              Full name
            </label>
            <input
              type="text"
              {...(register("name"), { required: true })}
              className="w-full border border-gray-300 rounded-md py-2 px-3 focus:outline-none focus:border-blue-500"
              autoComplete="off"
            />
          </div>
          {/*  Email Input */}
          <div className="mb-4 bg-sky-100">
            <label htmlFor="username" className="block text-gray-600">
              Email
            </label>
            <input
              type="email"
              {...(register("email"), { required: true })}
              className="w-full border border-gray-300 rounded-md py-2 px-3 focus:outline-none focus:border-blue-500"
              autoComplete="off"
            />
          </div>
          {/* Password Input */}
          <div className="mb-4">
            <label htmlFor="password" className="block text-gray-800">
              Password
            </label>
            <input
              type="password"
              {...register("password")}
              className="w-full border border-gray-300 rounded-md py-2 px-3 focus:outline-none focus:border-blue-500"
              autoComplete="off"
            />
          </div>

          {/* Register Button */}
          <Button
            variant={"default"}
            className="w-full bg-emerald-700 py-2 px-4 mt-5"
          >
            Register
          </Button>
        </form>
        {/* Login  Link */}
        <div className="mt-6 text-primary text-center font-semibold">
          <Link to="/login" className="hover:underline">
            Log In Here
          </Link>
        </div>
      </div>
    </div>
  );
};

export default RegisterPage;
